package com.zslin.bus.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.bus.common.dto.QueryListDto;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.common.tools.QueryTools;
import com.zslin.bus.dao.IAssetsDao;
import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.model.Assets;
import com.zslin.bus.model.Family;
import com.zslin.bus.model.Personal;
import com.zslin.bus.tools.JsonResult;
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

    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Personal> res = personalDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));
        return JsonResult.success().set("size", (int)res.getTotalElements()).set("data", res.getContent());
    }

    /** 获取家庭信息 */
    public JsonResult onShow(String params) {
        JsonResult result = JsonResult.getInstance();
        String type = JsonTools.getJsonParam(params, "type"); //类型，f-family，p-personal
        Integer id = JsonTools.getId(params); //ID
        Family f = null;
        if("f".equals(type)) {
            f = familyDao.findOne(id);
        } else if("p".equals(type)) {
            Personal p = personalDao.findOne(id);
            f = familyDao.findBySfzh(p.getHzsfzh());
            result.set("personal", p);
        }
        if(f!=null) {
            List<Personal> personalList = personalDao.findByHzsfzh(f.getSfzh());
            result.set("personalList", personalList);
        }
        result.set("family", f);

        return result;
    }

    public JsonResult loadOne(String params) {
        Integer id = JsonTools.getId(params);
        Personal p = personalDao.findOne(id);
        List<Assets> assetsList = assetsDao.findByGssfzh(p.getSfzh());
        return JsonResult.getInstance().set("personal", p).set("assetsList", assetsList);
    }

    /** 修改人员基本信息 */
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
            personalDao.save(obj);
            return JsonResult.success("数据修改成功");
        } catch (Exception e) {
//            e.printStackTrace();
            return JsonResult.error("数据保存失败："+e.getMessage());
        }
    }
}
