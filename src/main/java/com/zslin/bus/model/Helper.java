package com.zslin.bus.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 包保人员
 * Created by zsl on 2019/4/15.
 */
@Entity
@Table(name = "t_helper")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Helper {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 姓名 */
    private String xm;

    /** 身份证号 */
    private String sfzh;

    /** 联系电话 */
    private String lxdh;

    /** 工作单位 */
    private String gzdw;

    /** 工作地点 */
    private String gzdd;

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

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    public String getGzdd() {
        return gzdd;
    }

    public void setGzdd(String gzdd) {
        this.gzdd = gzdd;
    }
}
