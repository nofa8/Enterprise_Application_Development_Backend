package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Sensor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PostVolumeSensorDTO {

    private long code;
    private long sensorTypeCode;

    // Constructor
    public PostVolumeSensorDTO(long code, long sensorTypeCode) {
        this.code = code;
        this.sensorTypeCode = sensorTypeCode;

    }

    public PostVolumeSensorDTO() {
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

    // Static method to convert Sensor entity to DTO
    public static PostVolumeSensorDTO from(Sensor sensor) {
        return new PostVolumeSensorDTO(
                sensor.getCode(),
                sensor.getType() != null ? sensor.getType().getId() : null
        );

    }

    // Optionally, you could implement a method to convert a list of SensorValueHistory entities into a list of DTOs
    public static List<PostVolumeSensorDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(PostVolumeSensorDTO::from).collect(Collectors.toList());
    }
}