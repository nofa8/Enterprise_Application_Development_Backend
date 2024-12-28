package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.ProductType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    //long code, String name, String brand, ProductType type, Volume volume

    public void create(long code, String name, String brand, float price, String description, long codeProductType)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {

        if (entityManager.find(Product.class, code) != null) {
            throw new MyEntityExistsException("Product with code " + code + " already exists");
        }

        ProductType productType = entityManager.find(ProductType.class, codeProductType);
        if (productType == null) {
            throw new MyEntityNotFoundException("Product Type with code " + code + " not found");
        }

        try {
            var product = new Product(code, name, brand, price, description, productType);

            entityManager.persist(product);

        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Product> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }
    public Product find(long id) {
        var product = entityManager.find(Product.class,id);
        if (product == null) {
            throw new RuntimeException("Product " + id + " not found");
        }
        return product;
    }

    public void delete(long product) {
        entityManager.remove(entityManager.find(Product.class, product));
    }

    public void update(long code, String name, String brand, float price, String description, long codeProductType, long volumeCode) throws MyConstraintViolationException{
        Product product = entityManager.find(Product.class, code);
        if (product == null || !entityManager.contains(product)) {
            return;
        }
        try {
            product.setName(name);
            product.setBrand(brand);
            product.setPrice(price);
            product.setDescription(description);
            product.setType(entityManager.find(ProductType.class, codeProductType));
            entityManager.merge(product);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
}
