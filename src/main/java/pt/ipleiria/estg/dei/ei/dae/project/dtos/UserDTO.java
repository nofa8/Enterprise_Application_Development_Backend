package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO(String email, String name, String role, Long id) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.id = id;
    }

    public static UserDTO from(User user) {
        return new UserDTO(
                user.getEmail(),
                user.getName(),
                Hibernate.getClass(user).getSimpleName(),
                user.getId()
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