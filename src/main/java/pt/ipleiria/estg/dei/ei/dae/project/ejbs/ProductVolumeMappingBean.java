package pt.ipleiria.estg.dei.ei.dae.project.ejbs;


import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.ProductVolumeMapping;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import java.util.List;

import static pt.ipleiria.estg.dei.ei.dae.project.ejbs.ConfigBean.logger;

@Stateless
public class ProductVolumeMappingBean {

    @PersistenceContext
    private EntityManager entityManager;

    // Create a new ProductVolumeMapping
    public void create(long productCode  , long volumeCode , int quantity)
            throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {

        Product product = entityManager.find(Product.class, productCode);
        if (product == null) {
            throw new MyEntityNotFoundException("Product not found with code " + productCode);
        }
        Volume volume = entityManager.find(Volume.class, volumeCode);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume not found with code  " + volumeCode);
        }

        ProductVolumeMapping existingMapping = find(product, volume);
        if (existingMapping != null) {
            throw new MyEntityExistsException("Mapping for Product " + product.getCode() + " and Volume " + volume.getCode() + " already exists.");
        }
        try {
            ProductVolumeMapping mapping = new ProductVolumeMapping(product,volume,quantity);
//            mapping.setProduct(product);
//            mapping.setVolume(volume);
//            mapping.setAmount(quantity);
            // Persist the new mapping
            entityManager.persist(mapping);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    // Find a specific ProductVolumeMapping by Product and Volume
    public ProductVolumeMapping find(Product product, Volume volume) {
        TypedQuery<ProductVolumeMapping> query = entityManager.createQuery(
                "SELECT p FROM ProductVolumeMapping p WHERE p.product = :product AND p.volume = :volume", ProductVolumeMapping.class);
        query.setParameter("product", product);
        query.setParameter("volume", volume);
        List<ProductVolumeMapping> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0); // Returns the first match or null if not found
    }

    // Find all mappings for a specific product
    public List<ProductVolumeMapping> findAllForProduct(Product product) {
        TypedQuery<ProductVolumeMapping> query = entityManager.createQuery(
                "SELECT p FROM ProductVolumeMapping p WHERE p.product = :product", ProductVolumeMapping.class);
        query.setParameter("product", product);
        return query.getResultList();
    }

    // Find all mappings for a specific volume
    public List<ProductVolumeMapping> findAllForVolume(Volume volume) {
        TypedQuery<ProductVolumeMapping> query = entityManager.createQuery(
                "SELECT p FROM ProductVolumeMapping p WHERE p.volume = :volume", ProductVolumeMapping.class);
        query.setParameter("volume", volume);
        return query.getResultList();
    }

    // Update the quantity of an existing mapping
    public void updateQuantity(Product product, Volume volume, int quantity) throws MyEntityNotFoundException {
        ProductVolumeMapping mapping = find(product, volume);
        if (mapping == null) {
            throw new MyEntityNotFoundException("Mapping not found for Product " + product.getCode() + " and Volume " + volume.getCode());
        }

        mapping.setAmount(quantity);

        entityManager.merge(mapping);
    }

    // Delete an existing mapping
    public void delete(Product product, Volume volume) throws MyEntityNotFoundException {
        ProductVolumeMapping mapping = find(product, volume);
        if (mapping == null) {
            throw new MyEntityNotFoundException("Mapping not found for Product " + product.getCode() + " and Volume " + volume.getCode());
        }

        entityManager.remove(mapping);
    }
}
