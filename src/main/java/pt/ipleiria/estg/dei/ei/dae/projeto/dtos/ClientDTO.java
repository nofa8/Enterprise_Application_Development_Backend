package pt.ipleiria.estg.dei.ei.dae.projeto.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO implements Serializable {

    @Id
    private String email;

    private String password;
    private String name;
    private List<Order> orders;

    public ClientDTO( String email, String password, String name) {
        this.password = password;
        this.name = name;
        this.email = email;
        orders = new ArrayList<>();
    }

    public ClientDTO() {
        orders = new ArrayList<>();
    }




    public List<Order> getOrders() {
        return  new ArrayList<>(orders);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static ClientDTO from(Client client) {
        return new ClientDTO(
                client.getEmail(),
                client.getPassword(),
                client.getName()
        );
    }
    // converts an entire list of entities into a list of DTOs
    public static List<ClientDTO> from(List<Client> clients) {
        return clients.stream().map(ClientDTO::from).collect(Collectors.toList());
    }
}
