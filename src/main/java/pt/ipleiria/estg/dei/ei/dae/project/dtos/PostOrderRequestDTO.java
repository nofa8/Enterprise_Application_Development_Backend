package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class PostOrderRequestDTO implements Serializable {

    @NotNull
    private int code;

    @NotNull
    private String price;

    @NotNull
    private int clientCode;

    @NotNull
    private String state;

    @NotNull
    private String purchaseDate;

    public PostOrderRequestDTO(int code, String price, int clientCode, String state, String purchaseDate) {
        this.code = code;
        this.price = price;
        this.clientCode = clientCode;
        this.state = state;
        this.purchaseDate = purchaseDate;
    }

    public PostOrderRequestDTO() {
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getClientCode() {
        return clientCode;
    }

    public void setClientCode(int clientCode) {
        this.clientCode = clientCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
