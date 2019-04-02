package com.zslin.bus.tools;

/**
 * Created by zsl on 2018/7/3.
 */
public class JsonObj {

    private Integer size;

    private Object datas;

    public JsonObj() {}

    public JsonObj(Integer size, Object datas) {
        if(size==null || size<0) {size = 0;}
        this.size = size;
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "JsonObj{" +
                "size=" + size +
                ", datas=" + datas +
                '}';
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }
}
