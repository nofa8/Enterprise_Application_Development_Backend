package pt.ipleiria.estg.dei.ei.dae.project.ejbs;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.entities.ProductType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.SensorsType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.ProductSensorMapping;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductSensorMappingBean {

    @PersistenceContext
    private EntityManager entityManager;

    // Create a new ProductSensorMapping
    public void create(ProductType product, SensorsType sensor, int quantity)
            throws MyEntityExistsException, MyConstraintViolationException {

        // Check if the mapping already exists (to avoid duplicates)
        ProductSensorMapping existingMapping = find(product, sensor);
        if (existingMapping != null) {
            throw new MyEntityExistsException("Mapping for Product " + product.getCode() + " and Sensor " + sensor.getId() + " already exists.");
        }

        try {
            // Create the new mapping
            ProductSensorMapping mapping = new ProductSensorMapping();
            mapping.setProduct(product);
            mapping.setSensor(sensor);
            mapping.setQuantity(quantity);

            // Persist the new mapping
            entityManager.persist(mapping);
        } catch (ConstraintViolationException e) {
            // Handle any constraint violations or other exceptions
            throw new MyConstraintViolationException(e);
        }
    }

    // Find a specific ProductSensorMapping by Product and Sensor
    public ProductSensorMapping find(ProductType product, SensorsType sensor) {
        TypedQuery<ProductSensorMapping> query = entityManager.createQuery(
                "SELECT p FROM ProductSensorMapping p WHERE p.product = :product AND p.sensor = :sensor", ProductSensorMapping.class);
        query.setParameter("product", product);
        query.setParameter("sensor", sensor);
        List<ProductSensorMapping> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);  // Returns the first match or null if not found
    }

    // Find all mappings for a specific product
    public List<ProductSensorMapping> findAllForProduct(ProductType product) {
        TypedQuery<ProductSensorMapping> query = entityManager.createQuery(
                "SELECT p FROM ProductSensorMapping p WHERE p.product = :product", ProductSensorMapping.class);
        query.setParameter("product", product);
        return query.getResultList();
    }

    // Find all mappings for a specific sensor
    public List<ProductSensorMapping> findAllForSensor(SensorsType sensor) {
        TypedQuery<ProductSensorMapping> query = entityManager.createQuery(
                "SELECT p FROM ProductSensorMapping p WHERE p.sensor = :sensor", ProductSensorMapping.class);
        query.setParameter("sensor", sensor);
        return query.getResultList();
    }

    public void updateQuantity(ProductType product, SensorsType sensor, int quantity) throws MyEntityNotFoundException {
        ProductSensorMapping mapping = find(product, sensor);
        if (mapping == null) {
            throw new MyEntityNotFoundException("Mapping not found for Product " + product.getCode() + " and Sensor " + sensor.getId());
        }

        mapping.setQuantity(quantity);

        entityManager.merge(mapping);
    }

    public void delete(ProductType product, SensorsType sensor) throws MyEntityNotFoundException {
        ProductSensorMapping mapping = find(product, sensor);
        if (mapping == null) {
            throw new MyEntityNotFoundException("Mapping not found for Product " + product.getCode() + " and Sensor " + sensor.getId());
        }

        entityManager.remove(mapping);
    }
}
