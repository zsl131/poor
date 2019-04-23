package com.zslin.bus.dto;

/**
 * 饼状图DTO对象
 * Created by zsl on 2019/4/22.
 */
public class PieDto {

    private String name;

    private Long value;

    public PieDto() {
    }

    public PieDto(String name, Long value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "PieDto{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
