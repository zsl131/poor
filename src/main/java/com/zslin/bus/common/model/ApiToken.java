package com.zslin.bus.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by zsl on 2018/7/5.
 */
@Entity
@Table(name = "a_api_token")
@JsonInclude(value= JsonInclude.Include.NON_NULL)
public class ApiToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String status;

    private String token;

    @Column(name = "client_name")
    private String clientName;

    /** 如果是super则所有接口都可以使用 */
    @Column(name = "is_super")
    private String isSuper;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(String isSuper) {
        this.isSuper = isSuper;
    }
}
