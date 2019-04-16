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
    private Integer jtrs;

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
