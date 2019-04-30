package com.zslin.bus.dto;

import java.text.DecimalFormat;

/**
 * 人员统计DTO统计
 * Created by zsl on 2019/4/30.
 */
public class PersonalCountDto {

    /** 户数 */
    private Integer hs = 0;

    /** 人数 */
    private Integer rs = 0;

    /** 劳动力数 */
    private Integer ldls = 0;

    /** 就业数 */
    private Integer jys = 0;

    /** 就业占比 */
    private Float jybl = 0f;

    private String name = "";

    public PersonalCountDto(Integer hs, Integer rs, Integer ldls, Integer jys) {
        this.hs = hs;
        this.rs = rs;
        this.ldls = ldls;
        this.jys = jys;
        if(ldls>0) {
            DecimalFormat df = new DecimalFormat("#.00");
            String str = df.format((jys*100.0f/ldls));
            this.jybl = Float.parseFloat(str);
        }
    }

    public PersonalCountDto(String name, Integer hs, Integer rs, Integer ldls, Integer jys) {
        this(hs, rs, ldls, jys);
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonalCountDto{" +
                "hs=" + hs +
                ", rs=" + rs +
                ", ldls=" + ldls +
                ", jys=" + jys +
                ", jybl=" + jybl +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonalCountDto() {
    }

    public Integer getHs() {
        return hs;
    }

    public void setHs(Integer hs) {
        this.hs = hs;
    }

    public Integer getRs() {
        return rs;
    }

    public void setRs(Integer rs) {
        this.rs = rs;
    }

    public Integer getLdls() {
        return ldls;
    }

    public void setLdls(Integer ldls) {
        this.ldls = ldls;
    }

    public Integer getJys() {
        return jys;
    }

    public void setJys(Integer jys) {
        this.jys = jys;
    }

    public Float getJybl() {
        return jybl;
    }

    public void setJybl(Float jybl) {
        this.jybl = jybl;
    }
}
