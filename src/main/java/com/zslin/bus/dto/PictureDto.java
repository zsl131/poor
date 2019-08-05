package com.zslin.bus.dto;

/**
 * Created by zsl on 2019/5/1.
 */
public class PictureDto {

    private String name;

    /** 是否是房子 */
    private boolean isHouse;

    private boolean isCar;

    public PictureDto() {
    }

    public PictureDto(String name, boolean isHouse) {
        this.name = name;
        this.isHouse = isHouse;
    }

    public PictureDto(String name, boolean isHouse, boolean isCar) {
        this.name = name;
        this.isHouse = isHouse;
        this.isCar = isCar;
    }

    public boolean isCar() {
        return isCar;
    }

    public void setCar(boolean car) {
        isCar = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHouse() {
        return isHouse;
    }

    public void setHouse(boolean house) {
        isHouse = house;
    }
}
