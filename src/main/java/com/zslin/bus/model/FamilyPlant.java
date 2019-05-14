package com.zslin.bus.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 家庭种植品种
 * Created by zsl on 2019/5/14.
 */
@Entity
@Table(name = "t_family_plant")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 户主身份证号 */
    private String hzsfzh;

    /** 户主姓名 */
    private String hzxm;

    /** 种植品种名称 */
    private String zzpzmc;

    /** 种植品种代码 */
    private String zzpzdm;

    /** 种植面积 */
    private Float zzmj = 0f;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHzsfzh() {
        return hzsfzh;
    }

    public void setHzsfzh(String hzsfzh) {
        this.hzsfzh = hzsfzh;
    }

    public String getHzxm() {
        return hzxm;
    }

    public void setHzxm(String hzxm) {
        this.hzxm = hzxm;
    }

    public String getZzpzmc() {
        return zzpzmc;
    }

    public void setZzpzmc(String zzpzmc) {
        this.zzpzmc = zzpzmc;
    }

    public String getZzpzdm() {
        return zzpzdm;
    }

    public void setZzpzdm(String zzpzdm) {
        this.zzpzdm = zzpzdm;
    }

    public Float getZzmj() {
        return zzmj;
    }

    public void setZzmj(Float zzmj) {
        this.zzmj = zzmj;
    }
}
