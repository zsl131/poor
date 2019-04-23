package com.zslin.bus.service;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.dto.PieDto;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2019/4/22.
 */
@Service
@AdminAuth(psn = "业务管理", name = "统计管理", orderNum = 1, type = "1", url = "/admin/count")
public class CountService {

    @Autowired
    private IFamilyDao familyDao;

    @Autowired
    private IPersonalDao personalDao;

    public JsonResult index(String params) {
        JsonResult result = JsonResult.getInstance();
        /*List<PieDto> xbPie = personalDao.findPieByXb(); //性别饼状图
        result.set("xbPie", xbPie);*/
        return result;
    }

    /** 性别饼状图 */
    public JsonResult xbPie(String params) {
        List<PieDto> xbPie = personalDao.findPieByXb(); //性别饼状图
        return JsonResult.getInstance().set("xbPie", xbPie);
    }

    /** 是否是劳动力饼状图 */
    public JsonResult ldlPie(String params) {
        List<PieDto> data = personalDao.findPieByLdl();
        return JsonResult.getInstance().set("data", data);
    }

    /** 民族柱状态图 */
    public JsonResult mzPie(String params) {
        List<PieDto> data = personalDao.findPieByMz();
        return JsonResult.getInstance().set("data", data);
    }

    /** 贫困属性柱状图 */
    public JsonResult pksxPie(String params) {
        List<PieDto> data = personalDao.findPieByPksx();
        return JsonResult.getInstance().set("data", data);
    }

    /** 类型饼状图 */
    public JsonResult lxPie(String params) {
        List<PieDto> data = personalDao.findPieByLx();
        return JsonResult.getInstance().set("data", data);
    }

    /** 是否医保饼状图 */
    public JsonResult sfybPie(String params) {
        List<PieDto> data = personalDao.findPieBySfyb();
        return JsonResult.getInstance().set("data", data);
    }

    /** 参保险种饼状图 */
    public JsonResult cbxzPie(String params) {
        List<PieDto> data = personalDao.findPieByCbxz();
        return JsonResult.getInstance().set("data", data);
    }

    /** 是否患病饼状图 */
    public JsonResult sfhbPie(String params) {
        List<PieDto> data = personalDao.findPieBySfhb();
        return JsonResult.getInstance().set("data", data);
    }
}
