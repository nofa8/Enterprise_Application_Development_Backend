package pt.ipleiria.estg.dei.ei.dae.projeto.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packagetypes")
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

    @ManyToMany
    @JoinTable(
            name = "package_sensor_mapping",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "sensor_id")
    )
    private List<SensorsType> sensors;


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

    public void addSensorsType(SensorsType sensor) {
        if ( sensor == null || sensors.contains(sensor)  ){
            return;
        }

        sensors.add(sensor);
    }

    public void removeSensorsType(SensorsType sensor) {
        if ( sensor == null || !sensors.contains(sensor)  ){
            return;
        }
        sensors.remove(sensor);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public List<SensorsType> getSensors() {
        return new ArrayList<>(sensors);
    }


}