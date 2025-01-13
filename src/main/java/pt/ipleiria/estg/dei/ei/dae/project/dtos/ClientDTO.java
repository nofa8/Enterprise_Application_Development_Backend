package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO implements Serializable {

    private long id; // Using id as the primary key
    private String email; // Email remains unique, but it's not the primary key anymore
    @JsonIgnore
    private String password;
    private String name;
    private List<OrderDTO> ordersDTO;

    public ClientDTO(long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        ordersDTO = new ArrayList<>();
    }

    public ClientDTO() {
        ordersDTO = new ArrayList<>();
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

    public List<OrderDTO> getOrdersDTO() {
        return new ArrayList<>(ordersDTO);
    }
    public void setOrdersDTO(List<OrderDTO> ordersDTO) {
        if (ordersDTO == null) {
            return;
        }

        this.ordersDTO = new ArrayList<>(ordersDTO);
    }


    public static ClientDTO from(Client client) {
        return new ClientDTO(
                client.getId(),  // Use the new ID field
                client.getEmail(),
                client.getPassword(),
                client.getName()
        );
    }

    // Converts an entire list of entities into a list of DTOs
    public static List<ClientDTO> from(List<Client> clients) {
        return clients.stream().map(ClientDTO::from).collect(Collectors.toList());
    }


}
