package pt.ipleiria.estg.dei.ei.dae.projeto.dtos;

import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.SensorValueHistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.List;

public class SensorDTO {

    private long code;
    private long sensorTypeCode;  // Assuming you want to use the type code
    private String value;  // Latest value
    private String lastUpdate;  // Representing the timestamp
    private List<SensorLogDTO> log;  // For history endpoints (optional)

    // Constructor
    public SensorDTO(long code, long sensorTypeCode, String value, String lastUpdate, List<SensorLogDTO> log) {
        this.code = code;
        this.sensorTypeCode = sensorTypeCode;
        this.value = value;
        this.lastUpdate = lastUpdate;
        this.log = log;
    }

    // Getters and Setters
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getSensorTypeCode() {
        return sensorTypeCode;
    }

    public void setSensorTypeCode(long sensorTypeCode) {
        this.sensorTypeCode = sensorTypeCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<SensorLogDTO> getLog() {
        return log;
    }

    public void setLog(List<SensorLogDTO> log) {
        this.log = log;
    }

    // Static method to convert Sensor entity to DTO
    public static SensorDTO from(Sensor sensor) {
        // Convert timestamp to String if needed, using some date format
        String lastUpdate = sensor.getTimestamp() != null ? sensor.getTimestamp().toString() : null;

        // Create the DTO with the necessary information from the Sensor entity
        return new SensorDTO(
                sensor.getCode(),
                sensor.getType() != null ? sensor.getType().getId() : null,  // Assuming SensorsType has a 'getCode' method
                sensor.getValue(),
                lastUpdate,
                null // Here you can convert the log if needed
        );
    }

    // Optionally, you could implement a method to convert a list of SensorValueHistory entities into a list of DTOs
    public static List<SensorDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SensorDTO::from).collect(Collectors.toList());
    }
}
