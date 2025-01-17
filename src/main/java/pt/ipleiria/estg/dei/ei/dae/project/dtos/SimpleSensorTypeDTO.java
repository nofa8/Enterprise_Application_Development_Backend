package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.project.entities.SensorsType;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleSensorTypeDTO {

    private long code;

    // Constructor
    public SimpleSensorTypeDTO(long code) {
        this.code = code;

    }

    public SimpleSensorTypeDTO() {
    }

    // Getters and Setters
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }


    // Static method to convert Sensor entity to DTO
    public static SimpleSensorTypeDTO from(SensorsType sensor) {

        return new SimpleSensorTypeDTO(
                sensor.getId()
        );
    }

    public static List<SimpleSensorTypeDTO> from(List<SensorsType> sensors) {
        return sensors.stream().map(SimpleSensorTypeDTO::from).collect(Collectors.toList());
    }
}


