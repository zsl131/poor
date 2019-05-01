package com.zslin.bus.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.poi.ss.formula.functions.Na;

import javax.persistence.*;

/**
 * 图片上传出错记录
 * Created by zsl on 2019/4/26.
 */
@Entity
@Table(name = "t_picture_upload")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PictureUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "batch_no")
    private String batchNo;

    /** 条数 */
    private Integer amount;

    /** 操作员 */
    private String username;

    /** 乡镇名称 */
    private String xzmc;

    /** 乡镇ID */
    private Integer xzid;

    /** 获取方式，姓名或身份证号 */
    private String hqfs;

    /** 成功条数 */
    @Column(name = "suc_amount")
    private Integer sucAmount;

    public Integer getSucAmount() {
        return sucAmount;
    }

    public void setSucAmount(Integer sucAmount) {
        this.sucAmount = sucAmount;
    }

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "create_long")
    private Long createLong;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getCreateLong() {
        return createLong;
    }

    public void setCreateLong(Long createLong) {
        this.createLong = createLong;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getXzmc() {
        return xzmc;
    }

    public void setXzmc(String xzmc) {
        this.xzmc = xzmc;
    }

    public Integer getXzid() {
        return xzid;
    }

    public void setXzid(Integer xzid) {
        this.xzid = xzid;
    }

    public String getHqfs() {
        return hqfs;
    }

    public void setHqfs(String hqfs) {
        this.hqfs = hqfs;
    }
}
