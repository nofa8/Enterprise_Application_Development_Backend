package pt.ipleiria.estg.dei.ei.dae.projeto.entities;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClient",
                query = "SELECT c FROM Client c ORDER BY c.name" // JPQL
        )
})
public class Client extends  User {

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    public Client(String username, String password, String name, String email) {
        super(username, password, name, email);
        orders = new ArrayList<>();
    }

    public Client() {
        orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
}
