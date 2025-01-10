package pt.ipleiria.estg.dei.ei.dae.project.dtos;
import pt.ipleiria.estg.dei.ei.dae.project.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO implements Serializable {

    private long code;
    private VolumeState state;
    private long packageTypeCode;
    private Date lastUpdate;
    private List<SensorDTO> sensors;
    private List<ProductDTO> products;

    public VolumeDTO(long code, VolumeState state, long packageType, Date lastUpdate) {
        this.code = code;
        this.state = state;
        this.packageTypeCode = packageType;
        this.lastUpdate = lastUpdate;
        this.sensors = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public VolumeDTO() {
        this.sensors = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public VolumeState getState() {
        return state;
    }

    public void setState(VolumeState state) {
        this.state = state;
    }


    public long getPackageTypeCode(){
        return packageTypeCode;
    }
    public void setPackageTypeCode(long packageType) {
        this.packageTypeCode = packageType;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<SensorDTO> getSensors() {
        return new ArrayList<>(sensors);
    }

    public void setSensors(List<SensorDTO> sensors) {
        this.sensors = new ArrayList<>(sensors);
    }

    public List<ProductDTO> getProducts() {
        return new ArrayList<>(products);
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = new ArrayList<>(products);
    }

    public static VolumeDTO from(Volume volume) {
        VolumeDTO dto = new VolumeDTO(
                volume.getCode(),
                volume.getState(),
                volume.getTypePackage().getCode(),
                volume.getTimestamp()
        );
        dto.setSensors(SensorDTO.from(volume.getSensors()));
        dto.setProducts(ProductDTO.from(volume.getProducts()));
        return dto;
    }

    public static List<VolumeDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::from).collect(Collectors.toList());
    }
}
