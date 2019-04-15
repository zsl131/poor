package com.zslin.bus.dto;

import com.zslin.bus.model.Yqkf;

/**
 * 易迁卡户简单信息，用来在导入的时候返回哪个用户出错
 */
public class YqkfDto {
    private int xh;
    private String xm;
    private String sfzh;

    public YqkfDto(){

    }

    public YqkfDto(Yqkf yqkf) {
        this.setXh(yqkf.getXh());
        this.setSfzh(yqkf.getSfzh());
        this.setXm(yqkf.getXm());
    }

    public int getXh() {
        return xh;
    }

    public void setXh(int xh) {
        this.xh = xh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }
}
