package com.zslin.bus.threadlocal;

/**
 * Created by zsl on 2019/4/24.
 */
public class RequestDto {

    private Integer objId;

    private boolean isAll;

    private boolean isTownId = false;

    public RequestDto() {
    }

    public RequestDto(Integer objId, boolean isAll) {
        this.objId = objId;
        this.isAll = isAll;
        this.isTownId = false;
    }

    public RequestDto(Integer objId, boolean isAll, boolean isTownId) {
        this.objId = objId;
        this.isAll = isAll;
        this.isTownId = isTownId;
    }

    @Override
    public String toString() {
        return "RequestDto{" +
                "objId=" + objId +
                ", isAll=" + isAll +
                ", isTownId=" + isTownId +
                '}';
    }

    public boolean isTownId() {
        return isTownId;
    }

    public void setTownId(boolean townId) {
        isTownId = townId;
    }

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public boolean getIsAll() {
        return isAll;
    }

    public void setIsAll(boolean isAll) {
        this.isAll = isAll;
    }
}
