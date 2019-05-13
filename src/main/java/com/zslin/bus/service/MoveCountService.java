package com.zslin.bus.service;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dto.PieDto;
import com.zslin.bus.model.Family;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2019/5/13.
 */
@Service
@AdminAuth(psn = "业务管理", name = "搬迁统计管理", orderNum = 1, type = "1", url = "/admin/moveCount")
public class MoveCountService {

    @Autowired
    private IFamilyDao familyDao;

    public JsonResult index(String params) {
        List<PieDto> data = familyDao.findPieByBqdd();
        return JsonResult.getInstance().set("data", data);
    }

    public JsonResult findByBqsj(String params) {
        String bqdd = JsonTools.getJsonParam(params, "bqdd"); //搬迁地点
        List<PieDto> data = familyDao.findPieByBqsj(bqdd);
        return JsonResult.getInstance().set("data", data);
    }

    public JsonResult queryData(String params) {
        String bqdd = JsonTools.getJsonParam(params, "bqdd"); //搬迁地点
        String bqsj = JsonTools.getJsonParam(params, "bqsj"); //搬迁时间
        List<Family> res ;
        if(bqsj==null || "".equalsIgnoreCase(bqsj)) {
            res = familyDao.findByBqdd(bqdd);
        } else {
            res = familyDao.findByBqddAndBqsj(bqdd, bqsj);
        }
        return JsonResult.success().set("familyList", res);
    }
}
