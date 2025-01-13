package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.OrderState;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderClientDTO implements Serializable {
    private long code;
    private float price;
    private ClientDTO client;  // Updated to use ClientDTO
    private OrderState state;
    private String purchaseDate;  // Updated to String to match the desired format
    private String lastUpdate;    // Updated to String to match the desired format
    private List<VolumeDTO> volumes;

    // Constructor
    public OrderClientDTO(long code, float price, OrderState state, String purchaseDate, String lastUpdate) {
        this.code = code;
        this.price = price;
        this.state = state;
        this.purchaseDate = purchaseDate;
        this.lastUpdate = lastUpdate;
        this.volumes = new ArrayList<>();
    }

    // Empty constructor
    public OrderClientDTO() {
        this.volumes = new ArrayList<>();
    }

    // Getter and Setter methods

    public long getCode() {
        return code;
    }

    public float getPrice() {
        return price;
    }

    public OrderState getState() {
        return state;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public List<VolumeDTO> getVolumes() {
        return new ArrayList<>(volumes);
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
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
    public static OrderClientDTO from(Order order) {
        String formattedPurchaseDate = formatDate(order.getPurchaseDate());
        String formattedLastUpdate = formatDate(order.getTimestamp());
        OrderClientDTO orderClientDTO = new OrderClientDTO(
                order.getCode(),
                order.getPrice(),
                order.getState(),
                formattedPurchaseDate,
                formattedLastUpdate
        );
        //orderClientDTO.setVolumes(VolumeDTO.from(order.getVolumes()));
        return orderClientDTO;
    }

    public static List<OrderClientDTO> from(List<Order> orders) {
        return orders.stream().map(OrderClientDTO::from).collect(Collectors.toList());
    }
}