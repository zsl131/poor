package com.zslin.bus.common.iservice;

import com.zslin.bus.common.model.ApiCode;
import com.zslin.bus.tools.JsonResult;

/**
 * Created by zsl on 2018/7/5.
 */
public interface IApiCodeSerivce {

    JsonResult findAll();

    void save(ApiCode apiCode);

    ApiCode findByCode(String code);

    JsonResult init();
}
