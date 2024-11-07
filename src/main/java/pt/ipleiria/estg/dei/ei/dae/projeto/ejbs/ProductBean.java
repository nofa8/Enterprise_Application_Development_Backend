package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.ProductType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    //long code, String name, String brand, ProductType type, Volume volume

    public void create(long code, String name, String brand, ProductType type, long volumeCode)
            throws MyEntityExistsException, MyEntityNotFoundException {

        if (entityManager.find(Product.class, code) != null){
            throw new MyEntityNotFoundException("Product with code " + code + " not found");
        }

        Volume volume = entityManager.find(Volume.class, volumeCode);
        if ( volume == null){
            throw new MyEntityNotFoundException("Volume with code " + code + " not found");
        }

        var product = new Product(code,  name,  brand,  type,volume);

        entityManager.persist(product);
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

    public void update(long code, String name, String brand, ProductType type, long volumeCode) {
        Product product = entityManager.find(Product.class, code);
        if (product == null || !entityManager.contains(product)){
            return;
        }
        product.setName(name);
        product.setBrand(brand);
        product.setType(type);
        product.setVolume(entityManager.find(Volume.class,volumeCode));
        entityManager.merge(product);
    }
}
