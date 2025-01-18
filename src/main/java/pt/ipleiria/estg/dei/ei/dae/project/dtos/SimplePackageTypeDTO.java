package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.SensorsType;

import java.util.List;
import java.util.stream.Collectors;

public class SimplePackageTypeDTO {
    private long code;
    private String name;

    // Constructor
    public SimplePackageTypeDTO(long code, String name) {
        this.code = code;
        this.name = name;
    }

    public SimplePackageTypeDTO() {
    }

    // Getters and Setters
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Static method to convert Sensor entity to DTO
    public static SimplePackageTypeDTO from(PackageType packageType) {

        return new SimplePackageTypeDTO(
                packageType.getCode(),
                packageType.getName()
        );
    }

    public static List<SimplePackageTypeDTO> from(List<PackageType> packageType) {
        return packageType.stream().map(SimplePackageTypeDTO::from).collect(Collectors.toList());
    }
}
