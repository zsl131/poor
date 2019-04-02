package com.zslin.bus.basic.dto;

/**
 * Created by zsl on 2018/8/7.
 * 数据字典DTO对象
 */
public class DictionaryDto {

    private String label;

    private Integer value;

    @Override
    public String toString() {
        return "DictionaryDto{" +
                "label='" + label + '\'' +
                ", value=" + value +
                '}';
    }

    public DictionaryDto(){}

    public DictionaryDto(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
