package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.OrderState;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ClientBean clientBean;

    public void create(long code, float price, OrderState state, Date purchaseDate, Date timestamp, long clientId)
            throws MyEntityExistsException, MyEntityNotFoundException,MyConstraintViolationException {

        if (entityManager.find(Order.class, code) != null) {
            throw new MyEntityExistsException("Order with code " + code + " already exists");
        }

        Client client = entityManager.find(Client.class, clientId);
        if (client == null) {
            throw new MyEntityNotFoundException("Client with email " + clientId + " not found");
        }
        try {
            var order = new Order(code, price, state, purchaseDate, timestamp, client);

            entityManager.persist(order);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Order> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }
    public Order find(long id) throws  MyEntityNotFoundException {
        var order = entityManager.find(Order.class,id);
        if (order == null) {
            throw new MyEntityNotFoundException("Order " + id + " not found");
        }
        return order;
    }

    public List<Order> findByClientEmail(String clientEmail) {
        return entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.client.email = :email ORDER BY o.code", Order.class)
                .setParameter("email", clientEmail)
                .getResultList();
    }


    public void delete(long order) {
        entityManager.remove(entityManager.find(Order.class, order));
    }

    public void update(long code,float price, OrderState state, Date timestamp, long clientId) throws MyEntityNotFoundException, MyConstraintViolationException {
        Order order = entityManager.find(Order.class, code);
        if (order == null || !entityManager.contains(order)){
            return;
        }

        try{
            order.setPrice(price);
            order.setState(state);
            order.setTimestamp(timestamp);
            order.setClient(clientBean.find(clientId));
            entityManager.merge(order);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

    }

}
