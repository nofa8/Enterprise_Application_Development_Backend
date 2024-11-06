package pt.ipleiria.estg.dei.ei.dae.projeto.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.ProductType;

import java.util.Objects;

@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
    }
)
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

    //private float price;



    @NotNull
    private ProductType type;

    @ManyToOne
    @NotNull
    private Volume volume;

    public Product(long code, String name, String brand, ProductType type, Volume volume) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.volume = volume;
    }

    public Product() {
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
