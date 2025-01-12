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

    public void update(Long id, String password, String name, String email) throws MyConstraintViolationException, MyEntityNotFoundException {
        Manager manager = entityManager.find(Manager.class, id);
        if (manager == null) {
            throw new MyEntityNotFoundException("Manager with id " + id + " not found.");
        }

        try {
            manager.setPassword(hasher.hash(password));
            manager.setName(name);
            manager.setEmail(email);
            entityManager.merge(manager);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void create(String email, String password, String name) throws MyConstraintViolationException {
        try {
            var manager = new Manager(email, hasher.hash(password), name);
            entityManager.persist(manager);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Manager> findAll() {
        // remember, maps to: “SELECT m FROM Manager m ORDER BY m.name”
        return entityManager.createNamedQuery("getAllManagers", Manager.class).getResultList();
    }

    public Manager find(Long id) throws MyEntityNotFoundException {
        Manager manager = entityManager.find(Manager.class, id);
        if (manager == null) {
            throw new MyEntityNotFoundException("Manager with id " + id + " not found.");
        }
        return manager;
    }

    public Manager findByEmail(String email) throws MyEntityNotFoundException {
        try {
            return entityManager.createQuery(
                            "SELECT m FROM Manager m WHERE m.email = :email", Manager.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) { // Handle NoResultException or other exceptions if needed
            throw new MyEntityNotFoundException("Manager with name " + email + " not found.");
        }
    }

    public void delete(Long id) throws MyEntityNotFoundException {
        Manager manager = entityManager.find(Manager.class, id);
        if (manager == null) {
            throw new MyEntityNotFoundException("Manager with id " + id + " not found.");
        }
        entityManager.remove(manager);
    }
}
