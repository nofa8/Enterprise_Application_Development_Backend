package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


public class PostVolumeProductDTO implements Serializable {

    private long code;
    private int amount;

    // Constructors
    public PostVolumeProductDTO() {
    }

    public PostVolumeProductDTO(long code,int amount) {
        this.code = code;

        this.amount = amount;
    }

    // Getters and Setters
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // Static method to convert a Product entity to ProductDTO
    public static PostVolumeProductDTO from(Product product) {
        return new PostVolumeProductDTO(
                product.getCode(),
                product.getAmount()
        );
    }

    // Static method to convert a list of Product entities to a list of ProductDTOs
    public static List<PostVolumeProductDTO> from(List<Product> products) {
        return products.stream().map(PostVolumeProductDTO::from).collect(Collectors.toList());
    }
}