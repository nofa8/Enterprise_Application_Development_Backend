package pt.ipleiria.estg.dei.ei.dae.project.entities.composites;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductSensorKey implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "sensor_id")
    private Long sensorId;

    public ProductSensorKey() {
    }

    public ProductSensorKey(Long productId, Long sensorId) {
        this.productId = productId;
        this.sensorId = sensorId;
    }

    // Getters and setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSensorKey that = (ProductSensorKey) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(sensorId, that.sensorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, sensorId);
    }

    @Override
    public String toString() {
        return "ProductSensorKey{" +
                "productId=" + productId +
                ", sensorId=" + sensorId +
                '}';
    }
}
