package com.zslin.basic.service;

import com.zslin.basic.dao.IAppConfigDao;
import com.zslin.basic.dto.WebBaseDto;
import com.zslin.basic.model.AppConfig;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zsl on 2018/7/24.
 */
@Service
public class WebInterceptorService {

    @Autowired
    private IAppConfigDao appConfigDao;


    public JsonResult loadWebBase(String params) {
        AppConfig ac = appConfigDao.loadOne();
        WebBaseDto wbd = new WebBaseDto();
        wbd.setAc(ac);
        return JsonResult.getInstance().set("datas", wbd);
    }
}
