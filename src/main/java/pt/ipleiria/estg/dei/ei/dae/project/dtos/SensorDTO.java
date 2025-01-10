package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Sensor;

import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO {

    private long code;
    private long sensorTypeCode;
    private String value;
    private String lastUpdate;
    private List<SensorLogDTO> log;

    // Constructor
    public SensorDTO(long code, long sensorTypeCode, String value, String lastUpdate) {
        this.code = code;
        this.sensorTypeCode = sensorTypeCode;
        this.value = value;
        this.lastUpdate = lastUpdate;
        this.log = new ArrayList<>();
    }

    public SensorDTO() {
        this.log = new ArrayList<>();
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
        return new ArrayList<>( log);
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
                lastUpdate);
    }

    // Optionally, you could implement a method to convert a list of SensorValueHistory entities into a list of DTOs
    public static List<SensorDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SensorDTO::from).collect(Collectors.toList());
    }
}
