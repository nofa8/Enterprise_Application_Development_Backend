package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.project.entities.SensorsType;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleSensorTypeDTO {

    private long code;
    private String name;

    // Constructor
    public SimpleSensorTypeDTO(long code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                sensor.getId(),
                sensor.getName()
        );
    }

    public static List<SimpleSensorTypeDTO> from(List<SensorsType> sensors) {
        return sensors.stream().map(SimpleSensorTypeDTO::from).collect(Collectors.toList());
    }
}


