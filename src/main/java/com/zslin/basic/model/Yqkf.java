package com.zslin.basic.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="a_yqkf")
@JsonInclude(value= JsonInclude.Include.NON_NULL)
public class Yqkf {
    //id
    private int id;
    //户主姓名
    private String xm;
    //户主关系
    private String gx;
    //冗余是否是户主
    private int sfhz;
    //身份证
    private String sfzh;
    //性别
    private String xb;
    //年龄
    private String nl;
    //家庭地址
    private String jtdz;
    //脱贫属性
    private String tpsx;
    //易迁户属性
    private String yqhsx;
    //是否是劳动力
    private String sfsldl;
    //文化程度
    private String whcd;
    //就业情况_是否在校
    private String jxqk_sfzx;
    //就业情况_就读学校
    private String jxqk_jdqk;
    //就业情况_就读年级
    private String jxqk_jdnj;
    //享受资助情况_是否享受
    private String xszzqk_sfxs;
    //享受资助情况_资助项目
    private String xszzqk_zzxm;
    //享受资助情况_资助金额
    private String xszzqk_zzje;
    //健康状况
    private String jkzk;
    //城乡居民养老保险是否参保
    private String cxsfcb;
    //基本医疗保险参保情况及患病情况_是否参加基本医疗保险
    private String ylbxqk_sfcj;
    //基本医疗保险参保情况及患病情况_参保险种
    private String ylbxqk_cbxz;
    //基本医疗保险参保情况及患病情况_参保地或者参保单位
    private String ylbxqk_cbd;
    //基本医疗保险参保情况及患病情况_是否患特殊病慢性病或者9类15种重大疾病
    private String ylbxqk_sftsb;
    //参加过何种培训
    private String cjpx;
    //已就业_外出务工_务工地域
    private String yjy_wc_wgdy;
    //已就业_外出务工_务工地点
    private String yjy_wc_wgdd;
    //已就业_外出务工_企业名称
    private String yjy_wc_qymc;
    //已就业_外出务工_岗位名称
    private String yjy_wc_gwmc;
    //已就业_外出务工_务工时间
    private String yjy_wc_wgsj;
    //已就业_外出务工_月收入
    private String yjy_wc_ysr;
    //已就业_自主创业_创业项目
    private String yjy_zzcy_cyxm;
    //已就业_自主创业_创业地点
    private String yjy_zzcy_cydd;
    //已就业_自主创业_创业时间
    private String yjy_zzcy_cysj;
    //已就业_自主创业_月收入
    private String yjy_zzcy_ysr;
    //未就业_就业意愿_务工去向
    private String wjy_jyyy_wgqx;
    //未就业_就业意愿_公益性岗位
    private String wjy_jyyy_gyxgw;
    //未就业_就业意愿_自主创业
    private String wjy_jyyy_zzcy;
    //未就业_无法外出原因
    private String wjy_wfwcyy;
    //培训需求
    private String pxxq;
    //三块地_宅基地
    private String skd_zjd;
    //三块地_耕地
    private String skd_gd;
    //产业_种植品种
    private String cy_zzpz;
    //产业_种植面积
    private String cy_mj;
    //联系电话
    private String lxdh;
    //搬迁地点
    private String bqdd;
    //搬迁时间
    private String bqsj;
    //备注
    private String bz;
    //创建时间
    private Date createDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getGx() {
        return gx;
    }

    public void setGx(String gx) {
        this.gx = gx;
    }

    public int getSfhz() {
        return sfhz;
    }

    public void setSfhz(int sfhz) {
        this.sfhz = sfhz;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getNl() {
        return nl;
    }

    public void setNl(String nl) {
        this.nl = nl;
    }

    public String getJtdz() {
        return jtdz;
    }

    public void setJtdz(String jtdz) {
        this.jtdz = jtdz;
    }

    public String getTpsx() {
        return tpsx;
    }

    public void setTpsx(String tpsx) {
        this.tpsx = tpsx;
    }

    public String getYqhsx() {
        return yqhsx;
    }

    public void setYqhsx(String yqhsx) {
        this.yqhsx = yqhsx;
    }

    public String getSfsldl() {
        return sfsldl;
    }

    public void setSfsldl(String sfsldl) {
        this.sfsldl = sfsldl;
    }

    public String getWhcd() {
        return whcd;
    }

    public void setWhcd(String whcd) {
        this.whcd = whcd;
    }

    public String getJxqk_sfzx() {
        return jxqk_sfzx;
    }

    public void setJxqk_sfzx(String jxqk_sfzx) {
        this.jxqk_sfzx = jxqk_sfzx;
    }

    public String getJxqk_jdqk() {
        return jxqk_jdqk;
    }

    public void setJxqk_jdqk(String jxqk_jdqk) {
        this.jxqk_jdqk = jxqk_jdqk;
    }

    public String getJxqk_jdnj() {
        return jxqk_jdnj;
    }

    public void setJxqk_jdnj(String jxqk_jdnj) {
        this.jxqk_jdnj = jxqk_jdnj;
    }

    public String getXszzqk_sfxs() {
        return xszzqk_sfxs;
    }

    public void setXszzqk_sfxs(String xszzqk_sfxs) {
        this.xszzqk_sfxs = xszzqk_sfxs;
    }

    public String getXszzqk_zzxm() {
        return xszzqk_zzxm;
    }

    public void setXszzqk_zzxm(String xszzqk_zzxm) {
        this.xszzqk_zzxm = xszzqk_zzxm;
    }

    public String getXszzqk_zzje() {
        return xszzqk_zzje;
    }

    public void setXszzqk_zzje(String xszzqk_zzje) {
        this.xszzqk_zzje = xszzqk_zzje;
    }

    public String getJkzk() {
        return jkzk;
    }

    public void setJkzk(String jkzk) {
        this.jkzk = jkzk;
    }

    public String getCxsfcb() {
        return cxsfcb;
    }

    public void setCxsfcb(String cxsfcb) {
        this.cxsfcb = cxsfcb;
    }

    public String getYlbxqk_sfcj() {
        return ylbxqk_sfcj;
    }

    public void setYlbxqk_sfcj(String ylbxqk_sfcj) {
        this.ylbxqk_sfcj = ylbxqk_sfcj;
    }

    public String getYlbxqk_cbxz() {
        return ylbxqk_cbxz;
    }

    public void setYlbxqk_cbxz(String ylbxqk_cbxz) {
        this.ylbxqk_cbxz = ylbxqk_cbxz;
    }

    public String getYlbxqk_cbd() {
        return ylbxqk_cbd;
    }

    public void setYlbxqk_cbd(String ylbxqk_cbd) {
        this.ylbxqk_cbd = ylbxqk_cbd;
    }

    public String getYlbxqk_sftsb() {
        return ylbxqk_sftsb;
    }

    public void setYlbxqk_sftsb(String ylbxqk_sftsb) {
        this.ylbxqk_sftsb = ylbxqk_sftsb;
    }

    public String getCjpx() {
        return cjpx;
    }

    public void setCjpx(String cjpx) {
        this.cjpx = cjpx;
    }

    public String getYjy_wc_wgdy() {
        return yjy_wc_wgdy;
    }

    public void setYjy_wc_wgdy(String yjy_wc_wgdy) {
        this.yjy_wc_wgdy = yjy_wc_wgdy;
    }

    public String getYjy_wc_wgdd() {
        return yjy_wc_wgdd;
    }

    public void setYjy_wc_wgdd(String yjy_wc_wgdd) {
        this.yjy_wc_wgdd = yjy_wc_wgdd;
    }

    public String getYjy_wc_qymc() {
        return yjy_wc_qymc;
    }

    public void setYjy_wc_qymc(String yjy_wc_qymc) {
        this.yjy_wc_qymc = yjy_wc_qymc;
    }

    public String getYjy_wc_gwmc() {
        return yjy_wc_gwmc;
    }

    public void setYjy_wc_gwmc(String yjy_wc_gwmc) {
        this.yjy_wc_gwmc = yjy_wc_gwmc;
    }

    public String getYjy_wc_wgsj() {
        return yjy_wc_wgsj;
    }

    public void setYjy_wc_wgsj(String yjy_wc_wgsj) {
        this.yjy_wc_wgsj = yjy_wc_wgsj;
    }

    public String getYjy_wc_ysr() {
        return yjy_wc_ysr;
    }

    public void setYjy_wc_ysr(String yjy_wc_ysr) {
        this.yjy_wc_ysr = yjy_wc_ysr;
    }

    public String getYjy_zzcy_cyxm() {
        return yjy_zzcy_cyxm;
    }

    public void setYjy_zzcy_cyxm(String yjy_zzcy_cyxm) {
        this.yjy_zzcy_cyxm = yjy_zzcy_cyxm;
    }

    public String getYjy_zzcy_cydd() {
        return yjy_zzcy_cydd;
    }

    public void setYjy_zzcy_cydd(String yjy_zzcy_cydd) {
        this.yjy_zzcy_cydd = yjy_zzcy_cydd;
    }

    public String getYjy_zzcy_cysj() {
        return yjy_zzcy_cysj;
    }

    public void setYjy_zzcy_cysj(String yjy_zzcy_cysj) {
        this.yjy_zzcy_cysj = yjy_zzcy_cysj;
    }

    public String getYjy_zzcy_ysr() {
        return yjy_zzcy_ysr;
    }

    public void setYjy_zzcy_ysr(String yjy_zzcy_ysr) {
        this.yjy_zzcy_ysr = yjy_zzcy_ysr;
    }

    public String getWjy_jyyy_wgqx() {
        return wjy_jyyy_wgqx;
    }

    public void setWjy_jyyy_wgqx(String wjy_jyyy_wgqx) {
        this.wjy_jyyy_wgqx = wjy_jyyy_wgqx;
    }

    public String getWjy_jyyy_gyxgw() {
        return wjy_jyyy_gyxgw;
    }

    public void setWjy_jyyy_gyxgw(String wjy_jyyy_gyxgw) {
        this.wjy_jyyy_gyxgw = wjy_jyyy_gyxgw;
    }

    public String getWjy_jyyy_zzcy() {
        return wjy_jyyy_zzcy;
    }

    public void setWjy_jyyy_zzcy(String wjy_jyyy_zzcy) {
        this.wjy_jyyy_zzcy = wjy_jyyy_zzcy;
    }

    public String getWjy_wfwcyy() {
        return wjy_wfwcyy;
    }

    public void setWjy_wfwcyy(String wjy_wfwcyy) {
        this.wjy_wfwcyy = wjy_wfwcyy;
    }

    public String getPxxq() {
        return pxxq;
    }

    public void setPxxq(String pxxq) {
        this.pxxq = pxxq;
    }

    public String getSkd_zjd() {
        return skd_zjd;
    }

    public void setSkd_zjd(String skd_zjd) {
        this.skd_zjd = skd_zjd;
    }

    public String getSkd_gd() {
        return skd_gd;
    }

    public void setSkd_gd(String skd_gd) {
        this.skd_gd = skd_gd;
    }

    public String getCy_zzpz() {
        return cy_zzpz;
    }

    public void setCy_zzpz(String cy_zzpz) {
        this.cy_zzpz = cy_zzpz;
    }

    public String getCy_mj() {
        return cy_mj;
    }

    public void setCy_mj(String cy_mj) {
        this.cy_mj = cy_mj;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
