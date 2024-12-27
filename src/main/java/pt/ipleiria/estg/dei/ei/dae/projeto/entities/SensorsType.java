package pt.ipleiria.estg.dei.ei.dae.projeto.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sensor_types")
@NamedQueries({
        @NamedQuery(
                name = "getAllSensorTypes",
                query = "SELECT s FROM SensorsType s ORDER BY s.name" // JPQL
        )
})
public class SensorsType {

    @Id
    private long id;

    @NotBlank
    private String name;

    @OneToMany( mappedBy = "type")
    private List<Sensor> sensors;

    // Constructors, getters, and setters
    public SensorsType() {
        sensors = new ArrayList<>();

    }

    public SensorsType(long id, String name) {
        this.id = id;
        this.name = name;
        sensors = new ArrayList<>();
    }

    public void addSensor(Sensor sensor) {
        if ( sensor == null || sensors.contains(sensor)  ){
            return;
        }
        sensors.remove(sensor);
    }
    public void removeSensor(Sensor sensor) {
        if ( sensor == null || !sensors.contains(sensor)  ){
            return;
        }
        sensors.remove(sensor);
    }

    public List<Sensor> getSensors() {
        return new ArrayList<>(sensors);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
