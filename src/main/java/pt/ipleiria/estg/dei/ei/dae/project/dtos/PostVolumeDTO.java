package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PostVolumeDTO implements Serializable {

    private long code;
    private VolumeState state;
    private long packageTypeCode;
    private List<PostVolumeSensorDTO> sensors;
    private List<PostVolumeProductDTO> products;

    public PostVolumeDTO(long code, VolumeState state, long packageType) {
        this.code = code;
        this.state = state;
        this.packageTypeCode = packageType;
        this.sensors = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public PostVolumeDTO() {
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


    public List<PostVolumeSensorDTO> getSensors() {
        return new ArrayList<>(sensors);
    }

    public void setSensors(List<PostVolumeSensorDTO> sensors) {
        this.sensors = new ArrayList<>(sensors);
    }

    public List<PostVolumeProductDTO> getProducts() {
        return new ArrayList<>(products);
    }

    public void setProducts(List<PostVolumeProductDTO> products) {
        this.products = new ArrayList<>(products);
    }

    public static PostVolumeDTO from(Volume volume) {
        PostVolumeDTO dto = new PostVolumeDTO(
                volume.getCode(),
                volume.getState(),
                volume.getTypePackage().getCode()
        );
        dto.setSensors(PostVolumeSensorDTO.from(volume.getSensors()));
        dto.setProducts(PostVolumeProductDTO.from(volume.getProducts()));
        return dto;
    }

    public static List<PostVolumeDTO> from(List<Volume> volumes) {
        return volumes.stream().map(PostVolumeDTO::from).collect(Collectors.toList());
    }
}