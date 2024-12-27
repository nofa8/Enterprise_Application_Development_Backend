package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class PackageTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(long code, String name)
            throws MyEntityExistsException, MyEntityNotFoundException {

        if (entityManager.find(PackageType.class, code) != null){
            throw new MyEntityExistsException("Package Type with code " + code + " already exists");
        }

        var packageType = new PackageType(code, name);

        entityManager.persist(packageType);
    }

    public List<PackageType> findAll() {

        return entityManager.createNamedQuery("getAllPackageTypes", PackageType.class).getResultList();
    }
    public PackageType find(long id) {
        var packageType = entityManager.find(PackageType.class,id);
        if (packageType == null) {
            throw new RuntimeException("Package Type " + id + " not found");
        }
        return packageType;
    }

    public void delete(long product) {
        entityManager.remove(entityManager.find(PackageType.class, product));
    }
}
