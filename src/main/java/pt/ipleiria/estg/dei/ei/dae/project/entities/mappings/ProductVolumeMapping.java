package pt.ipleiria.estg.dei.ei.dae.project.entities.mappings;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.project.entities.*;

import java.util.Objects;

import pt.ipleiria.estg.dei.ei.dae.project.entities.composites.ProductVolumeKey;

@Entity
@Table(name = "product_volume_mapping")
public class ProductVolumeMapping {

    @EmbeddedId
    private ProductVolumeKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("volumeId")
    @JoinColumn(name = "volume_id")
    private Volume volume;

    @Column(nullable = false)
    @Min(1)
    private int amount;

    public ProductVolumeMapping() {
    }

    public ProductVolumeMapping(Product product, Volume volume, int quantity) {
        this.product = product;
        this.volume = volume;
        this.amount = quantity;
        this.id = new ProductVolumeKey(product.getCode(), volume.getCode());
    }

    public ProductVolumeKey getId() {
        return id;
    }

    public void setId(ProductVolumeKey id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct( Product product) {
        this.product = product;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVolumeMapping that = (ProductVolumeMapping) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductVolumeMapping{" +
                "product=" + product.getCode() +
                ", sensor=" + volume.getCode() +
                ", quantity=" + amount +
                '}';
    }
}

