package com.zslin.bus.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 户，用于记录户主信息
 * Created by zsl on 2019/4/14.
 */
@Entity
@Table(name = "t_family")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 序号 */
    private Integer xh;

    /** 户主姓名 */
    private String xm;

    /** 身份证号 */
    private String sfzh;

    /** 家庭人数 */
    private Integer jtrs = 0;

    /** 劳动力人数 */
    private Integer ldlrs = 0;

    /** 就业人数 */
    private Integer jyrs = 0;

    /** 家庭地址 */
    private String jtdz;

    /** 户主姓别 */
    private String xb;

    /** 户主民族 */
    private String mz;

    /** 贫困属性 */
    private String pksx;

    /** 易迁户属性 */
    private String yqhsx;

    /** 联系电话 */
    private String lxdh;

    /** 搬迁地点 */
    private String bqdd;

    /** 搬迁时间 */
    private String bqsj;

    /** 备注 */
    private String bz;

    /** 乡镇ID */
    private Integer xzid;

    /** 乡镇名称 */
    private String xzmc;

    /** 宅基地，面积 */
    private Float zjd = 0f;

    /** 林地，面积 */
    private Float ld = 0f;

    /** 耕地，面积 */
    private Float gd = 0f;

    /** 种植品种 */
    private String zzpz;

    /** 种植地面积 */
    private Float zzdmj = 0f;

    /** 类型：卡户/随迁户 */
    private String lx;

    /** 村庄ID */
    private Integer czid;

    /** 村庄名称 */
    private String czmc;

    public Integer getLdlrs() {
        return ldlrs;
    }

    public void setLdlrs(Integer ldlrs) {
        this.ldlrs = ldlrs;
    }

    public Integer getJyrs() {
        return jyrs;
    }

    public void setJyrs(Integer jyrs) {
        this.jyrs = jyrs;
    }

    public Integer getCzid() {
        return czid;
    }

    public void setCzid(Integer czid) {
        this.czid = czid;
    }

    public String getCzmc() {
        return czmc;
    }

    public void setCzmc(String czmc) {
        this.czmc = czmc;
    }

    public Float getLd() {
        return ld;
    }

    public void setLd(Float ld) {
        this.ld = ld;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public Float getZjd() {
        return zjd;
    }

    public void setZjd(Float zjd) {
        this.zjd = zjd;
    }

    public Float getGd() {
        return gd;
    }

    public void setGd(Float gd) {
        this.gd = gd;
    }

    public String getZzpz() {
        return zzpz;
    }

    public void setZzpz(String zzpz) {
        this.zzpz = zzpz;
    }

    public Float getZzdmj() {
        return zzdmj;
    }

    public void setZzdmj(Float zzdmj) {
        this.zzdmj = zzdmj;
    }

    public Integer getXzid() {
        return xzid;
    }

    public void setXzid(Integer xzid) {
        this.xzid = xzid;
    }

    public String getXzmc() {
        return xzmc;
    }

    public void setXzmc(String xzmc) {
        this.xzmc = xzmc;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getJtrs() {
        return jtrs;
    }

    public void setJtrs(Integer jtrs) {
        this.jtrs = jtrs;
    }

    public String getJtdz() {
        return jtdz;
    }

    public void setJtdz(String jtdz) {
        this.jtdz = jtdz;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getPksx() {
        return pksx;
    }

    public void setPksx(String pksx) {
        this.pksx = pksx;
    }

    public String getYqhsx() {
        return yqhsx;
    }

    public void setYqhsx(String yqhsx) {
        this.yqhsx = yqhsx;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getBqdd() {
        return bqdd;
    }

    public void setBqdd(String bqdd) {
        this.bqdd = bqdd;
    }

    public String getBqsj() {
        return bqsj;
    }

    public void setBqsj(String bqsj) {
        this.bqsj = bqsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
