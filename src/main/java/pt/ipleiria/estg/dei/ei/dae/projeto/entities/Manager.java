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

    public Manager(String email, String password, String name) {
        super(email, password, name);
    }

    public Manager() {
    }
}
