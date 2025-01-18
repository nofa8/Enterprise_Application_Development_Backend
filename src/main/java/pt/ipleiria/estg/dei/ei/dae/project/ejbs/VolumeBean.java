package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.project.entities.*;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.ProductSensorMapping;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.ProductVolumeMapping;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import java.time.Instant;
import java.util.*;

import static pt.ipleiria.estg.dei.ei.dae.project.ejbs.ConfigBean.logger;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long code, VolumeState state, long typePackage, long orderCode, Date timestamp)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {

        if (entityManager.find(Volume.class, code) != null) {
            throw new MyEntityExistsException("Volume with code " + code + " already exists");
        }

        Order order = entityManager.find(Order.class, orderCode);
        if (order == null) {
            throw new MyEntityNotFoundException("Order with code " + code + " not found");
        }

        PackageType packageType = entityManager.find(PackageType.class, typePackage);
        if (packageType == null) {
            throw new MyEntityNotFoundException("Package Type with code " + code + " not found");
        }

        try {
            var volume = new Volume(code, state, packageType, order, timestamp);

            entityManager.persist(volume);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
    public List<Volume> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }
    public Volume find(long id) {
        var volume = entityManager.find(Volume.class,id);
        if (volume == null) {
            throw new RuntimeException("Volume " + id + " not found");
        }
        return volume;
    }

    public void delete(long volume) {
        entityManager.remove(entityManager.find(Volume.class, volume));
    }

    public void update(long code, VolumeState state, PackageType typePackage, long orderCode, Date timestamp) throws MyConstraintViolationException {
        Volume volume = entityManager.find(Volume.class, code);
        if (volume == null || !entityManager.contains(volume)) {
            return;
        }
        try {
            volume.setState(state);
            volume.setTypePackage(typePackage);
            volume.setOrder(entityManager.find(Order.class, orderCode));
            volume.setTimestamp(timestamp);
            entityManager.merge(volume);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public Volume findWithProducts(long volumeCode) throws MyEntityNotFoundException {
        Volume volume = entityManager.find(Volume.class, volumeCode);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume with code " + volumeCode + " not found.");
        }
        Hibernate.initialize(volume.getProducts());
        return volume;
    }

    public Volume findWithSensors(long volumeCode) throws MyEntityNotFoundException {
        Volume volume = entityManager.find(Volume.class, volumeCode);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume with code " + volumeCode + " not found.");
        }
        Hibernate.initialize(volume.getSensors());
        return volume;
    }

    public Volume findWithProductsAndSensors(long volumeCode) throws MyEntityNotFoundException {
        Volume volume = entityManager.find(Volume.class, volumeCode);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume with code " + volumeCode + " not found.");
        }
        Hibernate.initialize(volume.getProducts());
        Hibernate.initialize(volume.getSensors());
        for (ProductVolumeMapping prod : volume.getProducts()){
            Hibernate.initialize(prod.getProduct());
        }
        return volume;
    }


    public void createVolumes(long orderId, List<PostVolumeDTO> volumeDTOs)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {

        // Fetch the associated order
        Date timestamp = Date.from(Instant.now());
        Order order = entityManager.find(Order.class, orderId);
        if (order == null) {
            throw new MyEntityNotFoundException("Order with code " + orderId + " not found");
        }

        try {
            for (PostVolumeDTO volumeDTO : volumeDTOs) {
                // Validate volume existence
                if (entityManager.find(Volume.class, volumeDTO.getCode()) != null) {
                    throw new MyEntityExistsException("Volume with code " + volumeDTO.getCode() + " already exists");
                }

                // Validate and fetch package type
                PackageType packageType = entityManager.find(PackageType.class, volumeDTO.getPackageTypeCode());
                if (packageType == null) {
                    throw new MyEntityNotFoundException("Package Type with code " + volumeDTO.getPackageTypeCode() + " not found");
                }

                // Compute required sensor quantities
                Map<Long, Integer> requiredSensorQuantities = calculateRequiredSensors(volumeDTO, packageType);

                // Fetch and validate sensors
                List<Sensor> sensors = fetchAndValidateSensors(volumeDTO, requiredSensorQuantities, timestamp);

                // Persist the volume and its sensors
                persistVolumeAndSensors(volumeDTO, packageType, order, sensors, timestamp);
            }
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }catch (IllegalStateException e){
            throw new IllegalStateException(e);
        }
    }

    private Map<Long, Integer> calculateRequiredSensors(PostVolumeDTO volumeDTO, PackageType packageType)
            throws MyEntityNotFoundException {
        Map<Long, Integer> sensorQuantities = new HashMap<>(packageType.getSensorQuantities());

        for (PostVolumeProductDTO productDTO : volumeDTO.getProducts()) {
            Product product = entityManager.find(Product.class, productDTO.getCode());
            if (product == null) {
                throw new MyEntityNotFoundException("Product with code " + productDTO.getCode() + " not found");
            }

            ProductType productType = product.getType();
            Map<Long, Integer> productSensorQuantities = productType.getSensorQuantities();

            for (int i = 0; i < productDTO.getAmount(); i++) {
                for (Map.Entry<Long, Integer> entry : productSensorQuantities.entrySet()) {
                    sensorQuantities.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
            }
        }

        return sensorQuantities;
    }

    private List<Sensor> fetchAndValidateSensors(PostVolumeDTO volumeDTO, Map<Long, Integer> requiredSensorQuantities, Date timestamp)
            throws MyEntityNotFoundException {
        List<Sensor> sensors = new ArrayList<>();
        Map<Long, Integer> sensorTypeCounts = new HashMap<>();

        for (PostVolumeSensorDTO sensorDTO : volumeDTO.getSensors()) {
            SensorsType sensorsType = entityManager.find(SensorsType.class, sensorDTO.getSensorTypeCode());
            if (sensorsType == null) {
                throw new MyEntityNotFoundException("Sensors Type with code " + sensorDTO.getSensorTypeCode() + " not found");
            }

            // Increment the count for this sensor type
            sensorTypeCounts.merge(sensorDTO.getSensorTypeCode(), 1, Integer::sum);

            sensors.add(new Sensor(sensorDTO.getCode(), sensorsType, "", timestamp, null));
        }

        // Validate that the sensor quantities match the required quantities
        if (!requiredSensorQuantities.equals(sensorTypeCounts)) {
            throw new IllegalStateException("The number of sensors does not correspond to the required quantities given the package and product types");
        }

        return sensors;
    }

    private void persistVolumeAndSensors(PostVolumeDTO volumeDTO, PackageType packageType, Order order, List<Sensor> sensors, Date timestamp) throws MyEntityNotFoundException {
        Volume volume = new Volume(
                volumeDTO.getCode(),
                volumeDTO.getState(),
                packageType,
                order,
                timestamp
        );

        entityManager.persist(volume);

        for (Sensor sensor : sensors) {
            sensor.setVolume(volume);
            entityManager.persist(sensor);
        }

        // Now handle the products and create mappings for each one
        for (PostVolumeProductDTO productDTO : volumeDTO.getProducts()) {
            // Find the product by its ID (assuming the DTO has the product code)
            Product product = entityManager.find(Product.class, productDTO.getCode());
            if (product == null) {
                throw new MyEntityNotFoundException("Product with code " + productDTO.getCode() + " not found.");
            }

            // Create the ProductVolumeMapping
            ProductVolumeMapping mapping = new ProductVolumeMapping(product, volume, productDTO.getAmount());

            // Persist the mapping
            entityManager.persist(mapping);
        }
    }


    public void patchState(Long volumeId, VolumeState state) throws MyEntityNotFoundException {
        Volume volume = entityManager.find(Volume.class, volumeId);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume with code " + volumeId + " not found");
        }

        volume.setState(state);
        entityManager.merge(volume);

    }

//    public void addProductToVolume(long volumeCode, long productCode, int quantity)
//            throws MyEntityNotFoundException, MyEntityExistsException {
//
//        Volume volume = findVolumeById(volumeCode);
//        Product product = findProductById(productCode);
//
//        if (quantity <= 0) {
//            throw new IllegalArgumentException("Quantity must be greater than zero.");
//        }
//        if (!entityManager.contains(volume)) {
//            volume = entityManager.merge(volume);
//        }
//
//        if (!entityManager.contains(product)) {
//            product = entityManager.merge(product);
//        }
//         final Product   prod = product;
//        boolean alreadyExists = volume.getProducts().stream()
//                .anyMatch(mapping -> mapping.getProduct().equals(prod));
//        if (alreadyExists) {
//            throw new MyEntityExistsException("Product " + productCode + " is already in Volume " + volumeCode + ".");
//        }
//
//        try {
//            ProductVolumeMapping productVolumeMapping = new ProductVolumeMapping(product, volume, quantity);
//            volume.addProductMapping(productVolumeMapping);
//            product.addVolumeMapping(productVolumeMapping);
//
//
//            entityManager.merge(volume);
//            entityManager.merge(product);
//        } catch (Exception e) {
//            logger.severe("Error adding product to volume: volumeCode="+volumeCode+", productCode="+productCode+", quantity="+quantity);
//            throw e;
//        }
//    }
//
//    public void removeProductFromVolume(long volumeCode, long productCode)
//            throws MyEntityNotFoundException {
//
//        Volume volume = findVolumeById(volumeCode);
//        Product product = findProductById(productCode);
//
//        ProductVolumeMapping mappingToRemove = volume.getProducts().stream()
//                .filter(mapping -> mapping.getProduct().equals(product))
//                .findFirst()
//                .orElseThrow(() -> new MyEntityNotFoundException(
//                        "Product " + productCode + " is not in Volume " + volumeCode + "."));
//
//        volume.removeProductMapping(mappingToRemove);
//        product.removeVolumeMapping(mappingToRemove);
//
//        try {
//            entityManager.merge(volume);
//            entityManager.merge(product);
//        } catch (Exception e) {
//            throw e;
//        }
//    }


    // Helper methods for finding Volume and Product entities
    private Volume findVolumeById(long volumeCode) throws MyEntityNotFoundException {
        Volume volume = entityManager.find(Volume.class, volumeCode);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume with code " + volumeCode + " not found.");
        }
        return volume;
    }

    private Product findProductById(long productCode) throws MyEntityNotFoundException {
        Product product = entityManager.find(Product.class, productCode);
        if (product == null) {
            throw new MyEntityNotFoundException("Product with code " + productCode + " not found.");
        }
        return product;
    }

}
