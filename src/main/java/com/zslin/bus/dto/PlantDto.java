package com.zslin.bus.dto;

import com.zslin.bus.poi.util.ExcelResources;

/**
 * 种植品种
 * Created by zsl on 2019/7/11.
 */
public class PlantDto {

    private String xzmc;

    private String czmc;

    private String zrc;

    private String xm;

    private String sfzh;

    private String lx;

    private Float gd = 0f;

    private Float ld = 0f;

    private Float zjd = 0f;

    private Float ktgdmj = 0f;

    private Float fz = 0f;

    private Float qz = 0f;

    private Float qhj = 0f;

    private Float dhb = 0f;

    private Float ht = 0f;

    private Float bl = 0f;

    private Float tm = 0f;

    private Float cy = 0f;

    private Float tz = 0f;

    private Float lz = 0f;

    private Float l = 0f;

    private Float yt = 0f;

    private Float gj = 0f;

    private Float ss = 0f;

    private Float tghc = 0f;

    private Float qt = 0f;

    @ExcelResources(title="乡(镇)",order=1)
    public String getXzmc() {
        return xzmc;
    }

    public void setXzmc(String xzmc) {
        this.xzmc = xzmc;
    }

    @ExcelResources(title="行政村",order=2)
    public String getCzmc() {
        return czmc;
    }

    public void setCzmc(String czmc) {
        this.czmc = czmc;
    }

    @ExcelResources(title="自然村",order=3)
    public String getZrc() {
        return zrc;
    }

    public void setZrc(String zrc) {
        this.zrc = zrc;
    }

    @ExcelResources(title="姓名",order=4)
    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    @ExcelResources(title="证件号码",order=5)
    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    @ExcelResources(title="是否卡户",order=6)
    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    @ExcelResources(title="耕地面积（亩）",order=7)
    public Float getGd() {
        return gd;
    }

    public void setGd(Float gd) {
        this.gd = gd;
    }

    @ExcelResources(title="林地面积（亩）",order=8)
    public Float getLd() {
        return ld;
    }

    public void setLd(Float ld) {
        this.ld = ld;
    }

    @ExcelResources(title="宅基地面积（㎡）",order=9)
    public Float getZjd() {
        return zjd;
    }

    public void setZjd(Float zjd) {
        this.zjd = zjd;
    }

    @ExcelResources(title="可退耕面积（亩）",order=10)
    public Float getKtgdmj() {
        return ktgdmj;
    }

    public void setKtgdmj(Float ktgdmj) {
        this.ktgdmj = ktgdmj;
    }

    @ExcelResources(title="方竹",order=11)
    public Float getFz() {
        return fz;
    }

    public void setFz(Float fz) {
        this.fz = fz;
    }

    @ExcelResources(title="筇竹",order=12)
    public Float getQz() {
        return qz;
    }

    public void setQz(Float qz) {
        this.qz = qz;
    }

    @ExcelResources(title="青花椒",order=13)
    public Float getQhj() {
        return qhj;
    }

    public void setQhj(Float qhj) {
        this.qhj = qhj;
    }

    @ExcelResources(title="贡椒（大红袍）",order=14)
    public Float getDhb() {
        return dhb;
    }

    public void setDhb(Float dhb) {
        this.dhb = dhb;
    }

    @ExcelResources(title="核桃",order=15)
    public Float getHt() {
        return ht;
    }

    public void setHt(Float ht) {
        this.ht = ht;
    }

    @ExcelResources(title="板栗",order=16)
    public Float getBl() {
        return bl;
    }

    public void setBl(Float bl) {
        this.bl = bl;
    }

    @ExcelResources(title="天麻菌材林",order=17)
    public Float getTm() {
        return tm;
    }

    public void setTm(Float tm) {
        this.tm = tm;
    }

    @ExcelResources(title="茶叶",order=18)
    public Float getCy() {
        return cy;
    }

    public void setCy(Float cy) {
        this.cy = cy;
    }

    @ExcelResources(title="桃子",order=19)
    public Float getTz() {
        return tz;
    }

    public void setTz(Float tz) {
        this.tz = tz;
    }

    @ExcelResources(title="李子",order=20)
    public Float getLz() {
        return lz;
    }

    public void setLz(Float lz) {
        this.lz = lz;
    }

    @ExcelResources(title="梨子",order=21)
    public Float getL() {
        return l;
    }

    public void setL(Float l) {
        this.l = l;
    }

    @ExcelResources(title="樱桃",order=22)
    public Float getYt() {
        return yt;
    }

    public void setYt(Float yt) {
        this.yt = yt;
    }

    @ExcelResources(title="柑桔",order=23)
    public Float getGj() {
        return gj;
    }

    public void setGj(Float gj) {
        this.gj = gj;
    }

    @ExcelResources(title="杉树",order=24)
    public Float getSs() {
        return ss;
    }

    public void setSs(Float ss) {
        this.ss = ss;
    }

    @ExcelResources(title="退耕还草",order=25)
    public Float getTghc() {
        return tghc;
    }

    public void setTghc(Float tghc) {
        this.tghc = tghc;
    }

    @ExcelResources(title="其他",order=26)
    public Float getQt() {
        return qt;
    }

    public void setQt(Float qt) {
        this.qt = qt;
    }
}
