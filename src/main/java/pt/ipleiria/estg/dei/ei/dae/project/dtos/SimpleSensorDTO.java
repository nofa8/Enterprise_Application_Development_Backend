package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Sensor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleSensorDTO {

    private long code;

    // Constructor
    public SimpleSensorDTO(long code) {
        this.code = code;

    }

    public SimpleSensorDTO() {
    }

    // Getters and Setters
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }


    // Static method to convert Sensor entity to DTO
    public static SimpleSensorDTO from(Sensor sensor) {

        return new SimpleSensorDTO(
                sensor.getCode()
        );
    }

    public static List<SimpleSensorDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SimpleSensorDTO::from).collect(Collectors.toList());
    }
}


