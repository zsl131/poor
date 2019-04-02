package com.zslin.bus.common.tools;

import com.zslin.basic.dao.IMenuDao;
import com.zslin.basic.dao.IRoleDao;
import com.zslin.basic.dao.IRoleMenuDao;
import com.zslin.basic.dao.IUserRoleDao;
import com.zslin.basic.model.Role;
import com.zslin.basic.model.RoleMenu;
import com.zslin.basic.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zsl on 2018/7/13.
 */
@Component
public class InitSystemTools {

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private BuildAdminMenuTools buildAdminMenuTools;

    @Autowired
    private IMenuDao menuDao;

    @Autowired
    private IRoleMenuDao roleMenuDao;

    @Autowired
    private IUserRoleDao userRoleDao;

    public void initSystem(Integer userId) {
        //1、初始化系统菜单
        buildAdminMenuTools.buildAdminMenus();
        //2、添加角色
        Role r = initRole();
        //3、为角色分配菜单
        List<Integer> ids = menuDao.findAllIds();
        for(Integer id: ids) {
            RoleMenu rm = new RoleMenu();
            rm.setRid(r.getId());
            rm.setMid(id);
            roleMenuDao.save(rm);
        }
        //4、添加系统用户
        //5、为用户分配角色
        UserRole ur = new UserRole();
        ur.setRid(r.getId());
        ur.setUid(userId);
        userRoleDao.save(ur);
    }

    private Role initRole() {
        Role r = new Role();
        r.setName("系统管理员");
        r.setSn("SYSTEM_ADMIN");
        roleDao.save(r);
        return r;
    }
}
