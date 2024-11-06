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

    public void create(String username, String password, String name,
                       String email) throws MyEntityExistsException {
        if (entityManager.find(Manager.class, username) != null) {
            throw new MyEntityExistsException("Manager  "+username+" already exists" );
        }
        var manager = new Manager(username, password, name, email);

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

    public Manager find(String username) {
        var manager = entityManager.find(Manager.class,username);
        if (manager == null) {
            throw new RuntimeException("Manager " + username + " not found");
        }
        return manager;
    }

    public void update(String username, String password, String name, String email){
        Manager manager = entityManager.find(Manager.class, username);
        if (manager == null){
            return;
        }
        manager.setPassword(password);
        manager.setName(name);
        manager.setEmail(email);

        entityManager.merge(manager);
    }


}
