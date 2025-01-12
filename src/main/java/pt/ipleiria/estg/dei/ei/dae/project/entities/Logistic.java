package pt.ipleiria.estg.dei.ei.dae.project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllLogistics",
                query = "SELECT c FROM Logistic c ORDER BY c.name" // JPQL
        )
})
public class Logistic extends  User {
    public Logistic(String email, String password, String name) {
        super(email, password, name);
    }

    public Logistic() {
    }
}

