package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.security.Hasher;

import java.util.List;

@Stateless
public class ClientBean {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public void update(Long id, String password, String name, String email) throws MyConstraintViolationException, MyEntityNotFoundException {
        Client client = entityManager.find(Client.class, id);
        if (client == null) {
            throw new MyEntityNotFoundException("Client with id " + id + " not found.");
        }

        try {
            client.setPassword(hasher.hash(password));
            client.setName(name);
            client.setEmail(email);
            entityManager.merge(client);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void create(String email, String password, String name) throws MyConstraintViolationException {
        try {
            var client = new Client(email, hasher.hash(password), name);
            entityManager.persist(client);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
    public List<Client> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllClients", Client.class).getResultList();
    }


    public Client find(Long id) throws MyEntityNotFoundException {
        Client client = entityManager.find(Client.class, id);
        if (client == null) {
            throw new MyEntityNotFoundException("Client with id " + id + " not found.");
        }
        return client;
    }

    public Client findByEmail(String email) throws MyEntityNotFoundException {
        try {
            return entityManager.createQuery(
                            "SELECT c FROM Client c WHERE c.email = :email", Client.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new MyEntityNotFoundException("Client with email " + email + " not found.");
        } catch (PersistenceException e) {
            throw new MyEntityNotFoundException("Database error occurred while searching for client with email " + email);
        }
    }



    public void delete(Long id) throws MyEntityNotFoundException {
        Client client = entityManager.find(Client.class, id);
        if (client == null) {
            throw new MyEntityNotFoundException("Client with id " + id + " not found.");
        }
        entityManager.remove(client);
    }
}
