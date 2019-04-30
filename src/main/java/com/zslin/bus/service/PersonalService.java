package com.zslin.bus.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.bus.common.annotations.Function;
import com.zslin.bus.common.dto.QueryListDto;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.common.tools.QueryTools;
import com.zslin.bus.dao.IAssetsDao;
import com.zslin.bus.dao.IDictionaryDao;
import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.dto.PersonalCountDto;
import com.zslin.bus.dto.PieDto;
import com.zslin.bus.model.Assets;
import com.zslin.bus.model.Dictionary;
import com.zslin.bus.model.Family;
import com.zslin.bus.model.Personal;
import com.zslin.bus.tools.JsonResult;
import com.zslin.bus.tools.PersonalCountTools;
import com.zslin.bus.tools.PersonalTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2019/4/17.
 */
@Service
@AdminAuth(psn = "业务管理", name = "易迁人员管理", orderNum = 1, type = "1", url = "/admin/personal")
public class PersonalService {

    @Autowired
    private IFamilyDao familyDao;

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IAssetsDao assetsDao;

    @Autowired
    private IDictionaryDao dictionaryDao;

    @Autowired
    private PersonalCountTools personalCountTools;

    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Personal> res = personalDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));
        List<PieDto> xb = personalDao.findPieByXb();
        return JsonResult.success().set("size", (int)res.getTotalElements()).set("data", res.getContent()).set("xbPie", xb);
    }

    /** 获取家庭信息 */
    public JsonResult onShow(String params) {
        JsonResult result = JsonResult.getInstance();
        String type = JsonTools.getJsonParam(params, "type"); //类型，f-family，p-personal
        Integer id = JsonTools.getId(params); //ID
        Family f = null;
        Personal p = null;
        if("f".equals(type)) {
            f = familyDao.findOne(id);
            p = personalDao.findBySfzh(f.getSfzh());
        } else if("p".equals(type)) {
            p = personalDao.findOne(id);
            f = familyDao.findBySfzh(p.getHzsfzh());
        }
        if(f!=null) {
            List<Personal> personalList = personalDao.findByHzsfzh(f.getSfzh());
            result.set("personalList", personalList);
        }
        List<Assets> assetsList = assetsDao.findByHzsfzh(p.getHzsfzh());
        result.set("family", f).set("personal", p).set("assetsList", assetsList);

        return result;
    }

    /** 获取最新统计DTO对象，参数：townId */
    public JsonResult queryCountDto(String params) {
        Integer townId = Integer.parseInt(JsonTools.getJsonParam(params, "townId"));
        List<PersonalCountDto> dto = personalCountTools.buildCountDto(townId);
        return JsonResult.success("获取成功").set("countDto", dto);
    }

    public JsonResult loadOne(String params) {
        Integer id = JsonTools.getId(params);
        Personal p = personalDao.findOne(id);
        List<Assets> assetsList = assetsDao.findByGssfzh(p.getSfzh());
        return JsonResult.getInstance().set("personal", p).set("assetsList", assetsList);
    }

    /** 修改人员基本信息 */
    @Function("修改人员基本信息")
    public JsonResult updateBasic(String params) {
        try {
            Personal p = JSONObject.toJavaObject(JSON.parseObject(params), Personal.class);
            Personal obj = personalDao.findOne(p.getId());
            obj.setXm(p.getXm());
            obj.setSfzh(p.getSfzh());
            obj.setXb(p.getXb());
            obj.setMz(p.getMz());
            obj.setWhcd(p.getWhcd());
            obj.setLxdh(p.getLxdh());
            obj.setSfsldl(p.getSfsldl());
            obj.setPksx(p.getPksx());
            obj.setJtdz(p.getJtdz());
            obj.setZplj(p.getZplj());
            obj.setPxxq(p.getPxxq());
            obj.setCjhzpx(p.getCjhzpx());
            personalDao.save(obj);
            return JsonResult.success("数据修改成功");
        } catch (Exception e) {
//            e.printStackTrace();
            return JsonResult.error("数据保存失败："+e.getMessage());
        }
    }

    /** 修改人员就业情况 */
    @Function("修改人员就业信息")
    public JsonResult updateWork(String params) {
        try {
            Personal p = JSONObject.toJavaObject(JSON.parseObject(params), Personal.class);
            Personal obj = personalDao.findOne(p.getId());
            String jylx = p.getJylx(); //1-外出务工；2-自主创业；3-未就业
            if("1".equals(jylx)) { //外出务工
                obj.setWgdd(p.getWgdd());
                obj.setQymc(p.getQymc());
                obj.setGwmc(p.getGwmc());
                obj.setWgsj(p.getWgsj());
                obj.setYgz(p.getYgz());
                obj.setJylx(PersonalTools.buildJyLx(obj));
                personalDao.save(obj);
            } else if("2".equals(jylx)) { //自主创业
                obj.setCyxm(p.getCyxm());
                obj.setCydd(p.getCydd());
                obj.setCysj(p.getCysj());
                obj.setYsr(p.getYsr());
                obj.setJylx(PersonalTools.buildJyLx(obj));
                personalDao.save(obj);
            } else if("3".equals(jylx)) { //未就业
                obj.setWgqx(p.getWgqx());
                obj.setGyxgw(p.getGyxgw());
                obj.setZzcy(p.getZzcy());
                obj.setWfwcyy(p.getWfwcyy());
                obj.setJylx(PersonalTools.buildJyLx(obj));
                personalDao.save(obj);
            }
            return JsonResult.success("修改就业情况成功");
        } catch (Exception e) {
//            e.printStackTrace();
            return JsonResult.error("修改出错："+e.getMessage());
        }
    }

    /** 修改人员搬迁信息 */
    @Function("修改人员搬迁信息")
    public JsonResult updateMove(String params) {
        try {
            Personal p = JSONObject.toJavaObject(JSON.parseObject(params), Personal.class);
            Personal obj = personalDao.findOne(p.getId());
            obj.setBqsj(p.getBqsj());
            obj.setBqdd(p.getBqdd());
            obj.setBz(p.getBz());
            personalDao.save(obj);
            return JsonResult.success("修改搬迁信息成功");
        } catch (Exception e) {
//            e.printStackTrace();
            return JsonResult.error("数据保存失败："+e.getMessage());
        }
    }

    /** 修改人员就学信息 */
    @Function("修改人员就学信息")
    public JsonResult updateStudy(String params) {
        try {
            Personal p = JSONObject.toJavaObject(JSON.parseObject(params), Personal.class);
            Personal obj = personalDao.findOne(p.getId());
            String sfxszz = p.getSfxszz(); //是否享受资助
            obj.setSfzx(p.getSfzx());
            obj.setJyjd(p.getJyjd());
            obj.setJdxx(p.getJdxx());
            obj.setJdnj(p.getJdnj());
            obj.setSfxszz(sfxszz);
            if("是".equals(sfxszz)) {
                obj.setZzje(p.getZzje());
                obj.setZzxm(p.getZzxm());
                List<Dictionary> dataList = dictionaryDao.findByPcode("DICT_SUPPORT");
                obj.setZzxmmc(PersonalTools.buildZzxmmc(dataList, p.getZzxm()));
            } else {
                obj.setZzje(0f);
                obj.setZzxm("");
                obj.setZzxmmc("");
            }
            personalDao.save(obj);
            return JsonResult.success("修改就学信息成功");
        } catch (Exception e) {
//            e.printStackTrace();
            return JsonResult.error("数据保存失败："+e.getMessage());
        }
    }

    /** 修改人员保险信息 */
    @Function("修改人员保险信息")
    public JsonResult updateSafe(String params) {
        try {
            Personal p = JSONObject.toJavaObject(JSON.parseObject(params), Personal.class);
            Personal obj = personalDao.findOne(p.getId());
            obj.setJkzk(p.getJkzk());
            obj.setSfylbx(p.getSfylbx());
            obj.setSfyb(p.getSfyb());
            obj.setCbxz(p.getCbxz());
            obj.setCbdw(p.getCbdw());
            obj.setSfhb(p.getSfhb());
            personalDao.save(obj);
            return JsonResult.success("修改保险信息成功");
        } catch (Exception e) {
//            e.printStackTrace();
            return JsonResult.error("数据保存失败："+e.getMessage());
        }
    }

    @Function("添加资产信息")
    public JsonResult addAssets(String params) {
        try {
            Assets a = JSONObject.toJavaObject(JSON.parseObject(params), Assets.class);
            Personal p = personalDao.findOne(a.getGsid());
            a.setGssfzh(p.getSfzh());
            a.setGsxm(p.getXm());
            a.setHzid(p.getHzid());
            a.setHzsfzh(p.getHzsfzh());
            a.setHzxm(p.getHzxm());
            assetsDao.save(a);
            return JsonResult.success("资产图片保存成功");
        } catch (Exception e) {
            return JsonResult.success("资产图片保存失败："+e.getMessage());
        }
    }

    /** 修改人员产业信息 */
    @Function("修改人员产业信息")
    public JsonResult updateIndustry(String params) {
        try {
            Personal p = JSONObject.toJavaObject(JSON.parseObject(params), Personal.class);
            Personal obj = personalDao.findOne(p.getId());
            Float zjd = p.getZjd(), ld = p.getLd(), gd = p.getGd(), zzdmj = p.getZzdmj();
            zjd = zjd==null?0f:zjd; ld = ld==null?0:ld; gd = gd==null?0:gd; zzdmj = zzdmj==null?0:zzdmj;
            String zzpz = p.getZzpz();
            obj.setZjd(zjd);
            obj.setLd(ld);
            obj.setGd(gd);
            obj.setZzpz(zzpz);
            obj.setZzdmj(zzdmj);
            personalDao.save(obj);
            familyDao.updateIndustry(zjd, ld, gd, zzpz, zzdmj, obj.getHzsfzh());
            return JsonResult.success("修改产业信息成功");
        } catch (Exception e) {
//            e.printStackTrace();
            return JsonResult.error("数据保存失败："+e.getMessage());
        }
    }
}
