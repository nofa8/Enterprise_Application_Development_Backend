package pt.ipleiria.estg.dei.ei.dae.projeto.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "sensors", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome"})
}
)
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Produto p ORDER BY p.code" // IF needed order by something else
        )
}
)
public class Sensors extends Versionable{

    @Id
    private long  code;
    @NotNull
    private SensorsType tipo;
    @NotBlank
    private double value;




    public Sensors() {
    }


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getBrand() {
        return brand;
    }

    public void setBrand(@NotBlank String brand) {
        this.brand = brand;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode());
    }

    @Override
    public boolean equals(Object obj) {
        return this.getCode() == ((Product)obj).getCode();
    }
}
