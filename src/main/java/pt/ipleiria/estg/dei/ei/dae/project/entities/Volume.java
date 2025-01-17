package pt.ipleiria.estg.dei.ei.dae.project.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "volumes")
@NamedQueries({
        @NamedQuery(
                name = "getAllVolumes",
                query = "SELECT v FROM Volume v ORDER BY v.code"
        )
}
)
public class Volume extends Versionable {
    @Id
    private long  code;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VolumeState state;

    // criar classe com sensores associados
    @NotNull
    @ManyToOne
    private PackageType typePackage;

    @NotNull
    @ManyToOne
    private Order order;

    @NotNull
    private Date timestamp;

    @NotNull
    @OneToMany(mappedBy = "volume")
    private List<Sensor> sensors;

    @ManyToMany(mappedBy = "volumes")
    private List<Product> products;


    public Volume(long code, VolumeState state, PackageType typePackage, Order order, Date timestamp) {
        this.code = code;
        this.state = state;
        this.typePackage = typePackage;
        this.order = order;
        this.timestamp = timestamp;
        sensors = new ArrayList<>();
        products = new ArrayList<>();
    }

    public Volume() {
        sensors = new ArrayList<>();
        products = new ArrayList<>();
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

    public void addSensor(Sensor sensor) {
        if ( sensor == null || sensors.contains(sensor)  ){
            return;
        }

        sensors.add(sensor);
    }

    public void removeSensor(Sensor sensor) {

        if ( sensor == null || !sensors.contains(sensor)  ){
            return;
        }

        sensors.remove(sensor);
    }

    public @NotNull Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NotNull Date timestamp) {
        this.timestamp = timestamp;
    }

    public @NotNull PackageType getTypePackage() {
        return typePackage;
    }

    public void setTypePackage(@NotNull PackageType typePackage) {
        this.typePackage = typePackage;
    }

    public @NotNull List<Sensor> getSensors() {
        return new ArrayList<>(sensors);
    }

    public @NotNull List<Product> getProducts() {
        return  new ArrayList<>(products);
    }

    public @NotNull Order getOrder() {
        return order;
    }

    public void setOrder(@NotNull Order order) {
        this.order = order;
    }

    public @NotNull VolumeState getState() {
        return state;
    }

    public void setState(@NotNull VolumeState state) {
        this.state = state;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode());
    }

    @Override
    public boolean equals(Object obj) {
        return this.getCode() == ((Volume)obj).getCode();
    }
}
