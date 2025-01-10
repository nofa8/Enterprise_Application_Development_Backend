package pt.ipleiria.estg.dei.ei.dae.project.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.PackageSensorMapping;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.ProductSensorMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "sensor_types")
@NamedQueries({
        @NamedQuery(
                name = "getAllSensorTypes",
                query = "SELECT s FROM SensorsType s ORDER BY s.name" // JPQL
        )
})
public class SensorsType {

    @Id
    private long id;

    @NotBlank
    private String name;

    @OneToMany( mappedBy = "type")
    private List<Sensor> sensors;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PackageSensorMapping> packages;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSensorMapping> products;



    // Constructors, getters, and setters
    public SensorsType() {
        sensors = new ArrayList<>();
        packages = new ArrayList<>();
        products = new ArrayList<>();
    }

    public SensorsType(long id, String name) {
        this.id = id;
        this.name = name;
        sensors = new ArrayList<>();
        packages = new ArrayList<>();
        products = new ArrayList<>();
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



    public List<Sensor> getSensors() {
        return new ArrayList<>(sensors);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<ProductSensorMapping> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void addProduct(ProductType product, int quantity) {
        if (product == null) {
            return;
        }

        // Check if the product is already associated with this sensor
        boolean exists = products.stream()
                .anyMatch(mapping -> mapping.getProduct().equals(product));

        if (exists) {
            return;
        }

        // Add new mapping
        ProductSensorMapping mapping = new ProductSensorMapping(product, this, quantity);
        products.add(mapping);
    }

    public void updateProductQuantity(ProductType product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        // Find the mapping for the specified product
        ProductSensorMapping mapping = products.stream()
                .filter(m -> m.getProduct().equals(product))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Product is not associated with this sensor."));

        // Update the quantity
        mapping.setQuantity(quantity);
    }

    public void removeProduct(ProductType product) {
        if (product == null) {
            return;
        }

        // Find the mapping for the specified product
        ProductSensorMapping mapping = products.stream()
                .filter(m -> m.getProduct().equals(product))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Product is not associated with this sensor."));

        // Remove the mapping
        products.remove(mapping);
    }


    public List<PackageSensorMapping> getPackages() {
        // Return an unmodifiable list to prevent external modifications
        return Collections.unmodifiableList(packages);
    }

    public void addPackage(PackageType packageType, int quantity) {
        if (packageType == null) {
            return;
        }

        // Check if the package is already associated with this sensor
        boolean exists = packages.stream()
                .anyMatch(mapping -> mapping.getPackageType().equals(packageType));

        if (exists) {
            return;
        }

        // Add new mapping
        PackageSensorMapping mapping = new PackageSensorMapping(packageType, this, quantity);
        packages.add(mapping);
    }

    public void updatePackageQuantity(PackageType packageType, int quantity) {
        if (packageType == null) {
            throw new IllegalArgumentException("Package cannot be null");
        }

        // Find the mapping for the specified package
        PackageSensorMapping mapping = packages.stream()
                .filter(m -> m.getPackageType().equals(packageType))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Package is not associated with this sensor."));

        // Update the quantity
        mapping.setQuantity(quantity);
    }

    public void removePackage(PackageType packageType) {
        if (packageType == null) {
            return;
        }

        // Find the mapping for the specified package
        PackageSensorMapping mapping = packages.stream()
                .filter(m -> m.getPackageType().equals(packageType))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Package is not associated with this sensor."));

        // Remove the mapping
        packages.remove(mapping);
    }

}
