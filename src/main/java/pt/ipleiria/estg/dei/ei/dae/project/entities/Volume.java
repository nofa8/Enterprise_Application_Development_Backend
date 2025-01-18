package pt.ipleiria.estg.dei.ei.dae.project.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.ProductVolumeMapping;

//ups
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
//ups
import java.util.*;

@Entity
@Table(name = "volumes")
@NamedQueries({
        @NamedQuery(
                name = "getAllVolumes",
                query = "SELECT v FROM Volume v ORDER BY v.code"
        )
}
)
public class Volume extends Versionable {
    @Id
    private long  code;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VolumeState state;

    // criar classe com sensores associados
    @NotNull
    @ManyToOne
    private PackageType typePackage;

    @NotNull
    @ManyToOne
    private Order order;

    @NotNull
    private Date timestamp;

    @NotNull
    @OneToMany(mappedBy = "volume")
    private List<Sensor> sensors;


    @OneToMany(mappedBy = "volume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVolumeMapping> products;


    public Volume(long code, VolumeState state, PackageType typePackage, Order order, Date timestamp) {
        this.code = code;
        this.state = state;
        this.typePackage = typePackage;
        this.order = order;
        this.timestamp = timestamp;
        sensors = new ArrayList<>();
        products = new ArrayList<>();
    }

    public Volume() {
        sensors = new ArrayList<>();
        products = new ArrayList<>();
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            return;
        }

        // Check if the product is already associated with this sensor
        boolean exists = products.stream()
                .anyMatch(mapping -> mapping.getProduct().equals(product));

        if (exists) {
            return;
        }

        // Add new mapping
        ProductVolumeMapping mapping = new ProductVolumeMapping(product, this, quantity);
        products.add(mapping);
    }

    public void updateProductQuantity(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        // Find the mapping for the specified product
        ProductVolumeMapping mapping = products.stream()
                .filter(m -> m.getProduct().equals(product))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Product is not associated with this volume."));

        // Update the quantity
        mapping.setAmount(quantity);
    }

    public void removeProduct(Product product) {
        if (product == null) {
            return;
        }

        // Find the mapping for the specified product
        ProductVolumeMapping mapping = products.stream()
                .filter(m -> m.getProduct().equals(product))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Product is not associated with this sensor."));

        // Remove the mapping
        products.remove(mapping);
    }

    public void addSensor(Sensor sensor) {
        if ( sensor == null || sensors.contains(sensor)  ){
            return;
        }

        sensors.add(sensor);
    }

    public void removeSensor(Sensor sensor) {

        if ( sensor == null || !sensors.contains(sensor)  ){
            return;
        }

        sensors.remove(sensor);
    }

    public void addProductMapping(ProductVolumeMapping mapping) {
        products.add(mapping);
        mapping.getProduct().getProductVolumeMappings().add(mapping);
    }

    public void removeProductMapping(ProductVolumeMapping mapping) {
        this.products.remove(mapping);
        mapping.getProduct().getProductVolumeMappings().remove(mapping);
    }




    public @NotNull Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NotNull Date timestamp) {
        this.timestamp = timestamp;
    }

    public @NotNull PackageType getTypePackage() {
        return typePackage;
    }

    public void setTypePackage(@NotNull PackageType typePackage) {
        this.typePackage = typePackage;
    }

    public @NotNull List<Sensor> getSensors() {
        return new ArrayList<>(sensors);
    }

//eu usei isto
    public List<Sensor> getSensorsByType(String type) {
        return sensors.stream()
                .filter(sensor -> sensor.getType() != null &&
                        sensor.getType().getName() != null &&
                        type.equals(sensor.getType().getName()))
                .collect(Collectors.toList());
    }



    public @NotNull List<ProductVolumeMapping> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public @NotNull Order getOrder() {
        return order;
    }

    public void setOrder(@NotNull Order order) {
        this.order = order;
    }

    public @NotNull VolumeState getState() {
        return state;
    }

    public void setState(@NotNull VolumeState state) {
        this.state = state;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode());
    }

    @Override
    public boolean equals(Object obj) {
        return this.getCode() == ((Volume)obj).getCode();
    }
}
