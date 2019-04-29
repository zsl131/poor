package com.zslin.bus.service;

import org.json.JSONArray;
import com.zslin.bus.common.annotations.Function;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.dao.IUserTownDao;
import com.zslin.bus.dto.TownDto;
import com.zslin.bus.model.Town;
import com.zslin.bus.model.UserTown;
import com.zslin.bus.tools.JsonResult;
import com.zslin.bus.tools.TownTools;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2019/4/12.
 */
@Service
public class UserTownService {

    @Autowired
    private IUserTownDao userTownDao;

    @Autowired
    private ITownDao townDao;

    @Autowired
    private TownTools townTools;

    public JsonResult onSetUserTown(String params) {
        Integer userId = Integer.parseInt(JsonTools.getJsonParam(params, "userId"));
        List<Integer> townIds = userTownDao.findTownId(userId);
//        List<Town> townList = townDao.findAll();
        List<TownDto> treeDto = townTools.buildTown();
        return JsonResult.success("获取成功").set("townIds", townIds).set("treeDto", treeDto); //.set("townList", townList)
    }

    @Function("为用户授权乡镇")
    public JsonResult setUserTown(String params) {
        Integer userId = Integer.parseInt(JsonTools.getJsonParam(params, "userId"));
        Integer townId = Integer.parseInt(JsonTools.getJsonParam(params, "townId"));
        String username = JsonTools.getJsonParam(params, "username");
        UserTown ut = userTownDao.findByUserIdAndTownId(userId, townId);
        if(ut==null) {
            ut = new UserTown();
            ut.setTownId(townId);
            ut.setUsername(username);
            ut.setUserId(userId);
            userTownDao.save(ut);
            return JsonResult.success("设置成功");
        } else {
            userTownDao.delete(ut);
            return JsonResult.success("取消成功");
        }
    }

    public JsonResult saveUserTown(String params) {
        String username = JsonTools.getJsonParam(params, "username");
        Integer userId = Integer.parseInt(JsonTools.getJsonParam(params, "userId"));
        JSONArray array = JsonTools.str2JsonArray(JsonTools.getJsonParam(params, "townIds"));

        userTownDao.deleteTownByUserId(userId); //先删除

        System.out.println(params);
        for(int i=0;i<array.length();i++) {
            Integer val = array.getInt(i);
//            System.out.println(i+": value : "+val);
            UserTown ut = new UserTown();
            ut.setTownId(val);
            ut.setUsername(username);
            ut.setUserId(userId);
            userTownDao.save(ut);
        }
        return JsonResult.success("保存成功");
    }
}
