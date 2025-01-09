package pt.ipleiria.estg.dei.ei.dae.projeto.dtos;

import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {

    private long code;
    private String name;
    private String brand;
    private long productTypeCode;  // Assuming you want the code of the ProductType entity
    private String description;
    private int amount;

    // Constructors
    public ProductDTO() {
    }

    public ProductDTO(long code, String name, String brand, long productTypeCode, String description, int amount) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.productTypeCode = productTypeCode;
        this.description = description;
        this.amount = amount;
    }

    // Getters and Setters
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(int productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // Static method to convert a Product entity to ProductDTO
    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getCode(),
                product.getName(),
                product.getBrand(),
                product.getType() != null ? product.getType().getCode() : 0,  // Assuming ProductType has a `getCode()` method
                product.getDescription(),
                product.getAmount()
        );
    }

    // Static method to convert a list of Product entities to a list of ProductDTOs
    public static List<ProductDTO> from(List<Product> products) {
        return products.stream().map(ProductDTO::from).collect(Collectors.toList());
    }
}
