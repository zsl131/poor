package com.zslin.bus.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zslin.bus.poi.util.ExcelResources;

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

    /** 序号 */
    private Integer xh;

    /** 户主姓名 */
    private String xm;

    /** 身份证号 */
    private String sfzh;

    /** 家庭人数 */
    private Integer jtrs;

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

    /** 是否是劳动力,劳动力；无劳动力 */
    private String sfsldl;

    /** 就学情况 */

    /** 是否在校 */
    private String sfzx;

    /** 教育阶段 */
    private String jyjd;

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

    /** 资助项目名称 */
    private String zzxmmc;

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

    /** 务工地域， 1-省外；2-省内县外；3-县内*/
    private String wgdy;

    /** 务工省份 */
    private String wgsf;

    /** 务工城市 */
    private String wgcs;

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
    private Float zjd = 0f;

    /** 林地面积 */
    private Float ld = 0f;

    /** 耕地，面积 */
    private Float gd = 0f;

    /** 种植品种 */
    private String zzpz;

    /** 种植地面积 */
    private Float zzdmj = 0f;

    /** 可退耕面积 */
    private Float ktgmj = 0f;

    /** 户主ID */
    private Integer hzid;

    /** 户主姓名 */
    private String hzxm;

    /** 户主身份证号 */
    private String hzsfzh;

    /** 与户主关系 */
    private String yhzgx;

    /** 乡镇ID */
    private Integer xzid;

    /** 乡镇名称 */
    private String xzmc;

    /** 类型：卡户/随迁户 */
    private String lx;

    /** 照片路径 */
    private String zplj;

    /** 就业类型：外出务工；自主创业；未就业 */
    private String jylx;

    /** 村庄ID */
    private Integer czid;

    /** 村庄名称 */
    private String czmc;

    /** 残疾证号 */
    private String cjzh;

    /** 搬迁地点ID */
    private Integer bqddid;

    /** 20190602 **/

    /** 自然村 */
    private String zrc;

    /** 户编号 */
    private String hbh;

    /** 致贫原因 */
    private String zpyy;

    /** 安置方式 */
    private String azfs;

    /** 人口自然增减 */
    private String rkzrzj;

    /** 国办劳动力情况 */
    private String gbldlqk;

    public String getGbldlqk() {
        return gbldlqk;
    }

    public void setGbldlqk(String gbldlqk) {
        this.gbldlqk = gbldlqk;
    }

    @ExcelResources(title="自然村",order=3)
    public String getZrc() {
        return zrc;
    }

    public void setZrc(String zrc) {
        this.zrc = zrc;
    }

    @ExcelResources(title="户编号",order=4)
    public String getHbh() {
        return hbh;
    }

    public void setHbh(String hbh) {
        this.hbh = hbh;
    }

    @ExcelResources(title="致贫原因",order=46)
    public String getZpyy() {
        return zpyy;
    }

    public void setZpyy(String zpyy) {
        this.zpyy = zpyy;
    }

    @ExcelResources(title="安置方式",order=49)
    public String getAzfs() {
        return azfs;
    }

    public void setAzfs(String azfs) {
        this.azfs = azfs;
    }

    public String getRkzrzj() {
        return rkzrzj;
    }

    public void setRkzrzj(String rkzrzj) {
        this.rkzrzj = rkzrzj;
    }

    /** 20190602 **/

    public Integer getBqddid() {
        return bqddid;
    }

    public void setBqddid(Integer bqddid) {
        this.bqddid = bqddid;
    }

    @ExcelResources(title="可退耕面积",order=44)
    public Float getKtgmj() {
        return ktgmj;
    }

    public void setKtgmj(Float ktgmj) {
        this.ktgmj = ktgmj;
    }

    public String getCjzh() {
        return cjzh;
    }

    public void setCjzh(String cjzh) {
        this.cjzh = cjzh;
    }

    public String getWgsf() {
        return wgsf;
    }

    public void setWgsf(String wgsf) {
        this.wgsf = wgsf;
    }

    public String getWgcs() {
        return wgcs;
    }

    public void setWgcs(String wgcs) {
        this.wgcs = wgcs;
    }

    public Integer getCzid() {
        return czid;
    }

    public void setCzid(Integer czid) {
        this.czid = czid;
    }

    @ExcelResources(title="行政村",order=2)
    public String getCzmc() {
        return czmc;
    }

    public void setCzmc(String czmc) {
        this.czmc = czmc;
    }

    @ExcelResources(title="林地面积",order=42)
    public Float getLd() {
        return ld;
    }

    public void setLd(Float ld) {
        this.ld = ld;
    }

    public String getJylx() {
        return jylx;
    }

    public void setJylx(String jylx) {
        this.jylx = jylx;
    }

    @ExcelResources(title="资助项目",order=18)
    public String getZzxmmc() {
        return zzxmmc;
    }

    public void setZzxmmc(String zzxmmc) {
        this.zzxmmc = zzxmmc;
    }

    public String getJyjd() {
        return jyjd;
    }

    public void setJyjd(String jyjd) {
        this.jyjd = jyjd;
    }

    public String getZplj() {
        return zplj;
    }

    public void setZplj(String zplj) {
        this.zplj = zplj;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public Integer getXzid() {
        return xzid;
    }

    public void setXzid(Integer xzid) {
        this.xzid = xzid;
    }

    @ExcelResources(title="乡(镇)",order=1)
    public String getXzmc() {
        return xzmc;
    }

    public void setXzmc(String xzmc) {
        this.xzmc = xzmc;
    }

    @Override
    public String toString() {
        return "Personal{" +
                "id=" + id +
                ", xh=" + xh +
                ", xm='" + xm + '\'' +
                ", sfzh='" + sfzh + '\'' +
                ", jtrs=" + jtrs +
                ", jtdz='" + jtdz + '\'' +
                ", xb='" + xb + '\'' +
                ", mz='" + mz + '\'' +
                ", pksx='" + pksx + '\'' +
                ", yqhsx='" + yqhsx + '\'' +
                ", lxdh='" + lxdh + '\'' +
                ", bqdd='" + bqdd + '\'' +
                ", bqsj='" + bqsj + '\'' +
                ", bz='" + bz + '\'' +
                ", nl=" + nl +
                ", whcd='" + whcd + '\'' +
                ", sfsldl='" + sfsldl + '\'' +
                ", sfzx='" + sfzx + '\'' +
                ", jdxx='" + jdxx + '\'' +
                ", jdnj='" + jdnj + '\'' +
                ", sfxszz='" + sfxszz + '\'' +
                ", zzxm='" + zzxm + '\'' +
                ", zzje=" + zzje +
                ", jkzk='" + jkzk + '\'' +
                ", sfylbx='" + sfylbx + '\'' +
                ", sfyb='" + sfyb + '\'' +
                ", cbxz='" + cbxz + '\'' +
                ", cbdw='" + cbdw + '\'' +
                ", sfhb='" + sfhb + '\'' +
                ", cjhzpx='" + cjhzpx + '\'' +
                ", jyqk='" + jyqk + '\'' +
                ", wgdy='" + wgdy + '\'' +
                ", wgdd='" + wgdd + '\'' +
                ", qymc='" + qymc + '\'' +
                ", gwmc='" + gwmc + '\'' +
                ", wgsj='" + wgsj + '\'' +
                ", ygz=" + ygz +
                ", cyxm='" + cyxm + '\'' +
                ", cydd='" + cydd + '\'' +
                ", cysj='" + cysj + '\'' +
                ", ysr=" + ysr +
                ", wgqx='" + wgqx + '\'' +
                ", gyxgw='" + gyxgw + '\'' +
                ", zzcy='" + zzcy + '\'' +
                ", wfwcyy='" + wfwcyy + '\'' +
                ", pxxq='" + pxxq + '\'' +
                ", zjd=" + zjd +
                ", gd=" + gd +
                ", zzpz='" + zzpz + '\'' +
                ", zzdmj=" + zzdmj +
                ", hzid=" + hzid +
                ", hzxm='" + hzxm + '\'' +
                ", hzsfzh='" + hzsfzh + '\'' +
                ", yhzgx='" + yhzgx + '\'' +
                ", xzid=" + xzid +
                ", xzmc='" + xzmc + '\'' +
                '}';
    }

    @ExcelResources(title="家庭人数",order=7)
    public Integer getJtrs() {
        return jtrs;
    }

    public void setJtrs(Integer jtrs) {
        this.jtrs = jtrs;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ExcelResources(title="姓名",order=5)
    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    @ExcelResources(title="身份证号",order=6)
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

    @ExcelResources(title="性别",order=10)
    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    @ExcelResources(title="民族",order=9)
    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    @ExcelResources(title="脱贫属性",order=13)
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

    @ExcelResources(title="联系电话",order=45)
    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    @ExcelResources(title="搬迁地点",order=48)
    public String getBqdd() {
        return bqdd;
    }

    public void setBqdd(String bqdd) {
        this.bqdd = bqdd;
    }

    @ExcelResources(title="搬迁年度",order=47)
    public String getBqsj() {
        return bqsj;
    }

    public void setBqsj(String bqsj) {
        this.bqsj = bqsj;
    }

    @ExcelResources(title="备注",order=50)
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

    @ExcelResources(title="文化程度",order=11)
    public String getWhcd() {
        return whcd;
    }

    public void setWhcd(String whcd) {
        this.whcd = whcd;
    }

    @ExcelResources(title="劳动力",order=12)
    public String getSfsldl() {
        return sfsldl;
    }

    public void setSfsldl(String sfsldl) {
        this.sfsldl = sfsldl;
    }

    @ExcelResources(title="是否在校",order=14)
    public String getSfzx() {
        return sfzx;
    }

    public void setSfzx(String sfzx) {
        this.sfzx = sfzx;
    }

    @ExcelResources(title="就读学校",order=15)
    public String getJdxx() {
        return jdxx;
    }

    public void setJdxx(String jdxx) {
        this.jdxx = jdxx;
    }

    @ExcelResources(title="就读年级",order=16)
    public String getJdnj() {
        return jdnj;
    }

    public void setJdnj(String jdnj) {
        this.jdnj = jdnj;
    }

    @ExcelResources(title="是否享受资助",order=17)
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

    @ExcelResources(title="资助金额",order=19)
    public Float getZzje() {
        return zzje;
    }

    public void setZzje(Float zzje) {
        this.zzje = zzje;
    }

    @ExcelResources(title="健康状况",order=20)
    public String getJkzk() {
        return jkzk;
    }

    public void setJkzk(String jkzk) {
        this.jkzk = jkzk;
    }

    @ExcelResources(title="城乡居民养老保险是否参保",order=21)
    public String getSfylbx() {
        return sfylbx;
    }

    public void setSfylbx(String sfylbx) {
        this.sfylbx = sfylbx;
    }

    @ExcelResources(title="是否参加基本医疗保险",order=22)
    public String getSfyb() {
        return sfyb;
    }

    public void setSfyb(String sfyb) {
        this.sfyb = sfyb;
    }

    @ExcelResources(title="参保险种（居民、职工）",order=23)
    public String getCbxz() {
        return cbxz;
    }

    public void setCbxz(String cbxz) {
        this.cbxz = cbxz;
    }

    @ExcelResources(title="参保地或参保单位",order=24)
    public String getCbdw() {
        return cbdw;
    }

    public void setCbdw(String cbdw) {
        this.cbdw = cbdw;
    }

    @ExcelResources(title="是否患特殊病慢性病或者9类15种重大疾病（儿童白血病（含急性淋巴细胞白血病和急性早幼粒细胞白血病）、儿童先天性心脏病（含房间隔缺损、室间隔缺损、动脉导管未闭、肺动脉瓣狭窄、法式四联征以及合并两种或以上的复杂性先心病）、食管癌、胃癌、结肠癌、直肠癌、终末期肾病、重性精神病、耐多药结核）",order=25)
    public String getSfhb() {
        return sfhb;
    }

    public void setSfhb(String sfhb) {
        this.sfhb = sfhb;
    }

    @ExcelResources(title="参加过何种培训",order=26)
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

    @ExcelResources(title="务工地点",order=27)
    public String getWgdd() {
        return wgdd;
    }

    public void setWgdd(String wgdd) {
        this.wgdd = wgdd;
    }

    @ExcelResources(title="企业名称",order=28)
    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    @ExcelResources(title="岗位名称",order=29)
    public String getGwmc() {
        return gwmc;
    }

    public void setGwmc(String gwmc) {
        this.gwmc = gwmc;
    }

    @ExcelResources(title="务工时间",order=30)
    public String getWgsj() {
        return wgsj;
    }

    public void setWgsj(String wgsj) {
        this.wgsj = wgsj;
    }

    @ExcelResources(title="月工资收入",order=31)
    public Float getYgz() {
        return ygz;
    }

    public void setYgz(Float ygz) {
        this.ygz = ygz;
    }

    @ExcelResources(title="创业项目",order=32)
    public String getCyxm() {
        return cyxm;
    }

    public void setCyxm(String cyxm) {
        this.cyxm = cyxm;
    }

    @ExcelResources(title="企业地点",order=33)
    public String getCydd() {
        return cydd;
    }

    public void setCydd(String cydd) {
        this.cydd = cydd;
    }

    @ExcelResources(title="企业时间",order=34)
    public String getCysj() {
        return cysj;
    }

    public void setCysj(String cysj) {
        this.cysj = cysj;
    }

    @ExcelResources(title="月收入",order=35)
    public Float getYsr() {
        return ysr;
    }

    public void setYsr(Float ysr) {
        this.ysr = ysr;
    }

    @ExcelResources(title="务工去向",order=36)
    public String getWgqx() {
        return wgqx;
    }

    public void setWgqx(String wgqx) {
        this.wgqx = wgqx;
    }

    @ExcelResources(title="公益性岗位",order=37)
    public String getGyxgw() {
        return gyxgw;
    }

    public void setGyxgw(String gyxgw) {
        this.gyxgw = gyxgw;
    }

    @ExcelResources(title="自主创业",order=38)
    public String getZzcy() {
        return zzcy;
    }

    public void setZzcy(String zzcy) {
        this.zzcy = zzcy;
    }

    @ExcelResources(title="无法外出原因",order=39)
    public String getWfwcyy() {
        return wfwcyy;
    }

    public void setWfwcyy(String wfwcyy) {
        this.wfwcyy = wfwcyy;
    }

    @ExcelResources(title="培训需求",order=40)
    public String getPxxq() {
        return pxxq;
    }

    public void setPxxq(String pxxq) {
        this.pxxq = pxxq;
    }

    @ExcelResources(title="宅基地面积",order=43)
    public Float getZjd() {
        return zjd;
    }

    public void setZjd(Float zjd) {
        this.zjd = zjd;
    }

    @ExcelResources(title="耕地面积",order=41)
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

    @ExcelResources(title="与户主关系",order=8)
    public String getYhzgx() {
        return yhzgx;
    }

    public void setYhzgx(String yhzgx) {
        this.yhzgx = yhzgx;
    }
}
