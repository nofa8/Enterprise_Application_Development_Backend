package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.project.entities.*;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import java.time.Instant;
import java.util.Date;
import java.util.List;

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

    public void addProductToVolume(long volumeCode, long productCode)
            throws MyEntityNotFoundException, MyEntityExistsException {

        Volume volume = entityManager.find(Volume.class, volumeCode);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume with code " + volumeCode + " not found.");
        }

        Product product = entityManager.find(Product.class, productCode);
        if (product == null) {
            throw new MyEntityNotFoundException("Product with code " + productCode + " not found.");
        }

        if (volume.getProducts().contains(product)) {
            throw new MyEntityExistsException("Product " + productCode + " is already in Volume " + volumeCode + ".");
        }

        volume.addProduct(product);
        product.addVolume(volume);
        entityManager.merge(volume);
        entityManager.merge(product);
    }

    public void removeProductFromVolume(long volumeCode, long productCode)
            throws MyEntityNotFoundException, MyEntityExistsException {

        Volume volume = entityManager.find(Volume.class, volumeCode);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume with code " + volumeCode + " not found.");
        }

        Product product = entityManager.find(Product.class, productCode);
        if (product == null) {
            throw new MyEntityNotFoundException("Product with code " + productCode + " not found.");
        }

        if (!volume.getProducts().contains(product)) {
            throw new MyEntityExistsException("Product " + productCode + " is not in Volume " + volumeCode + ".");
        }

        // Remove Product from the Volume
        volume.removeProduct(product);
        product.removeVolume(volume);
        entityManager.merge(volume);
        entityManager.merge(product);
    }

    public Volume findWithProducts(long volumeCode) throws MyEntityNotFoundException {
        Volume volume = entityManager.find(Volume.class, volumeCode);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume with code " + volumeCode + " not found.");
        }
        Hibernate.initialize(volume.getProducts());
        return volume;
    }

    public void createVolumes(long orderId, List<VolumeDTO> volumeDTOs)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {

        // Find the associated order
        Date timestamp = Date.from(Instant.now());
        Order order = entityManager.find(Order.class, orderId);
        if (order == null) {
            throw new MyEntityNotFoundException("Order with code " + orderId + " not found");
        }

        for (VolumeDTO volumeDTO : volumeDTOs) {
            // Check if the volume already exists
            if (entityManager.find(Volume.class, volumeDTO.getCode()) != null) {
                throw new MyEntityExistsException("Volume with code " + volumeDTO.getCode() + " already exists");
            }

            // Find the associated package type
            PackageType packageType = entityManager.find(PackageType.class, volumeDTO.getPackageTypeCode());
            if (packageType == null) {
                throw new MyEntityNotFoundException("Package Type with code " + volumeDTO.getPackageTypeCode() + " not found");
            }

            try {
                // Create and persist the volume
                Volume volume = new Volume(
                        volumeDTO.getCode(),
                        volumeDTO.getState(),
                        packageType,
                        order,
                        timestamp
                );

                entityManager.persist(volume);

                for (SensorDTO sensor : volumeDTO.getSensors()){
                    SensorsType sensorsType = entityManager.find(SensorsType.class,sensor.getSensorTypeCode());
                    if (sensorsType == null) {
                        throw new MyEntityNotFoundException("Sensors Type with code " + sensor.getSensorTypeCode() + " not found");
                    }
                    Sensor sensor1 = new Sensor(sensor.getCode(),sensorsType,sensor.getValue(),sensor.getLastUpdate(),volume);
                    entityManager.persist(sensor1);
                }
            } catch (ConstraintViolationException e) {
                throw new MyConstraintViolationException(e);
            }
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
}
