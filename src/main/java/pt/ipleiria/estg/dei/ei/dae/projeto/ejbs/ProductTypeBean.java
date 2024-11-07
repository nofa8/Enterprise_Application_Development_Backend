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
public class ProductTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(long code)
            throws MyEntityExistsException, MyEntityNotFoundException {

        if (entityManager.find(ProductType.class, code) != null){
            throw new MyEntityExistsException("Product Type with code " + code + " already exists");
        }

        var product = new ProductType(code);

        entityManager.persist(product);
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

    public void delete(long product) {
        entityManager.remove(entityManager.find(ProductType.class, product));
    }

//    public void update(long code, String name, String brand, ProductType type, long volumeCode) {
//        Product product = entityManager.find(Product.class, code);
//        if (product == null || !entityManager.contains(product)){
//            return;
//        }
//        product.setName(name);
//        product.setBrand(brand);
//        product.setType(type);
//        product.setVolume(entityManager.find(Volume.class,volumeCode));
//        entityManager.merge(product);
//    }
}
