package pt.ipleiria.estg.dei.ei.dae.project.entities.composites;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductVolumeKey implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "volume_id")
    private Long volumeId;

    public ProductVolumeKey() {
    }

    public ProductVolumeKey(Long productId, Long volumeId) {
        this.productId = productId;
        this.volumeId = volumeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Long volumeId) {
        this.volumeId = volumeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVolumeKey that = (ProductVolumeKey) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(volumeId, that.volumeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, volumeId);
    }

    @Override
    public String toString() {
        return "ProductVolumeKey{" +
                "productId=" + productId +
                ", sensorId=" + volumeId +
                '}';
    }
}
