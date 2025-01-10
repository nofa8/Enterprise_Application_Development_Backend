package pt.ipleiria.estg.dei.ei.dae.project.entities.mappings;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import pt.ipleiria.estg.dei.ei.dae.project.entities.ProductType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.SensorsType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.composites.ProductSensorKey;

import java.util.Objects;

@Entity
@Table(name = "product_sensor_mapping")
public class ProductSensorMapping {

    @EmbeddedId
    private ProductSensorKey id; // Composite key for the relationship

    @ManyToOne()
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductType product;

    @ManyToOne()
    @MapsId("sensorId")
    @JoinColumn(name = "sensor_id")
    private SensorsType sensor;

    @Column(nullable = false)
    @Min(1)
    private int quantity;

    public ProductSensorMapping() {
    }

    public ProductSensorMapping(ProductType product, SensorsType sensor, int quantity) {
        this.product = product;
        this.sensor = sensor;
        this.quantity = quantity;
        this.id = new ProductSensorKey(product.getCode(), sensor.getId());
    }

    public ProductSensorKey getId() {
        return id;
    }

    public void setId(ProductSensorKey id) {
        this.id = id;
    }

    public ProductType getProduct() {
        return product;
    }

    public void setProduct(ProductType product) {
        this.product = product;
    }

    public SensorsType getSensor() {
        return sensor;
    }

    public void setSensor(SensorsType sensor) {
        this.sensor = sensor;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSensorMapping that = (ProductSensorMapping) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductSensorMapping{" +
                "product=" + product.getCode() +
                ", sensor=" + sensor.getId() +
                ", quantity=" + quantity +
                '}';
    }
}

