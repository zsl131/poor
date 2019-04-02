package com.zslin.bus.common.tools;

import com.zslin.basic.dao.IMenuDao;
import com.zslin.basic.dao.IRoleDao;
import com.zslin.basic.dao.IRoleMenuDao;
import com.zslin.basic.model.Role;
import com.zslin.basic.model.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zsl on 2018/7/23.
 */
@Component
public class AuthRoleMenuTools {

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IRoleMenuDao roleMenuDao;

    @Autowired
    private IMenuDao menuDao;

    public void authAdmin() {
        Role r = roleDao.findBySn("SYSTEM_ADMIN");
        if(r!=null) {
            Integer rid = r.getId();
            List<Integer> alreadyMenuIds = roleMenuDao.queryMenuIds(rid);
            List<Integer> allMenuIds = menuDao.findAllIds();
            for(Integer id : allMenuIds) {
                if(!alreadyMenuIds.contains(id)) {
                    RoleMenu rm = new RoleMenu();
                    rm.setMid(id);
                    rm.setRid(rid);
                    roleMenuDao.save(rm);
                }
            }
        }
    }
}
