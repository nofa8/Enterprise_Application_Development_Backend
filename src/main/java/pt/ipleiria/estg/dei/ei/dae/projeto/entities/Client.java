package pt.ipleiria.estg.dei.ei.dae.projeto.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClients",
                query = "SELECT c FROM Client c ORDER BY c.name" // JPQL
        )
})
public class Client extends  User {
    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    public Client( String email, String password, String name) {
        super( email, password, name);
        orders = new ArrayList<>();
    }

    public Client() {
        orders = new ArrayList<>();
    }


    public void addOrder(Order order) {
        if ( order == null || orders.contains(order)  ){
            return;
        }
        orders.add(order);
    }

    public void removeOrder(Order order) {
        if ( order == null || !orders.contains(order)  ){
            return;
        }
        orders.remove(order);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
}
