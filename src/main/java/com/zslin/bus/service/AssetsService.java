package com.zslin.bus.service;

import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.dao.IAssetsDao;
import com.zslin.bus.model.Assets;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2019/4/25.
 */
@Service
public class AssetsService {

    @Autowired
    private IAssetsDao assetsDao;

    /** 列表资产信息 */
    public JsonResult list(String params) {
        String hz = JsonTools.getJsonParam(params, "hz"); //是否是户主
        String mc = JsonTools.getJsonParam(params, "mc");
        boolean hasMc = (mc!=null && !"".equals(mc)); //是否有名称
        Integer id = JsonTools.getId(params); //人员ID
        List<Assets> list;
        if("1".equals(hz)) { //如果是户主
            if(hasMc) {list = assetsDao.findByHzidAndMc(id, mc);}
            else {list = assetsDao.findByHzid(id);}
        } else {
            if(hasMc) {list = assetsDao.findByGsidAndMc(id, mc);}
            else {list = assetsDao.findByGsid(id);}
        }
        return JsonResult.success("获取成功").set("list", list);
    }
}
