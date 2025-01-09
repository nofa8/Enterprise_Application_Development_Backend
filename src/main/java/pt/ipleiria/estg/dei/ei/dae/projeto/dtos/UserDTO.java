package pt.ipleiria.estg.dei.ei.dae.projeto.dtos;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private String name;
    private String email;
    private String role;

    public UserDTO() {
    }

    public UserDTO( String email,String name, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static UserDTO from(User user) {
        return new UserDTO(
                user.getEmail(),
                user.getName(),
                Hibernate.getClass(user).getSimpleName()
        );
    }

    public static List<UserDTO> from(List<User> users) {
        return users.stream().map(UserDTO::from).collect(Collectors.toList());
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}