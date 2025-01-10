package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.SensorsType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.PackageSensorMapping;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class PackageSensorMappingBean {

    @PersistenceContext
    private EntityManager entityManager;

    // Create a new PackageSensorMapping
    public void create(PackageType packageType, SensorsType sensor, int quantity)
            throws MyEntityExistsException, MyConstraintViolationException {

        // Check if the mapping already exists (to avoid duplicates)
        PackageSensorMapping existingMapping = find(packageType, sensor);
        if (existingMapping != null) {
            throw new MyEntityExistsException("Mapping for Package " + packageType.getCode() + " and Sensor " + sensor.getId() + " already exists.");
        }

        try {
            // Create the new mapping
            PackageSensorMapping mapping = new PackageSensorMapping();
            mapping.setPackageType(packageType);
            mapping.setSensor(sensor);
            mapping.setQuantity(quantity);

            // Persist the new mapping
            entityManager.persist(mapping);
        } catch (ConstraintViolationException e) {
            // Handle any constraint violations or other exceptions
            throw new MyConstraintViolationException(e);
        }
    }

    // Find a specific PackageSensorMapping by Package and Sensor
    public PackageSensorMapping find(PackageType packageType, SensorsType sensor) {
        TypedQuery<PackageSensorMapping> query = entityManager.createQuery(
                "SELECT p FROM PackageSensorMapping p WHERE p.packageType = :packageType AND p.sensor = :sensor", PackageSensorMapping.class);
        query.setParameter("packageType", packageType);
        query.setParameter("sensor", sensor);
        List<PackageSensorMapping> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0); // Returns the first match or null if not found
    }

    // Find all mappings for a specific package
    public List<PackageSensorMapping> findAllForPackage(PackageType packageType) {
        TypedQuery<PackageSensorMapping> query = entityManager.createQuery(
                "SELECT p FROM PackageSensorMapping p WHERE p.packageType = :packageType", PackageSensorMapping.class);
        query.setParameter("packageType", packageType);
        return query.getResultList();
    }

    // Find all mappings for a specific sensor
    public List<PackageSensorMapping> findAllForSensor(SensorsType sensor) {
        TypedQuery<PackageSensorMapping> query = entityManager.createQuery(
                "SELECT p FROM PackageSensorMapping p WHERE p.sensor = :sensor", PackageSensorMapping.class);
        query.setParameter("sensor", sensor);
        return query.getResultList();
    }

    public void updateQuantity(PackageType packageType, SensorsType sensor, int quantity) throws MyEntityNotFoundException {
        PackageSensorMapping mapping = find(packageType, sensor);
        if (mapping == null) {
            throw new MyEntityNotFoundException("Mapping not found for Package " + packageType.getCode() + " and Sensor " + sensor.getId());
        }

        mapping.setQuantity(quantity);

        entityManager.merge(mapping);
    }

    public void delete(PackageType packageType, SensorsType sensor) throws MyEntityNotFoundException {
        PackageSensorMapping mapping = find(packageType, sensor);
        if (mapping == null) {
            throw new MyEntityNotFoundException("Mapping not found for Package " + packageType.getCode() + " and Sensor " + sensor.getId());
        }

        entityManager.remove(mapping);
    }
}
