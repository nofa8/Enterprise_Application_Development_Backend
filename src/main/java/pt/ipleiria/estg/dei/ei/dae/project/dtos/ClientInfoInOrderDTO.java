package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientInfoInOrderDTO implements Serializable {

    @Id
    private String email;

    private String name;

    public ClientInfoInOrderDTO( String email, String name) {

        this.name = name;
        this.email = email;

    }

    public ClientInfoInOrderDTO() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static ClientInfoInOrderDTO from(Client client) {
        return new ClientInfoInOrderDTO(
                client.getEmail(),
                client.getName()
        );
    }
    // converts an entire list of entities into a list of DTOs
    public static List<ClientInfoInOrderDTO> from(List<Client> clients) {
        return clients.stream().map(ClientInfoInOrderDTO::from).collect(Collectors.toList());
    }
}
