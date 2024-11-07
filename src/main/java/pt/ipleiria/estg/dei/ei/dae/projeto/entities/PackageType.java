package pt.ipleiria.estg.dei.ei.dae.projeto.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.SensorsType;

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

    @ElementCollection(targetClass = SensorsType.class)
    @CollectionTable(name = "package_sensors", joinColumns = @JoinColumn(name = "package_code"))
    @Column(name = "sensor_type")
    @Enumerated(EnumType.STRING) // Stores the enum values as strings
    private List<SensorsType> sensors;


    @OneToMany(mappedBy = "typePackage")
    private List<Volume> volumes;


    public PackageType(long code) {
        this.code = code;
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