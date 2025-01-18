package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.OrderState;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersClientDTO implements Serializable {

    private long code;
    private float price;
    private OrderState state;
    private String purchaseDate;  // Updated to String to match the desired format
    private String lastUpdate;    // Updated to String to match the desired format

    // Constructor
    public OrdersClientDTO(long code, float price, OrderState state, String purchaseDate, String lastUpdate) {
        this.code = code;
        this.price = price;
        this.state = state;
        this.purchaseDate = purchaseDate;
        this.lastUpdate = lastUpdate;
    }

    public OrdersClientDTO() {
    }

    // Getter and Setter methods
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // Utility method to format Date to String with the desired format
    private static String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            return sdf.format(date);
        }
        return null;
    }

    // Method to convert from Order entity to DTO
    public static OrdersClientDTO from(Order order) {
        String formattedPurchaseDate = formatDate(order.getPurchaseDate());
        String formattedLastUpdate = formatDate(order.getTimestamp());
        return new OrdersClientDTO(
                order.getCode(),
                order.getPrice(),
                order.getState(),
                formattedPurchaseDate,
                formattedLastUpdate
        );
    }
    public static List<OrdersClientDTO> from(List<Order> orders) {
        return orders.stream().map(OrdersClientDTO::from).collect(Collectors.toList());
    }
}
