package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Manager;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;

import java.util.List;

@Stateless
public class ManagerBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String email, String password, String name
                       ) throws MyEntityExistsException {
        if (entityManager.find(Manager.class, email) != null) {
            throw new MyEntityExistsException("Manager  "+email+" already exists" );
        }
        var manager = new Manager(email, password, name);

        entityManager.persist(manager);
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

    public Manager find(String email) {
        var manager = entityManager.find(Manager.class,email);
        if (manager == null) {
            throw new RuntimeException("Manager " + email + " not found");
        }
        return manager;
    }

    public void update( String email, String password, String name){
        Manager manager = entityManager.find(Manager.class, email);
        if (manager == null){
            return;
        }
        manager.setPassword(password);
        manager.setName(name);

        entityManager.merge(manager);
    }


}
