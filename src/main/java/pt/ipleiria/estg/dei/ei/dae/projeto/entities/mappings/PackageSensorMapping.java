package pt.ipleiria.estg.dei.ei.dae.projeto.entities.mappings;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.SensorsType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.composites.PackageSensorKey;

@Entity
@Table(name = "package_sensor_mapping")
public class PackageSensorMapping {

    @EmbeddedId
    private PackageSensorKey id; // Composite key

    @ManyToOne
    @MapsId("packageId")
    @JoinColumn(name = "package_id")
    private PackageType packageType;

    @ManyToOne
    @MapsId("sensorId")
    @JoinColumn(name = "sensor_id")
    private SensorsType sensor;

    @Column(nullable = false)
    @Min(1)
    private int quantity;

    public PackageSensorMapping() {}

    public PackageSensorMapping(PackageType packageType, SensorsType sensor, int quantity) {
        this.packageType = packageType;
        this.sensor = sensor;
        this.quantity = quantity;
        this.id = new PackageSensorKey(packageType.getCode(), sensor.getId());
    }

    public PackageSensorKey getId() {
        return id;
    }

    public void setId(PackageSensorKey id) {
        this.id = id;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
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
}
