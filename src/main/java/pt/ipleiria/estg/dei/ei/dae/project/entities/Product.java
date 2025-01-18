package pt.ipleiria.estg.dei.ei.dae.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.ProductVolumeMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.code"
        )
})
public class Product extends Versionable {

    @Id
    private long code;

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    @NotNull
    @Positive
    private float price;

    @NotBlank
    private String description;

    @NotNull
    @ManyToOne
    private ProductType type;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVolumeMapping> productVolumeMappings;

    public Product(long code, String name, String brand, float price, String description, ProductType type) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.type = type;
        this.productVolumeMappings = new ArrayList<>();
    }

    public Product() {
        this.productVolumeMappings = new ArrayList<>();
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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

    public List<ProductVolumeMapping> getProductVolumeMappings() {
        return new ArrayList<>(productVolumeMappings);
    }

    public void addProductVolumeMapping(ProductVolumeMapping productVolumeMapping) {
        if (productVolumeMapping == null || productVolumeMappings.contains(productVolumeMapping)) {
            return;
        }
        productVolumeMappings.add(productVolumeMapping);
    }

    public void removeProductVolumeMapping(ProductVolumeMapping productVolumeMapping) {
        if (productVolumeMapping == null || !productVolumeMappings.contains(productVolumeMapping)) {
            return;
        }
        productVolumeMappings.remove(productVolumeMapping);
    }

    public long getAmount(Volume volume) {
        if (volume == null) {
            throw new IllegalArgumentException("Volume cannot be null");
        }
        return productVolumeMappings.stream()
                .filter(mapping -> mapping.getVolume().equals(volume))
                .mapToLong(ProductVolumeMapping::getAmount)
                .findFirst()
                .orElse(0L);
    }
    public void addVolumeMapping(ProductVolumeMapping mapping) {
        productVolumeMappings.add(mapping); // Add to the collection
        mapping.getVolume().getProducts().add(mapping); // Maintain bidirectional consistency
    }

    public void removeVolumeMapping(ProductVolumeMapping mapping) {
        this.productVolumeMappings.remove(mapping);
        mapping.getVolume().getProducts().remove(mapping);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Product && this.getCode() == ((Product) obj).getCode();
    }

    @Override
    public String toString() {
        return "Product{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}
