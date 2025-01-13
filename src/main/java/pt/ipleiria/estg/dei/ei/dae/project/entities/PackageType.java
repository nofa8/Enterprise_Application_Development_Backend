package pt.ipleiria.estg.dei.ei.dae.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.PackageSensorMapping;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.ProductSensorMapping;

import java.util.*;

@Entity
@Table(name = "package_types")
@NamedQueries({
        @NamedQuery(
                name = "getAllPackageTypes",
                query = "SELECT p FROM PackageType p ORDER BY p.code" // IF needed order by something else
        )
}
)
public class PackageType extends Versionable {

    @Id
    private long code;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "packageType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PackageSensorMapping> sensors;


    @OneToMany(mappedBy = "typePackage")
    private List<Volume> volumes;


    public PackageType(long code, String name) {
        this.code = code;
        this.name = name;
        this.sensors = new ArrayList<>();
        this.volumes = new ArrayList<>();
    }

    public PackageType() {
        this.sensors = new ArrayList<>();
        this.volumes = new ArrayList<>();
    }

    public void addVolume(Volume volume) {
        if ( volume == null || volumes.contains(volume)  ){
            return;
        }

        volumes.add(volume);
    }

    public void removeVolume(Volume sensor) {

        if ( sensor == null || !volumes.contains(sensor)  ){
            return;
        }

        volumes.remove(sensor);
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }



    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }


    public List<PackageSensorMapping> getSensors() {
        // Return an unmodifiable list of SensorsType extracted from the ProductSensorMapping
        return Collections.unmodifiableList(sensors);
    }
    public void addSensor(SensorsType sensor, int quantity) {
        if (sensor == null) {
            throw new IllegalArgumentException("Sensor cannot be null");
        }

        boolean exists = sensors.stream()
                .anyMatch(mapping -> mapping.getSensor().equals(sensor));
        if (exists) {
            throw new IllegalStateException("Sensor is already associated with this package.");
        }

        PackageSensorMapping mapping = new PackageSensorMapping(this, sensor, quantity);
        sensors.add(mapping);
    }

    public void updateSensorQuantity(SensorsType sensor, int quantity) {
        if (sensor == null) {
            throw new IllegalArgumentException("Sensor cannot be null");
        }

        PackageSensorMapping mapping = sensors.stream()
                .filter(m -> m.getSensor().equals(sensor))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Sensor is not associated with this package."));
        mapping.setQuantity(quantity);
    }

    public Map<Long, Integer> getSensorQuantities() {
        Map<Long, Integer> sensorQuantities = new HashMap<>();
        for (PackageSensorMapping map : sensors) {
            Long sensorTypeCode = map.getSensor().getId();
            int quantity = map.getQuantity();

            sensorQuantities.merge(sensorTypeCode, quantity, Integer::sum);
        }
        return sensorQuantities;
    }


    public void removeSensor(SensorsType sensor) {
        if (sensor == null) {
            return;
        }

        PackageSensorMapping mapping = sensors.stream()
                .filter(m -> m.getSensor().equals(sensor))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Sensor is not associated with this package."));
        sensors.remove(mapping);
    }

}