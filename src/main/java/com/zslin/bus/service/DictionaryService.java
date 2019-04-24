package com.zslin.bus.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.MyBeanUtils;
import com.zslin.bus.common.annotations.Function;
import com.zslin.bus.common.dto.QueryListDto;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.common.tools.QueryTools;
import com.zslin.bus.dao.IDictionaryDao;
import com.zslin.bus.dto.DictionaryDto;
import com.zslin.bus.model.Dictionary;
import com.zslin.bus.tools.DictionaryTools;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2019/4/8.
 */
@Service
@AdminAuth(psn = "业务管理", name = "数据字典管理", orderNum = 1, type = "1", url = "/admin/dictionary")
public class DictionaryService {

    @Autowired
    private IDictionaryDao dictionaryDao;

    @Autowired
    private DictionaryTools dictionaryTools;

    public JsonResult list(String params) {
        /*QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Dictionary> res = dictionaryDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));
        return JsonResult.success().set("size", (int)res.getTotalElements()).set("data", res.getContent());*/
        JsonResult result = JsonResult.getInstance();
        Integer pid = 0;
        try { pid = Integer.parseInt(JsonTools.getJsonParam(params, "pid"));} catch (Exception e) {pid=0;}
        List<DictionaryDto> treeDto = dictionaryTools.buildDictionary();
        List<Dictionary> list ;
        if(pid==0) {
            list = dictionaryDao.findParent();
        } else {
            list = dictionaryDao.findByPid(pid);
            result.set("dic", dictionaryDao.findOne(pid));
        }
        return result.set("size", list.size()).set("data", list).set("treeDto", treeDto);
    }

    public JsonResult listNoPage(String params) {
        List<Dictionary> list = dictionaryDao.findAll();
        return JsonResult.success().set("list", list);
    }

    public JsonResult loadOne(String params) {
        Integer id = JsonTools.getId(params);
        Dictionary obj = dictionaryDao.findOne(id);
        return JsonResult.succ(obj);
    }

    @Function("添加修改数据字典")
    public JsonResult addOrUpdate(String params) {
        Dictionary obj = JSONObject.toJavaObject(JSON.parseObject(params), Dictionary.class);
        String code = obj.getCode();
        Integer pid = obj.getPid();
        if(obj.getId()!=null && obj.getId()>0) { //修改
            Dictionary t = dictionaryDao.getOne(obj.getId());
            MyBeanUtils.copyProperties(obj, t, "pid", "pname", "pcode", "code");
            dictionaryDao.save(t);
        } else {
            if(obj.getPid()==null || obj.getPid()<=0) {
                if(dictionaryDao.findParentByCode(code)!=null) {
                    return JsonResult.success("["+code+"]已存在").set("flag", "0");
                }
            } else {
                Dictionary d = dictionaryDao.findOne(obj.getPid());
                obj.setPname(d.getName());
                obj.setPcode(d.getCode());
                if(dictionaryDao.findByPidAndCode(pid, code)!=null) {
                    return JsonResult.success("["+code+"]已存在").set("flag", "0");
                }
            }
            dictionaryDao.save(obj);
        }
        return JsonResult.success("保存成功").set("flag", "1");
    }

    public JsonResult delete(String params) {
        Integer id = JsonTools.getId(params);
        dictionaryDao.delete(id);
        return JsonResult.success("删除成功");
    }

    /** 获取数据字典 */
    public JsonResult listByPcode(String params) {
        String pcode = JsonTools.getJsonParam(params, "pcode");
        List<Dictionary> list = dictionaryDao.findByPcode(pcode);
        return JsonResult.success("获取成功").set("list", list);
    }
}
