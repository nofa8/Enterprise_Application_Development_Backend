package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.OrderState;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderManagerDTO implements Serializable {
    private long code;
    private float price;
    private OrderState state;
    private ClientInfoInOrderDTO client;
    private String lastUpdate;
    private String purchaseDate;
    private List<VolumeDTO> volumes;

    // Constructor
    public OrderManagerDTO(long code, float price, OrderState state, ClientInfoInOrderDTO client, String purchaseDate, String lastUpdate) {
        this.code = code;
        this.price = price;
        this.state = state;
        this.client = client;
        this.purchaseDate = purchaseDate;
        this.lastUpdate = lastUpdate;
        this.volumes = new ArrayList<>();
    }

    // Empty constructor
    public OrderManagerDTO() {
        this.volumes = new ArrayList<>();
    }

    // Getter and Setter methods

    public long getCode() {
        return code;
    }

    public float getPrice() {
        return price;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public ClientInfoInOrderDTO getClient() {
        return client;
    }

    public List<VolumeDTO> getVolumes() {
        return new ArrayList<>(volumes);
    }

    public OrderState getState() {
        return state;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setClient(ClientInfoInOrderDTO client) {
        this.client = client;
    }

    public void setVolumes(List<VolumeDTO> volumes) {
        if (volumes == null){
            return;
        }
        this.volumes = new ArrayList<>(volumes);
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
    public static OrderManagerDTO from(Order order) {
        String formattedPurchaseDate = formatDate(order.getPurchaseDate());
        String formattedLastUpdate = formatDate(order.getTimestamp());
        OrderManagerDTO orderManagerDTO = new OrderManagerDTO(
                order.getCode(),
                order.getPrice(),
                order.getState(),
                ClientInfoInOrderDTO.from(order.getClient()),
                formattedPurchaseDate,
                formattedLastUpdate
        );
        orderManagerDTO.setVolumes(VolumeDTO.from(order.getVolumes()));
        return orderManagerDTO;
    }

    public static List<OrderManagerDTO> from(List<Order> orders) {
        return orders.stream().map(OrderManagerDTO::from).collect(Collectors.toList());
    }
}

