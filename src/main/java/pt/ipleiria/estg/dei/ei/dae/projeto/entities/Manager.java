package pt.ipleiria.estg.dei.ei.dae.projeto.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllManagers",
                query = "SELECT m FROM Manager m ORDER BY m.name" // JPQL
        )
})
public class Manager extends User {

    public Manager(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    public Manager() {
    }
}
