package pt.ipleiria.estg.dei.ei.dae.project.entities.composites;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PackageSensorKey implements Serializable {

    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "sensor_id")
    private Long sensorId;

    public PackageSensorKey() {}

    public PackageSensorKey(Long packageId, Long sensorId) {
        this.packageId = packageId;
        this.sensorId = sensorId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
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
        PackageSensorKey that = (PackageSensorKey) o;
        return Objects.equals(packageId, that.packageId) && Objects.equals(sensorId, that.sensorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageId, sensorId);
    }
}
