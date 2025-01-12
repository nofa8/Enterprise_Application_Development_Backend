package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Logistic;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.security.Hasher;

import java.util.List;

@Stateless
public class LogisticBean {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;


    /**
     * Creates a new Logistic user.
     *
     * @param email    the email of the logistic user
     * @param password the password of the logistic user
     * @param name     the name of the logistic user
     * @throws MyEntityExistsException       if a logistic user with the same email already exists
     * @throws MyConstraintViolationException if validation constraints are violated
     */
    public void create(String email, String password, String name) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        try {
            var logistic = new Logistic(email, hasher.hash(password), name);
            entityManager.persist(logistic);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    /**
     * Updates an existing Logistic user.
     *
     * @param id       the ID of the logistic user
     * @param password the new password
     * @param name     the new name
     * @param email    the new email
     * @throws MyEntityNotFoundException     if the logistic user is not found
     * @throws MyConstraintViolationException if validation constraints are violated
     */
    public void update(Long id, String password, String name, String email) throws MyEntityNotFoundException, MyConstraintViolationException {
        Logistic logistic = entityManager.find(Logistic.class, id);
        if (logistic == null) {
            throw new MyEntityNotFoundException("Logistic with id " + id + " not found.");
        }

        try {
            logistic.setPassword(hasher.hash(password));
            logistic.setName(name);
            logistic.setEmail(email);
            entityManager.merge(logistic);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    /**
     * Deletes a Logistic user by ID.
     *
     * @param id the ID of the logistic user
     * @throws MyEntityNotFoundException if the logistic user is not found
     */
    public void delete(Long id) throws MyEntityNotFoundException {
        Logistic logistic = entityManager.find(Logistic.class, id);
        if (logistic == null) {
            throw new MyEntityNotFoundException("Logistic with id " + id + " not found.");
        }
        entityManager.remove(logistic);
    }

    /**
     * Finds a Logistic user by ID.
     *
     * @param id the ID of the logistic user
     * @return the found Logistic user
     * @throws MyEntityNotFoundException if the logistic user is not found
     */
    public Logistic find(Long id) throws MyEntityNotFoundException {
        Logistic logistic = entityManager.find(Logistic.class, id);
        if (logistic == null) {
            throw new MyEntityNotFoundException("Logistic with id " + id + " not found.");
        }
        return logistic;
    }

    /**
     * Finds a Logistic user by email.
     *
     * @param email the email of the logistic user
     * @return the found Logistic user
     * @throws MyEntityNotFoundException if the logistic user is not found
     */
    public Logistic findByEmail(String email) throws MyEntityNotFoundException {
        try {
            return entityManager.createQuery(
                            "SELECT l FROM Logistic l WHERE l.email = :email", Logistic.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            throw new MyEntityNotFoundException("Logistic with email " + email + " not found.");
        }
    }

    /**
     * Retrieves all Logistic users.
     *
     * @return a list of all Logistic users
     */
    public List<Logistic> findAll() {
        return entityManager.createNamedQuery("getAllLogistics", Logistic.class).getResultList();
    }
}
