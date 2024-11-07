package pt.ipleiria.estg.dei.ei.dae.projeto.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.code" // IF needed order by something else
        )
    }
)
public class Product extends Versionable {

    @Id
    private long  code;
    @NotBlank
    private String name;
    @NotBlank
    private String brand;

    @NotEmpty
    private float price;

    @NotBlank
    private String description;

    @NotNull
    @ManyToOne
    private ProductType type;

    @ManyToOne
    @NotNull
    private Volume volume;

    public Product(long code, String name, String brand, float price, String description, ProductType type, Volume volume) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.type = type;
        this.volume = volume;
    }

    public Product() {
    }


    @NotEmpty
    public float getPrice() {
        return price;
    }

    public void setPrice(@NotEmpty float price) {
        this.price = price;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public @NotNull ProductType getType() {
        return type;
    }

    public void setType(@NotNull ProductType type) {
        this.type = type;
    }

    public @NotNull Volume getVolume() {
        return volume;
    }

    public void setVolume(@NotNull Volume volume) {
        this.volume = volume;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getBrand() {
        return brand;
    }

    public void setBrand(@NotBlank String brand) {
        this.brand = brand;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode());
    }

    @Override
    public boolean equals(Object obj) {
        return this.getCode() == ((Product)obj).getCode();
    }

}
