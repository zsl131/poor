package com.zslin.bus.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 资产
 * Created by zsl on 2019/4/15.
 */
@Entity
@Table(name = "t_assets")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Assets {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 资产名称，如：车、房子 */
    private String mc;

    /** 户主ID */
    private Integer hzid;

    /** 户主姓名 */
    private String hzxm;

    /** 户主身份证号 */
    private String hzsfzh;

    /** 归属人员ID */
    private Integer gsid;

    /** 归属人员姓名 */
    private String gsxm;

    /** 归属人员身份证号 */
    private String gssfzh;

    /** 图片地址 */
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Integer getHzid() {
        return hzid;
    }

    public void setHzid(Integer hzid) {
        this.hzid = hzid;
    }

    public String getHzxm() {
        return hzxm;
    }

    public void setHzxm(String hzxm) {
        this.hzxm = hzxm;
    }

    public String getHzsfzh() {
        return hzsfzh;
    }

    public void setHzsfzh(String hzsfzh) {
        this.hzsfzh = hzsfzh;
    }

    public Integer getGsid() {
        return gsid;
    }

    public void setGsid(Integer gsid) {
        this.gsid = gsid;
    }

    public String getGsxm() {
        return gsxm;
    }

    public void setGsxm(String gsxm) {
        this.gsxm = gsxm;
    }

    public String getGssfzh() {
        return gssfzh;
    }

    public void setGssfzh(String gssfzh) {
        this.gssfzh = gssfzh;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
