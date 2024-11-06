package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.OrderState;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ClientBean clientBean;

    public void create(long code, OrderState state, Date purchaseDate, Date timestamp, long clientCode)
            throws MyEntityExistsException, MyEntityNotFoundException {

        if (entityManager.find(Order.class, code) != null){
            throw new MyEntityNotFoundException("Order with code " + code + " not found");
        }

        Client client = entityManager.find(Client.class, clientCode);
        if ( client == null){
            throw new MyEntityNotFoundException("Client with code " + code + " not found");
        }

        var order = new Order(code,  state, purchaseDate, timestamp,  client);

        entityManager.persist(order);
    }

    public List<Order> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }
    public Order find(long id) {
        var order = entityManager.find(Order.class,id);
        if (order == null) {
            throw new RuntimeException("Order " + id + " not found");
        }
        return order;
    }

    public void delete(long order) {
        entityManager.remove(entityManager.find(Order.class, order));
    }

    public void update(long code, OrderState state, Date timestamp, String username) {
        Order order = entityManager.find(Order.class, code);
        if (order == null || !entityManager.contains(order)){
            return;
        }
        order.setState(state);
        order.setTimestamp(timestamp);
        order.setClient(clientBean.find(username));
        entityManager.merge(order);
    }

}
