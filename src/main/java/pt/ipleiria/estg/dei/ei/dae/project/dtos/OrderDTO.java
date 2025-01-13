package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.OrderState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO implements Serializable {

    private long code;
    private float price;
    private long clientId;
    private OrderState state;
    private Date purchaseDate;
    private Date lastUpdate;
    @JsonInclude(JsonInclude.Include.NON_EMPTY) // Só inclui se não for null
    private List<VolumeDTO> volumes;

    public OrderDTO(long code, float price, long clientCode, OrderState state, Date purchaseDate, Date lastUpdate) {
        this.code = code;
        this.price = price;
        this.clientId = clientCode;
        this.state = state;
        this.purchaseDate = purchaseDate;
        this.lastUpdate = lastUpdate;
        this.volumes = new ArrayList<>();
    }

    public OrderDTO() {
        this.volumes = new ArrayList<>();
    }

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

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<VolumeDTO> getVolumes() {
        return new ArrayList<>(volumes);
    }

    public void setVolumes(List<VolumeDTO> volumes) {
        if (volumes == null){
            return;
        }
        this.volumes = new ArrayList<>(volumes);
    }

    public static OrderDTO from(Order order) {
        OrderDTO dto = new OrderDTO(
                order.getCode(),
                order.getPrice(),
                order.getClientId(),
                order.getState(),
                order.getPurchaseDate(),
                order.getTimestamp()
        );
        //dto.setVolumes(VolumeDTO.from(order.getVolumes()));
        return dto;
    }

    public static List<OrderDTO> from(List<Order> orders) {
        return orders.stream().map(OrderDTO::from).collect(Collectors.toList());
    }
}
