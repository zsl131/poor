package com.zslin.basic.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.dao.IRoleDao;
import com.zslin.basic.dao.IUserDao;
import com.zslin.basic.dao.IUserRoleDao;
import com.zslin.basic.dto.AuthRoleDto;
import com.zslin.basic.iservice.IUserService;
import com.zslin.basic.model.Role;
import com.zslin.basic.model.User;
import com.zslin.basic.model.UserRole;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.DateTools;
import com.zslin.basic.tools.SecurityUtil;
import com.zslin.basic.tools.login.LoginDto;
import com.zslin.basic.tools.login.LoginTools;
import com.zslin.bus.common.annotations.ApiCodeClass;
import com.zslin.bus.common.dto.QueryListDto;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.common.tools.QueryTools;
import com.zslin.bus.dao.IUserTownDao;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2018/7/10.
 */
@Service
@ApiCodeClass
@AdminAuth(psn = "权限管理", name = "用户管理", orderNum = 1, type = "1", url = "/admin/users")
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IUserRoleDao userRoleDao;

    @Autowired
    private LoginTools loginTools;

    @Autowired
    private IUserTownDao userTownDao;

    @AdminAuth(name = "用户列表", orderNum = 1)
    public JsonResult listUser(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<User> res = userDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));

        return JsonResult.getInstance().set("size", (int) res.getTotalElements()).set("datas", res.getContent());
    }

    public JsonResult loadOne(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            User user = userDao.findOne(id);
            return JsonResult.succ(user);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    @AdminAuth(name = "修改用户", orderNum = 2)
    public JsonResult saveUser(String params) {
//        System.out.println("params::   "+ params);
        try {
            User u = JSONObject.toJavaObject(JSON.parseObject(params), User.class);
            if(u.getId()==null || u.getId()<=0) { //添加
                User user = userDao.findByUsername(u.getUsername());
                if(user!=null) {
                    return JsonResult.error(u.getUsername()+"已经存在");
                }
                u.setPassword(SecurityUtil.md5(u.getUsername(), u.getPassword()));
                u.setCreateDate(DateTools.currentDay("yyyy-MM-dd HH:mm:ss"));
                userDao.save(u);
            } else {
                User user = userDao.findOne(u.getId());
                if(u.getPassword()!=null && !"".equals(u.getPassword())) {
                    user.setPassword(SecurityUtil.md5(u.getUsername(), u.getPassword()));
                }
                user.setStatus(u.getStatus());
                user.setNickname(u.getNickname());
                userDao.save(user);
            }
            return JsonResult.succ(u);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    @AdminAuth(name = "删除用户", orderNum = 3)
    public JsonResult deleteUser(String params) {
        System.out.println("params:::"+params);
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            User u = userDao.findOne(id);
            if("1".equalsIgnoreCase(u.getIsAdmin())) {
                return JsonResult.error("管理员用户不可以删除");
            } else {
                userDao.delete(id);
                return JsonResult.success("删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    public JsonResult login(String params) {
        try {
            String username = JsonTools.getJsonParam(params, "username");
            String password = JsonTools.getJsonParam(params, "password");
            if(username == null || "".equals(username) || password == null || "".equals(password)) {
                return JsonResult.error("用户名或密码为空");
            }
            User user = userDao.findByUsername(username);
            if(user==null || user.getStatus() != 1) {
                return JsonResult.error("用户不存在或被停用");
            }
            password = SecurityUtil.md5(username, password);
            if(!password.equals(user.getPassword())) {
                return JsonResult.error("密码不正确");
            }

            LoginDto loginDto = loginTools.buildAuthMenus(user.getId());
            loginDto.setUser(user);

            String level = userTownDao.findLevelByUser(username); //用户管辖乡镇的级别
            loginDto.setLevel(level);

            return JsonResult.succ(loginDto);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    public JsonResult matchRole(String params) {
        try {
            Integer userId = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            List<Integer> roleIds = userDao.listUserRoleIds(userId); //已拥有的角色Id
            List<Role> roleList = roleDao.findAll();
            AuthRoleDto ard = new AuthRoleDto(roleIds, roleList);
            return JsonResult.succ(ard);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    @AdminAuth(name = "分配用户角色", orderNum = 5)
    public JsonResult authRole(String params) {
        System.out.println(params);
        try {
            Integer uid = Integer.parseInt(JsonTools.getJsonParam(params, "uid"));
            userRoleDao.deleteByUserId(uid); //先删除所有权限
            String rids = JsonTools.getJsonParam(params, "rids");
            JSONArray array = JSON.parseArray(rids);
            for(Integer i=0;i<array.size();i++) {
                Integer rid = array.getInteger(i);
                UserRole ur = new UserRole();
                ur.setUid(uid);
                ur.setRid(rid);
                userRoleDao.save(ur);
            }
            return JsonResult.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    public JsonResult updatePwd(String params) {
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(params, "id"));
            User user = userDao.findOne(id);
            String username = user.getUsername();
            String oldPwd = JsonTools.getJsonParam(params, "oldPwd");
            String password = JsonTools.getJsonParam(params, "password");
            String nickname = JsonTools.getJsonParam(params, "nickname");
            if(!SecurityUtil.md5(username, oldPwd).equals(user.getPassword())) {
                return JsonResult.error("原始密码输入错误");
            }
            user.setNickname(nickname);
            user.setPassword(SecurityUtil.md5(username, password));
            userDao.save(user);
            return JsonResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }
}
