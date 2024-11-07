package pt.ipleiria.estg.dei.ei.dae.projeto.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.SensorsType;

import java.util.List;

@Entity
@Table(name = "producttypes")
@NamedQueries({
        @NamedQuery(
                name = "getAllProductsType",
                query = "SELECT p FROM ProductType p ORDER BY p.code" // IF needed order by something else
        )
    }
)
public class ProductType {

    @Id
    private long  code;

    @ElementCollection(targetClass = SensorsType.class)
    @CollectionTable(name = "product_sensors", joinColumns = @JoinColumn(name = "product_code"))
    @Column(name = "sensor_type")
    @Enumerated(EnumType.STRING) // Stores the enum values as strings
    private List<SensorsType> sensors;

    public ProductType(long code, List<SensorsType> sensors) {
        this.code = code;
        this.sensors = sensors;
    }

    public ProductType() {
    }


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public List<SensorsType> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorsType> sensors) {
        this.sensors = sensors;
    }
}
