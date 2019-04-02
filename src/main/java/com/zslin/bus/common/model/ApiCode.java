package com.zslin.bus.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by zsl on 2018/7/5.
 */
@Entity
@Table(name = "a_api_code")
@JsonInclude(value= JsonInclude.Include.NON_NULL)
public class ApiCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 服务名称 */
    @Column(name = "service_name")
    private String serviceName;

    /** 方法名称 */
    @Column(name = "method_name")
    private String methodName;

    /** 编码，须分类管理 */
    private String code;

    /** 备注 */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
