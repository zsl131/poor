package com.zslin.bus.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.MyBeanUtils;
import com.zslin.bus.common.annotations.Function;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.dao.IUserTownDao;
import com.zslin.bus.dto.TownDto;
import com.zslin.bus.model.Town;
import com.zslin.bus.tools.JsonResult;
import com.zslin.bus.tools.TownTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsl on 2019/4/5.
 */
@Service
@AdminAuth(psn = "业务管理", name = "乡镇管理", orderNum = 1, type = "1", url = "/admin/town")
public class TownService {

    @Autowired
    private ITownDao townDao;

    @Autowired
    private IUserTownDao userTownDao;

    @Autowired
    private TownTools townTools;

    public JsonResult list(String params) {
        /*QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Town> res = townDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));
        return JsonResult.success().set("size", (int)res.getTotalElements()).set("data", res.getContent());*/

        JsonResult result = JsonResult.getInstance();
        Integer pid = 0;
        try { pid = Integer.parseInt(JsonTools.getJsonParam(params, "pid"));} catch (Exception e) {pid=0;}
        List<TownDto> treeDto = townTools.buildTown();
        Sort sort = SimpleSortBuilder.generateSort("orderNo");
        List<Town> list ;
        if(pid==0 || pid==1) {
            list = townDao.findParent(sort);
        } else {
            list = townDao.findByPid(pid, sort);
            result.set("town", townDao.findOne(pid));
        }
        return result.set("size", list.size()).set("data", list).set("treeDto", treeDto);
    }

    public JsonResult listByLogin(String params) {
        String username = JsonTools.getHeaderParams(params, "username"); //
//        String level = JsonTools.getJsonParam(params, "level");
        Sort sort = SimpleSortBuilder.generateSort("orderNo");
        Integer townId = 0;
        try { townId = Integer.parseInt(JsonTools.getJsonParam(params, "townId"));} catch (Exception e) { townId = 0; }
        Town curTown = null;
        curTown = townId>1?townDao.findOne(townId):null;
        List<Town> townList = townDao.findByUsername(username, sort);
        Town town = townList.get(0);
//        System.out.println("=="+town.getName()+"==="+town.getId()+"==="+(town.getId()!=1));
        List<Town> children = new ArrayList<>();
        if(town!=null && town.getId()!=1) {
            children = townDao.findByPid(town.getId(), sort);
        } else if(town!=null && town.getId()==1) {
            children = townDao.findParent(sort);
        }

        if(townId>1 && townList.contains(curTown) || children.contains(curTown)) { //如果是1则为彝良县本身，所以这里不能包含在内
            town = curTown;
            children = townDao.findByPid(townId, sort);
        }

//        System.out.println("username::"+username);
        return JsonResult.success("获取成功").set("townList", townList).set("picList", buildPic(townList)).set("town", town).set("children", children);
    }

    public List<String> buildPic(List<Town> townList) {
        List<String> result = new ArrayList<>();
        for(Town t : townList) {
            String picUrl = t.getPicUrl();
            if(picUrl!=null && !"".equals(picUrl)) {result.add(picUrl);}
        }
        return result;
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

    @Function("添加修改乡镇")
    public JsonResult addOrUpdate(String params) {
        Town obj = JSONObject.toJavaObject(JSON.parseObject(params), Town.class);
        if(obj.getId()!=null && obj.getId()>0) { //修改
            Town t = townDao.getOne(obj.getId());
            MyBeanUtils.copyProperties(obj, t, "id", "pid", "pname");
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
