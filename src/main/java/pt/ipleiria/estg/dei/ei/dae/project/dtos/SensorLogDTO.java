package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.ipleiria.estg.dei.ei.dae.project.entities.SensorValueHistory;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SensorLogDTO implements Serializable {

    @JsonIgnore
    private Long id;
    @JsonIgnore
    private SensorDTO sensor; // Assuming SensorDTO is another DTO for the Sensor entity
    private String value;
    private String timestamp;

    public SensorLogDTO(Long id, SensorDTO sensor, String value, String timestamp) {
        this.id = id;
        this.sensor = sensor;
        this.value = value;
        this.timestamp = timestamp;
    }

    public SensorLogDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static SensorLogDTO from(SensorValueHistory sensorValueHistory) {
        return new SensorLogDTO(
                sensorValueHistory.getId(),
                SensorDTO.from(sensorValueHistory.getSensor()), // assuming SensorDTO.from maps a Sensor entity to a DTO
                sensorValueHistory.getValue(),
                sensorValueHistory.getTimestamp().toString() // Convert Date to String, or use a formatter if needed
        );
    }

    public static List<SensorLogDTO> from(List<SensorValueHistory> sensorValueHistories) {
        return sensorValueHistories.stream().map(SensorLogDTO::from).collect(Collectors.toList());
    }
}
