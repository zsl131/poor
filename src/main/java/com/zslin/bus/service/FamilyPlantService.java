package com.zslin.bus.service;

import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.dao.IDictionaryDao;
import com.zslin.bus.dao.IFamilyPlantDao;
import com.zslin.bus.model.Dictionary;
import com.zslin.bus.model.FamilyPlant;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2019/5/14.
 */
@Service
public class FamilyPlantService {

    @Autowired
    private IFamilyPlantDao familyPlantDao;

    @Autowired
    private IDictionaryDao dictionaryDao;

    public JsonResult loadPlant(String params) {
        String hzsfzh = JsonTools.getJsonParam(params, "hzsfzh");
        List<Dictionary> dicList = dictionaryDao.findByPcode("DICT_PLANT"); //数据字典
        List<FamilyPlant> plantList = familyPlantDao.findByHzsfzh(hzsfzh);
        return JsonResult.success().set("dicList", dicList).set("plantList", plantList);
    }

    public JsonResult showPlant(String params) {
        String hzsfzh = JsonTools.getJsonParam(params, "hzsfzh");
        List<FamilyPlant> plantList = familyPlantDao.findByHzsfzh(hzsfzh);
        return JsonResult.success().set("plantList", plantList);
    }
}
