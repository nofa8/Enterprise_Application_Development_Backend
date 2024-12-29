package pt.ipleiria.estg.dei.ei.dae.projeto.entities;


import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.mappings.ProductSensorMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "product_types")
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSensorMapping> sensors;

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

    public List<ProductSensorMapping> getSensors() {
        // Return an unmodifiable list of SensorsType extracted from the ProductSensorMapping
        return Collections.unmodifiableList(sensors);
    }

    public void addSensor(SensorsType sensor, int quantity) {
        if (sensor == null) {
            throw new IllegalArgumentException("Sensor cannot be null");
        }

        // Check if the sensor is already in the list
        boolean exists = sensors.stream()
                .anyMatch(mapping -> mapping.getSensor().equals(sensor));

        if (exists) {
            throw new IllegalStateException("Sensor is already associated with this product.");
        }

        // Add new mapping
        ProductSensorMapping mapping = new ProductSensorMapping(this, sensor, quantity);
        sensors.add(mapping);
    }

    public void updateSensorQuantity(SensorsType sensor, int quantity) {
        if (sensor == null) {
            throw new IllegalArgumentException("Sensor cannot be null");
        }

        // Find the mapping for the specified sensor
        ProductSensorMapping mapping = sensors.stream()
                .filter(m -> m.getSensor().equals(sensor))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Sensor is not associated with this product."));

        // Update the quantity
        mapping.setQuantity(quantity);
    }

    public void removeSensor(SensorsType sensor) {
        if (sensor == null) {
            return;
        }

        // Find the mapping for the specified sensor
        ProductSensorMapping mapping = sensors.stream()
                .filter(m -> m.getSensor().equals(sensor))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Sensor is not associated with this product."));

        // Remove the mapping
        sensors.remove(mapping);
    }
}
