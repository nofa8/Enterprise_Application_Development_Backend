package pt.ipleiria.estg.dei.ei.dae.projeto.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.OrderState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
}
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.code"
        )
    }
)
public class Order extends Versionable {
    @Id
    private long  code;


    //private float price;

    @NotNull
    private OrderState state;

    @NotNull
    private Date purchaseDate;

    @NotNull
    private Date timestamp;

    @NotNull
    @OneToMany(mappedBy = "order")
    private List<Volume> volumes;

    @ManyToOne
    private Client  client;

    public Order(long code, OrderState state, Date purchaseDate, Date timestamp, Client client) {
        this.code = code;
        this.state = state;
        this.purchaseDate = purchaseDate;
        this.timestamp = timestamp;
        this.client = client;
        volumes = new ArrayList<>();

    }

    public Order() {
        volumes = new ArrayList<>();
    }

    public @NotNull Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(@NotNull Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public @NotNull OrderState getState() {
        return state;
    }

    public void setState(@NotNull OrderState state) {
        this.state = state;
    }

    public @NotNull Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NotNull Date timestamp) {
        this.timestamp = timestamp;
    }

    public @NotNull List<Volume> getVolumes() {
        return new ArrayList<>(volumes);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode());
    }

    @Override
    public boolean equals(Object obj) {
        return this.getCode() == ((Order)obj).getCode();
    }
}
