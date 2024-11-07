package pt.ipleiria.estg.dei.ei.dae.projeto.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.SensorsType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "producttypes")
@NamedQueries({
        @NamedQuery(
                name = "getAllProductTypes",
                query = "SELECT p FROM ProductType p ORDER BY p.code" // IF needed order by something else
        )
    }
)
public class ProductType extends Versionable{

    @Id
    private long  code;

    @ElementCollection(targetClass = SensorsType.class)
    @CollectionTable(name = "product_sensors", joinColumns = @JoinColumn(name = "product_code"))
    @Column(name = "sensor_type")
    @Enumerated(EnumType.STRING) // Stores the enum values as strings
    private List<SensorsType> sensors;

    @OneToMany(mappedBy = "type")
    private List<Product> products;


    public ProductType(long code) {
        this.code = code;
        this.sensors = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public ProductType() {
        this.sensors = new ArrayList<>();
        this.products = new ArrayList<>();
    }


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
    public void addProduct(Product product) {
        if ( product == null || products.contains(product)  ){
            return;
        }

        products.add(product);
    }

    public void removeProduct(Product product) {

        if ( product == null || !products.contains(product)  ){
            return;
        }

        products.remove(product);
    }

    public List<SensorsType> getSensors() {
        return new ArrayList<>(sensors);
    }

    public void addSensorsType(SensorsType sensor) {
        if ( sensor == null || sensors.contains(sensor)  ){
            return;
        }

        sensors.add(sensor);
    }

    public void removeSensorsType(SensorsType sensor) {

        if ( sensor == null || !sensors.contains(sensor)  ){
            return;
        }

        sensors.remove(sensor);
    }



}
