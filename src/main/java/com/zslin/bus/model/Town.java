package com.zslin.bus.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by zsl on 2019/4/4.
 * 乡镇
 */
@Entity
@Table(name = "t_town")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    /** 级别，10-县级；11-镇级，数值越大级别越低 */
    private String level;

    /** 乡镇介绍 */
    @Lob
    private String remark;

    private Integer pid;

    private String pname;

    @Column(name = "order_no")
    private Integer orderNo;

    @Override
    public String toString() {
        return "Town{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", remark='" + remark + '\'' +
                ", pid=" + pid +
                ", pname='" + pname + '\'' +
                ", orderNo=" + orderNo +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    /** 图片路径 */
    @Column(name = "pic_url")
    private String picUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
