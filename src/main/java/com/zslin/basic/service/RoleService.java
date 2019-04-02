package com.zslin.basic.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.dao.IRoleDao;
import com.zslin.basic.dao.IRoleMenuDao;
import com.zslin.basic.model.Role;
import com.zslin.basic.model.RoleMenu;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.PinyinToolkit;
import com.zslin.bus.common.dto.QueryListDto;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.common.tools.QueryTools;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2018/7/13.
 */
@Service
@AdminAuth(name = "角色管理", psn = "权限管理", orderNum = 2, type = "1", url = "/admin/role")
public class RoleService {

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IRoleMenuDao roleMenuDao;

    public JsonResult authMenu(String params) {
        try {
            Integer mid = Integer.parseInt(JsonTools.getJsonParam(params, "mid"));
            Integer rid = Integer.parseInt(JsonTools.getJsonParam(params, "rid"));
            RoleMenu rm = roleMenuDao.queryByRidAndMid(rid, mid);
            String message ;
            if(rm==null) {
                rm = new RoleMenu();
                rm.setMid(mid);
                rm.setRid(rid);
                roleMenuDao.save(rm);
                message = "授权成功";
            } else {
                roleMenuDao.delete(rm);
                message = "取消授权成功";
            }
            return JsonResult.getInstance(message);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.getInstance().fail(e.getMessage());
        }
    }

    public JsonResult listRoleMenuIds(String params) {
        try {
            Integer rid = Integer.parseInt(JsonTools.getJsonParam(params, "rid"));
            List<Integer> mids = roleMenuDao.queryMenuIds(rid);
            return JsonResult.getInstance().set("size", mids.size()).set("datas", mids);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.getInstance().fail(e.getMessage());
        }
    }

    @AdminAuth(name = "角色列表", orderNum = 1)
    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Role> res = roleDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", (int) res.getTotalElements()).set("datas", res.getContent());
    }

    @AdminAuth(name = "添加角色", orderNum = 2)
    public JsonResult add(String params) {
        try {
            Role role = JSONObject.toJavaObject(JSON.parseObject(params), Role.class);
            String sn = PinyinToolkit.cn2Spell(role.getName(),"").toUpperCase();
            if(roleDao.findBySn(sn)!=null) {
                return JsonResult.getInstance().fail("【"+sn+"】已经存在");
            }
            role.setSn(sn);
            roleDao.save(role);
            return JsonResult.getInstance().set("datas", role);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.getInstance().fail(e.getMessage());
        }
    }

    @AdminAuth(name = "修改角色", orderNum = 3)
    public JsonResult update(String params) {
        try {
            Role r = JSONObject.toJavaObject(JSON.parseObject(params), Role.class);
            r.setSn(PinyinToolkit.cn2Spell(r.getName(),"").toUpperCase());
            roleDao.save(r);
            return JsonResult.getInstance().set("datas", r);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.getInstance().fail(e.getMessage());
        }
    }

    public JsonResult loadOne(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            Role role = roleDao.findOne(id);
            return JsonResult.getInstance().set("datas", role);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    @AdminAuth(name = "删除角色", orderNum = 4)
    public JsonResult delete(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            Role r = roleDao.findOne(id);
            if("SYSTEM_ADMIN".equalsIgnoreCase(r.getSn())) {
                return JsonResult.error("系统管理员角色不可以删除");
            } else {
                roleDao.delete(id);
                return JsonResult.success("删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }
}
