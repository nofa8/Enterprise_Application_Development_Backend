package pt.ipleiria.estg.dei.ei.dae.project.dtos;



import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientNoDTO implements Serializable {

    private long id; // Using id as the primary key
    private String email; // Email remains unique, but it's not the primary key anymore
    @JsonIgnore
    private String password;
    private String name;


    public ClientNoDTO(long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;

    }

    public ClientNoDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public static ClientNoDTO from(Client client) {
        return new ClientNoDTO(
                client.getId(),  // Use the new ID field
                client.getEmail(),
                client.getPassword(),
                client.getName()
        );
    }

    // Converts an entire list of entities into a list of DTOs
    public static List<ClientNoDTO> from(List<Client> clients) {
        return clients.stream().map(ClientNoDTO::from).collect(Collectors.toList());
    }


}
