package com.zslin.bus.common.dto;

/**
 * Created by zsl on 2018/7/10.
 */
public class QueryListConditionDto {
    private String key;
    private String match;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public QueryListConditionDto() {

    }

    public QueryListConditionDto(String key, String match, String value) {
        this.key = key;
        this.match = match;
        this.value = value;
    }
}
