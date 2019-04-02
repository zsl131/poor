package com.zslin.bus.basic.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zsl on 2018/7/31.
 */
public class NormalDto {

    private Map<String, Object> database;

    public static NormalDto getInstance() {
        return new NormalDto();
    }
    private NormalDto(){
        database = new HashMap<>();
    }

    public Map<String, Object> set(String objName, Object obj) {
        database.put(objName, obj);
        return database;
    }

    public Map<String, Object> getDatabase() {
        return database;
    }

    public void setDatabase(Map<String, Object> database) {
        this.database = database;
    }
}
