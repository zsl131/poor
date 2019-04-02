package com.zslin.basic.service;

import com.zslin.basic.dao.IAppConfigDao;
import com.zslin.basic.dao.IUserDao;
import com.zslin.basic.iservice.IAppConfigService;
import com.zslin.basic.model.AppConfig;
import com.zslin.basic.model.User;
import com.zslin.basic.tools.NormalTools;
import com.zslin.basic.tools.SecurityUtil;
import com.zslin.bus.common.tools.InitSystemTools;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * Created by zsl on 2018/7/8.
 */
@Service
public class AppConfigService implements IAppConfigService {

    @Autowired
    private IAppConfigDao appConfigDao;

    @Autowired
    private InitSystemTools initSystemTools;

    @Autowired
    private IUserDao userDao;

    public JsonResult loadOne(String params) {
        AppConfig ac = appConfigDao.loadOne();
        return JsonResult.getInstance().set("size", ac==null?0:1).set("datas", ac);
    }

    public JsonResult initSystem(String params) {
        AppConfig ac = appConfigDao.loadOne();
        if(ac!=null && "1".equals(ac.getInitFlag())) {
            return JsonResult.getInstance().fail("系统已经初始化，不可重复操作");
        }
        try {
            ac = new AppConfig();
            ac.setAdminEmail(JsonTools.getJsonParam(params, "email"));
            ac.setAppName(JsonTools.getJsonParam(params, "appName"));
            ac.setAppVersion("1.0");
            ac.setCreateDate(NormalTools.curDatetime());
            ac.setInitFlag("1");
            appConfigDao.save(ac);

            User user = new User();
            user.setCreateDate(NormalTools.curDatetime());
            user.setNickname(JsonTools.getJsonParam(params, "nickname"));
            String username = JsonTools.getJsonParam(params, "username");
            String password = JsonTools.getJsonParam(params, "password");
            user.setPassword(SecurityUtil.md5(username, password));
            user.setStatus(1);
            user.setIsAdmin("1");
            user.setUsername(username);
            userDao.save(user);

            initSystemTools.initSystem(user.getId());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return JsonResult.getInstance().fail(e.getMessage());
        }

        return JsonResult.getInstance().ok("系统初始化完成");
    }
}
