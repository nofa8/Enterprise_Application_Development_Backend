package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.ProductType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.SensorsType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.mappings.ProductSensorMapping;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(long code)
            throws MyEntityExistsException, MyEntityNotFoundException,MyConstraintViolationException {

        if (entityManager.find(ProductType.class, code) != null) {
            throw new MyEntityExistsException("Product Type with code " + code + " already exists");
        }
        try {
            var product = new ProductType(code);

            entityManager.persist(product);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
    public List<ProductType> findAll() {

        return entityManager.createNamedQuery("getAllProductTypes", ProductType.class).getResultList();
    }
    public ProductType find(long id) {
        var productType = entityManager.find(ProductType.class,id);
        if (productType == null) {
            throw new RuntimeException("Product Type " + id + " not found");
        }
        return productType;
    }

    public void delete(long productCode)  throws MyConstraintViolationException, MyEntityNotFoundException{
        try{
            ProductType product = entityManager.find(ProductType.class, productCode);
            if (product == null) {
                throw new MyEntityNotFoundException("Product Type with code " + productCode + " not found.");
            }

            entityManager.remove(product);
        }catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public void update(long code) throws MyConstraintViolationException {
        ProductType product = entityManager.find(ProductType.class, code);
        if (product == null || !entityManager.contains(product)) {
            return;
        }
        try {
            product.setCode(code);
            entityManager.merge(product);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void addSensor(long productCode, SensorsType sensor, int quantity)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        ProductType product = entityManager.find(ProductType.class, productCode);
        if (product == null) {
            throw new MyEntityNotFoundException("Product Type with code " + productCode + " not found.");
        }

        try {
            product.addSensor(sensor, quantity);
            entityManager.merge(product);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // Remove a sensor from a ProductType
    public void removeSensor(long productCode, SensorsType sensor) throws MyEntityNotFoundException {
        ProductType product = entityManager.find(ProductType.class, productCode);
        if (product == null) {
            throw new MyEntityNotFoundException("Product Type with code " + productCode + " not found.");
        }

        product.removeSensor(sensor);
        entityManager.merge(product);
    }

    public List<ProductSensorMapping> findSensorsForProduct(long productCode) throws MyEntityNotFoundException {
        ProductType product = entityManager.find(ProductType.class, productCode);
        if (product == null) {
            throw new MyEntityNotFoundException("Product Type with code " + productCode + " not found.");
        }
        return product.getSensors();
    }
}
