package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.SensorsType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.mappings.PackageSensorMapping;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class PackageTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    // Create a new PackageType
    public void create(long code, String name)
            throws MyEntityExistsException, MyConstraintViolationException {

        if (entityManager.find(PackageType.class, code) != null) {
            throw new MyEntityExistsException("Package Type with code " + code + " already exists.");
        }
        try {
            var packageType = new PackageType(code, name);
            entityManager.persist(packageType);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // Find all PackageTypes
    public List<PackageType> findAll() {
        return entityManager.createNamedQuery("getAllPackageTypes", PackageType.class).getResultList();
    }

    // Find a specific PackageType
    public PackageType find(long id) throws MyEntityNotFoundException {
        PackageType packageType = entityManager.find(PackageType.class, id);
        if (packageType == null) {
            throw new MyEntityNotFoundException("Package Type with code " + id + " not found.");
        }
        return packageType;
    }

    // Delete a PackageType
    public void delete(long packageCode) throws MyConstraintViolationException, MyEntityNotFoundException {
        try {
            PackageType packageType = entityManager.find(PackageType.class, packageCode);
            if (packageType == null) {
                throw new MyEntityNotFoundException("Package Type with code " + packageCode + " not found.");
            }
            entityManager.remove(packageType);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // Add a sensor to a PackageType
    public void addSensor(long packageCode, SensorsType sensor, int quantity)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        PackageType packageType = entityManager.find(PackageType.class, packageCode);
        if (packageType == null) {
            throw new MyEntityNotFoundException("Package Type with code " + packageCode + " not found.");
        }

        try {
            packageType.addSensor(sensor, quantity);
            entityManager.merge(packageType);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // Remove a sensor from a PackageType
    public void removeSensor(long packageCode, SensorsType sensor) throws MyEntityNotFoundException {
        PackageType packageType = entityManager.find(PackageType.class, packageCode);
        if (packageType == null) {
            throw new MyEntityNotFoundException("Package Type with code " + packageCode + " not found.");
        }

        packageType.removeSensor(sensor);
        entityManager.merge(packageType);
    }

    // Find all sensors for a specific PackageType
    public List<PackageSensorMapping> findSensorsForPackage(long packageCode) throws MyEntityNotFoundException {
        PackageType packageType = entityManager.find(PackageType.class, packageCode);
        if (packageType == null) {
            throw new MyEntityNotFoundException("Package Type with code " + packageCode + " not found.");
        }
        return packageType.getSensors();
    }
}
