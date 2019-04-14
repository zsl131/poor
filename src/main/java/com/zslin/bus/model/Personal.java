package com.zslin.bus.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 家庭人员
 * Created by zsl on 2019/4/14.
 */
@Entity
@Table(name = "t_personal")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Personal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 户主姓名 */
    private String xm;

    /** 身份证号 */
    private String sfzh;

    /** 家庭地址 */
    private String jtdz;

    /** 户主姓别 */
    private String xb;

    /** 户主民族 */
    private String mz;

    /** 贫困属性 */
    private String pksx;

    /** 易迁户属性 */
    private String yqhsx;

    /** 联系电话 */
    private String lxdh;

    /** 搬迁地点 */
    private String bqdd;

    /** 搬迁时间 */
    private String bqsj;

    /** 备注 */
    private String bz;

    /** 年龄，每天更新 */
    private Integer nl;

    /** 文化程度 */
    private String whcd;

    /** 是否是劳动力 */
    private String sfsldl;

    /** 就学情况 */

    /** 是否在校 */
    private String sfzx;

    /** 就读学校 */
    private String jdxx;

    /** 就读年级 */
    private String jdnj;

    /** 就学情况 */

    /** 享受资助情况 */

    /** 是否享受资助 */
    private String sfxszz;

    /** 资助项目 */
    private String zzxm;

    /** 资助金额 */
    private Float zzje;

    /** 享受资助情况 */

    /** 健康状况 */
    private String jkzk;

    /** 城乡居民养老保险是否参保，是否养老保险 */
    private String sfylbx;

    /** 基本医疗保险参保情况及患病情况 */

    /** 是否医保，是否参加基本医疗保险 */
    private String sfyb;

    /** 参保险种（居民、职工） */
    private String cbxz;

    /** 参保地或参保单位 */
    private String cbdw;

    /** 是否患病 */
    private String sfhb;

    /** 基本医疗保险参保情况及患病情况 */

    /** 参加何种培训 */
    private String cjhzpx;

    /** 就业情况 */

    /** 就业情况 */
    private String jyqk;

    /** 就业情况-外出务工 */

    /** 务工地域 */
    private String wgdy;

    /** 务工地点 */
    private String wgdd;

    /** 企业名称 */
    private String qymc;

    /** 岗位名称 */
    private String gwmc;

    /** 务工时间 */
    private String wgsj;

    /** 月工资 */
    private Float ygz;

    /** 就业情况-自主创业 */

    /** 创业项目 */
    private String cyxm;

    /** 创业地点 */
    private String cydd;

    /** 创业时间 */
    private String cysj;

    /** 月收入 */
    private Float ysr;

    /** 就业情况-未就业 */

    /** 务工去向 */
    private String wgqx;

    /** 公益性岗位 */
    private String gyxgw;

    /** 自主创业 */
    private String zzcy;

    /** 无法外出原因 */
    private String wfwcyy;

    /** 就业情况 */

    /** 培训需求 */
    private String pxxq;

    /** 宅基地，面积 */
    private Float zjd;

    /** 耕地，面积 */
    private Float gd;

    /** 种植品种 */
    private String zzpz;

    /** 种植地面积 */
    private Float zzdmj;

    /** 户主ID */
    private Integer hzid;

    /** 户主姓名 */
    private String hzxm;

    /** 户主身份证号 */
    private String hzsfzh;

    /** 与户主关系 */
    private String yhzgx;

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

    public String getJtdz() {
        return jtdz;
    }

    public void setJtdz(String jtdz) {
        this.jtdz = jtdz;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getPksx() {
        return pksx;
    }

    public void setPksx(String pksx) {
        this.pksx = pksx;
    }

    public String getYqhsx() {
        return yqhsx;
    }

    public void setYqhsx(String yqhsx) {
        this.yqhsx = yqhsx;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getBqdd() {
        return bqdd;
    }

    public void setBqdd(String bqdd) {
        this.bqdd = bqdd;
    }

    public String getBqsj() {
        return bqsj;
    }

    public void setBqsj(String bqsj) {
        this.bqsj = bqsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getNl() {
        return nl;
    }

    public void setNl(Integer nl) {
        this.nl = nl;
    }

    public String getWhcd() {
        return whcd;
    }

    public void setWhcd(String whcd) {
        this.whcd = whcd;
    }

    public String getSfsldl() {
        return sfsldl;
    }

    public void setSfsldl(String sfsldl) {
        this.sfsldl = sfsldl;
    }

    public String getSfzx() {
        return sfzx;
    }

    public void setSfzx(String sfzx) {
        this.sfzx = sfzx;
    }

    public String getJdxx() {
        return jdxx;
    }

    public void setJdxx(String jdxx) {
        this.jdxx = jdxx;
    }

    public String getJdnj() {
        return jdnj;
    }

    public void setJdnj(String jdnj) {
        this.jdnj = jdnj;
    }

    public String getSfxszz() {
        return sfxszz;
    }

    public void setSfxszz(String sfxszz) {
        this.sfxszz = sfxszz;
    }

    public String getZzxm() {
        return zzxm;
    }

    public void setZzxm(String zzxm) {
        this.zzxm = zzxm;
    }

    public Float getZzje() {
        return zzje;
    }

    public void setZzje(Float zzje) {
        this.zzje = zzje;
    }

    public String getJkzk() {
        return jkzk;
    }

    public void setJkzk(String jkzk) {
        this.jkzk = jkzk;
    }

    public String getSfylbx() {
        return sfylbx;
    }

    public void setSfylbx(String sfylbx) {
        this.sfylbx = sfylbx;
    }

    public String getSfyb() {
        return sfyb;
    }

    public void setSfyb(String sfyb) {
        this.sfyb = sfyb;
    }

    public String getCbxz() {
        return cbxz;
    }

    public void setCbxz(String cbxz) {
        this.cbxz = cbxz;
    }

    public String getCbdw() {
        return cbdw;
    }

    public void setCbdw(String cbdw) {
        this.cbdw = cbdw;
    }

    public String getSfhb() {
        return sfhb;
    }

    public void setSfhb(String sfhb) {
        this.sfhb = sfhb;
    }

    public String getCjhzpx() {
        return cjhzpx;
    }

    public void setCjhzpx(String cjhzpx) {
        this.cjhzpx = cjhzpx;
    }

    public String getJyqk() {
        return jyqk;
    }

    public void setJyqk(String jyqk) {
        this.jyqk = jyqk;
    }

    public String getWgdy() {
        return wgdy;
    }

    public void setWgdy(String wgdy) {
        this.wgdy = wgdy;
    }

    public String getWgdd() {
        return wgdd;
    }

    public void setWgdd(String wgdd) {
        this.wgdd = wgdd;
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getGwmc() {
        return gwmc;
    }

    public void setGwmc(String gwmc) {
        this.gwmc = gwmc;
    }

    public String getWgsj() {
        return wgsj;
    }

    public void setWgsj(String wgsj) {
        this.wgsj = wgsj;
    }

    public Float getYgz() {
        return ygz;
    }

    public void setYgz(Float ygz) {
        this.ygz = ygz;
    }

    public String getCyxm() {
        return cyxm;
    }

    public void setCyxm(String cyxm) {
        this.cyxm = cyxm;
    }

    public String getCydd() {
        return cydd;
    }

    public void setCydd(String cydd) {
        this.cydd = cydd;
    }

    public String getCysj() {
        return cysj;
    }

    public void setCysj(String cysj) {
        this.cysj = cysj;
    }

    public Float getYsr() {
        return ysr;
    }

    public void setYsr(Float ysr) {
        this.ysr = ysr;
    }

    public String getWgqx() {
        return wgqx;
    }

    public void setWgqx(String wgqx) {
        this.wgqx = wgqx;
    }

    public String getGyxgw() {
        return gyxgw;
    }

    public void setGyxgw(String gyxgw) {
        this.gyxgw = gyxgw;
    }

    public String getZzcy() {
        return zzcy;
    }

    public void setZzcy(String zzcy) {
        this.zzcy = zzcy;
    }

    public String getWfwcyy() {
        return wfwcyy;
    }

    public void setWfwcyy(String wfwcyy) {
        this.wfwcyy = wfwcyy;
    }

    public String getPxxq() {
        return pxxq;
    }

    public void setPxxq(String pxxq) {
        this.pxxq = pxxq;
    }

    public Float getZjd() {
        return zjd;
    }

    public void setZjd(Float zjd) {
        this.zjd = zjd;
    }

    public Float getGd() {
        return gd;
    }

    public void setGd(Float gd) {
        this.gd = gd;
    }

    public String getZzpz() {
        return zzpz;
    }

    public void setZzpz(String zzpz) {
        this.zzpz = zzpz;
    }

    public Float getZzdmj() {
        return zzdmj;
    }

    public void setZzdmj(Float zzdmj) {
        this.zzdmj = zzdmj;
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

    public String getYhzgx() {
        return yhzgx;
    }

    public void setYhzgx(String yhzgx) {
        this.yhzgx = yhzgx;
    }
}