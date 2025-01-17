package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.OrderState;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PostOrderRequestDTO implements Serializable {

    @NotNull
    private long code;

    @NotNull
    private float price;

    @NotNull
    private long clientCode;

    @NotNull
    private OrderState state;

    @NotNull
    private Date purchaseDate;

    public PostOrderRequestDTO(long code, float price, long clientCode, OrderState state, Date purchaseDate) {
        this.code = code;
        this.price = price;
        this.clientCode = clientCode;
        this.state = state;
        this.purchaseDate = purchaseDate;
    }

    public PostOrderRequestDTO() {
    }

    // Getters and Setters
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }



    public long getClientCode() {
        return clientCode;
    }

    public void setClientCode(long clientCode) {
        this.clientCode = clientCode;
    }

    @NotNull
    public float getPrice() {
        return price;
    }

    public void setPrice(@NotNull float price) {
        this.price = price;
    }

    public @NotNull OrderState getState() {
        return state;
    }

    public void setState(@NotNull OrderState state) {
        this.state = state;
    }

    public @NotNull Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(@NotNull Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public static PostOrderRequestDTO from(Order order) {
        PostOrderRequestDTO dto = new PostOrderRequestDTO(
                order.getCode(),
                order.getPrice(),
                order.getClientId(),
                order.getState(),
                order.getPurchaseDate()
        );
        return dto;
    }

    public static List<PostOrderRequestDTO> from(List<Order> orders) {
        return orders.stream().map(PostOrderRequestDTO::from).collect(Collectors.toList());
    }


}
