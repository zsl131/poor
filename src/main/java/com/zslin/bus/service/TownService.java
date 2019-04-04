package com.zslin.bus.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.MyBeanUtils;
import com.zslin.bus.common.dto.QueryListDto;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.common.tools.QueryTools;
import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.model.Town;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2019/4/5.
 */
@Service
@AdminAuth(psn = "业务管理", name = "乡镇管理", orderNum = 1, type = "1", url = "/admin/town")
public class TownService {

    @Autowired
    private ITownDao townDao;

    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Town> res = townDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));
        return JsonResult.success().set("size", (int)res.getTotalElements()).set("data", res.getContent());
    }

    public JsonResult listNoPage(String params) {
        List<Town> list = townDao.findAll();
        return JsonResult.success().set("list", list);
    }

    public JsonResult loadOne(String params) {
        Integer id = JsonTools.getId(params);
        Town obj = townDao.findOne(id);
        return JsonResult.succ(obj);
    }

    public JsonResult addOrUpdate(String params) {
        Town obj = JSONObject.toJavaObject(JSON.parseObject(params), Town.class);
        if(obj.getId()!=null && obj.getId()>0) { //修改
            Town t = townDao.getOne(obj.getId());
            MyBeanUtils.copyProperties(obj, t);
            townDao.save(t);
        } else {
            townDao.save(obj);
        }
        return JsonResult.success("保存成功");
    }

    public JsonResult delete(String params) {
        Integer id = JsonTools.getId(params);
        townDao.delete(id);
        return JsonResult.success("删除成功");
    }
}
