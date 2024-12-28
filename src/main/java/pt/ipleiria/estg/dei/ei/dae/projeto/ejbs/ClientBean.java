package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Manager;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.projeto.security.Hasher;

import java.util.List;

@Stateless
public class ClientBean {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public void update(String username, String password, String name, String email) throws  MyConstraintViolationException{
        Client client = entityManager.find(Client.class, username);
        if (client == null){
            return;
        }

        try {
            client.setPassword(hasher.hash(password));
            client.setName(name);
            client.setEmail(email);
            entityManager.merge(client);

        }catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }
    public void create(String email, String password, String name) throws MyEntityExistsException, MyConstraintViolationException {
        if (entityManager.find(Client.class, email) != null) {
            throw new MyEntityExistsException("Client  '"+email+"' already exists!");
        }

        try {
            var client = new Client( email, hasher.hash(password), name);
            entityManager.persist(client);
            entityManager.flush(); // when using Hibernate, to force it to throw ConstraintViolationException, as in the JPA specification
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
    public List<Client> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllClients", Client.class).getResultList();
    }


    public Client find(String email) throws  MyEntityNotFoundException {
        var client = entityManager.find(Client.class,email);
        if (client == null) {
            throw new MyEntityNotFoundException("client " + email + " not found");
        }
        return client;
    }

    public void delete(String client) {
        entityManager.remove(entityManager.find(Client.class, client));
    }


}
