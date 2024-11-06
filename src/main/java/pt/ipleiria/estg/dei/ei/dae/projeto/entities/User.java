package pt.ipleiria.estg.dei.ei.dae.projeto.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = "email")}
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Extra: try the other strategies… what happens to the database?
public class User  extends Versionable{


    @Id
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;
    // Constructor with arguments ...
    // Constructor without arguments...
    // Getters and Setters ...


    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }


    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @Email @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotNull String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUsername());
    }

    @Override
    public boolean equals(Object obj) {
        return this.getUsername() == ((User)obj).getUsername();
    }

}