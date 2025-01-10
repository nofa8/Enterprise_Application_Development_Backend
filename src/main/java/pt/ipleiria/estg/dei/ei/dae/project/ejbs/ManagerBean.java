package pt.ipleiria.estg.dei.ei.dae.project.ejbs;


import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Manager;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.security.Hasher;

import java.util.List;

@Stateless
public class ManagerBean {
    @PersistenceContext
    private EntityManager entityManager;


    @Inject
    private Hasher hasher;
    public void create(String email, String password, String name
                       ) throws MyEntityExistsException, MyConstraintViolationException {
        if (entityManager.find(Manager.class, email) != null) {
            throw new MyEntityExistsException("Manager  " + email + " already exists");
        }

        try {
            var manager = new Manager(email, hasher.hash(password), name);

            entityManager.persist(manager);
        }catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }
    public void delete(Manager manager) {
        if (manager == null || !entityManager.contains(manager)){
            return;
        }
        entityManager.remove(manager);
    }

    public List<Manager> findAll() {
        // remember, maps to: “SELECT s FROM Manager s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllManagers",Manager.class).getResultList();
    }

    public Manager find(String email) throws MyEntityNotFoundException {
        var manager = entityManager.find(Manager.class,email);
        if (manager == null) {
            throw new MyEntityNotFoundException("Manager " + email + " not found");
        }
        return manager;
    }

    public void update( String email, String password, String name) throws MyConstraintViolationException{
        Manager manager = entityManager.find(Manager.class, email);
        if (manager == null){
            return;
        }

        try{
            manager.setPassword(hasher.hash(password));
            manager.setName(name);
            entityManager.merge(manager);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }


    }


}
