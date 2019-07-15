package com.zslin.test;

/**
 * Created by zsl on 2019/6/26.
 */
public class JDBCPOJO {

    private String url;

    private String user;

    private String pwd;

    public JDBCPOJO() {
    }

    public JDBCPOJO(String url, String user, String pwd) {
        this.url = url;
        this.user = user;
        this.pwd = pwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
