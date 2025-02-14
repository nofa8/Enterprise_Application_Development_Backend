package pt.ipleiria.estg.dei.ei.dae.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "sensors")
@NamedQueries({
        @NamedQuery(
                name = "getAllSensors",
                query = "SELECT s FROM Sensor s ORDER BY s.code" // IF needed order by something else
        )
    }
)
public class Sensor extends Versionable{

    @Id
    private long  code;

    @ManyToOne
    private SensorsType type;

    private String value;

    @NotNull
    private Date timestamp;

    @ManyToOne
    private Volume volume;

    public Sensor(long code, SensorsType type, String value, Date timestamp, Volume volume) {
        this.code = code;
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
        this.volume = volume;
    }

    public Sensor() {
    }


    public @NotNull Volume getVolume() {
        return volume;
    }

    public void setVolume(@NotNull Volume volume) {
        this.volume = volume;
    }

    public @NotNull Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NotNull Date timestamp) {
        this.timestamp = timestamp;
    }


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public @NotNull SensorsType getType() {
        return type;
    }

    public void setType(@NotNull SensorsType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode());
    }

    @Override
    public boolean equals(Object obj) {
        return this.getCode() == ((Sensor)obj).getCode();
    }
}
