package com.zslin.bus.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 包保关系
 * Created by zsl on 2019/4/15.
 */
@Entity
@Table(name = "t_helper_personal")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelperPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 包保人员ID */
    private Integer bbid;

    /** 包保人员姓名 */
    private String bbxm;

    /** 包保人员身份证号 */
    private String bbsfzh;

    /** 户主ID */
    private Integer hzid;

    /** 户主姓名 */
    private String hzxm;

    /** 户主身份证号 */
    private String hzsfzh;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBbid() {
        return bbid;
    }

    public void setBbid(Integer bbid) {
        this.bbid = bbid;
    }

    public String getBbxm() {
        return bbxm;
    }

    public void setBbxm(String bbxm) {
        this.bbxm = bbxm;
    }

    public String getBbsfzh() {
        return bbsfzh;
    }

    public void setBbsfzh(String bbsfzh) {
        this.bbsfzh = bbsfzh;
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
}
