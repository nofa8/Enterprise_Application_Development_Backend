package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Sensor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SensorDataDTO {
    private long code;
    private String value;

    // Getters and Setters
    public long getCode() {
        return code;
    }

    public SensorDataDTO() {
    }

    public SensorDataDTO(String value, long code) {
        this.value = value;
        this.code = code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public static SensorDataDTO from(Sensor sensor) {
        return new SensorDataDTO(
                sensor.getValue(),
                sensor.getCode()
        );
    }

    // Optionally, you could implement a method to convert a list of SensorValueHistory entities into a list of DTOs
    public static List<SensorDataDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SensorDataDTO::from).collect(Collectors.toList());
    }

}
