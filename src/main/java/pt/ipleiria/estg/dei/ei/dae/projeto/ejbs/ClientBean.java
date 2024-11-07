package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Manager;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ClientBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void update(String username, String password, String name, String email){
        Client client = entityManager.find(Client.class, username);
        if (client == null){
            return;
        }
        client.setPassword(password);
        client.setName(name);
        client.setEmail(email);

        entityManager.merge(client);
    }
    public void create(String username, String password, String name,
                       String email) throws MyEntityExistsException, MyConstraintViolationException {
        if (entityManager.find(Client.class, username) != null) {
            throw new MyEntityExistsException("Client  '"+username+"' already exists!");
        }

        try {
            var client = new Client(username, password, name, email);
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

    public Client find(String username) {
        var client = entityManager.find(Client.class,username);
        if (client == null) {
            throw new RuntimeException("client " + username + " not found");
        }
        return client;
    }

    public void delete(String client) {
        entityManager.remove(entityManager.find(Client.class, client));
    }

}
