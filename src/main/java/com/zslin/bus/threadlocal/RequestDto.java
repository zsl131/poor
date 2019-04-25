package com.zslin.bus.threadlocal;

/**
 * Created by zsl on 2019/4/24.
 */
public class RequestDto {

    private Integer uid;

    private Integer isAll;

    public RequestDto() {
    }

    public RequestDto(Integer uid, Integer isAll) {
        this.uid = uid;
        this.isAll = isAll;
    }

    @Override
    public String toString() {
        return "RequestDto{" +
                "uid=" + uid +
                ", isAll=" + isAll +
                '}';
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getIsAll() {
        return isAll;
    }

    public void setIsAll(Integer isAll) {
        this.isAll = isAll;
    }
}
