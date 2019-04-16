package com.zslin.basic.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.dao.IMenuDao;
import com.zslin.basic.dao.IRoleMenuDao;
import com.zslin.basic.dto.MenuTreeDto;
import com.zslin.basic.dto.TreeRootDto;
import com.zslin.basic.model.Menu;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.normal.MenuTools;
import com.zslin.bus.common.annotations.Function;
import com.zslin.bus.common.dto.QueryListDto;
import com.zslin.bus.common.tools.AuthRoleMenuTools;
import com.zslin.bus.common.tools.BuildAdminMenuTools;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.common.tools.QueryTools;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2018/7/13.
 */
@Service
@AdminAuth(name = "菜单管理", psn = "权限管理", orderNum = 3, type = "1", url = "/admin/menu")
public class MenuService {

    @Autowired
    private IMenuDao menuDao;

    @Autowired
    private MenuTools menuTools;

    @Autowired
    private IRoleMenuDao roleMenuDao;

    @Autowired
    private BuildAdminMenuTools buildAdminMenuTools;

    @Autowired
    private AuthRoleMenuTools authRoleMenuTools;

    @Function("初始化系统菜单")
    @AdminAuth(name = "初始化菜单", orderNum = 6)
    public JsonResult init(String params) {
        buildAdminMenuTools.buildAdminMenus(); //重构菜单
        authRoleMenuTools.authAdmin(); //为系统管理员授权菜单
        return JsonResult.getInstance("初始化菜单成功");
    }

    @AdminAuth(name = "菜单列表", orderNum = 1)
    public JsonResult listRoot(String params) {
        Integer pid = 0;
        try { pid = Integer.parseInt(JsonTools.getJsonParam(params, "pid"));} catch (Exception e) {pid=0;}
        List<MenuTreeDto> list = menuTools.buildMenuTree();
        Sort sort = SimpleSortBuilder.generateSort("orderNum_a");
        List<Menu> menuList ;
        if(pid==0) {
            menuList = menuDao.findRootMenu(sort);
        } else {
            menuList = menuDao.findByParent(pid, sort);
        }
        TreeRootDto trd = new TreeRootDto(list, menuList);
        return JsonResult.getInstance().set("size", menuList.size()).set("datas", trd);
    }

    /**
     *
     * @param params {pid: 1}
     * @return
     */
    public JsonResult listChildren(String params) {
        Integer pid = 0;
        try { pid = Integer.parseInt(JsonTools.getJsonParam(params, "pid"));} catch (Exception e) {pid=0;}
        Sort sort = SimpleSortBuilder.generateSort("orderNum_a");
        List<Menu> menuList ;
        if(pid==0) {
            menuList = menuDao.findRootMenu(sort);
        } else {
            menuList = menuDao.findByParent(pid, sort);
        }

        return JsonResult.getInstance().set("size", menuList.size()).set("datas", menuList);
    }

    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Menu> res = menuDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", (int) res.getTotalElements()).set("datas", res.getContent());
    }

    @AdminAuth(name = "添加菜单", orderNum = 2)
    @Function("添加菜单")
    public JsonResult add(String params) {
        try {
            Menu menu = JSONObject.toJavaObject(JSON.parseObject(params), Menu.class);
            menuDao.save(menu);
            return JsonResult.getInstance().set("datas", menu);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.getInstance().fail(e.getMessage());
        }
    }

    @AdminAuth(name = "修改菜单", orderNum = 3)
    @Function("修改菜单")
    public JsonResult update(String params) {
        try {
            Menu menu = JSONObject.toJavaObject(JSON.parseObject(params), Menu.class);
            Menu m = menuDao.findOne(menu.getId());
            m.setOrderNum(menu.getOrderNum());
            m.setHref(menu.getHref());
            m.setName(menu.getName());
            m.setIcon(menu.getIcon());
            menuDao.save(m);
            return JsonResult.getInstance().set("datas", m);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.getInstance().fail(e.getMessage());
        }
    }

    @AdminAuth(name = "删除菜单", orderNum = 4)
    @Function("删除菜单")
    public JsonResult delete(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            menuDao.delete(id);
            roleMenuDao.deleteByMenuId(id);
            return JsonResult.getInstance("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.getInstance().fail(e.getMessage());
        }
    }
}
