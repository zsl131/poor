package com.zslin.bus.common.service;

import com.zslin.bus.common.annotations.ApiCodeClass;
import com.zslin.bus.common.annotations.ApiCodeMethod;
import com.zslin.bus.common.idao.IApiCodeDao;
import com.zslin.bus.common.iservice.IApiCodeSerivce;
import com.zslin.bus.common.model.ApiCode;
import com.zslin.bus.common.tools.BuildApiCodeTools;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2018/7/5.
 */
@Service
@ApiCodeClass
public class ApiCodeService implements IApiCodeSerivce {

    @Autowired
    private IApiCodeDao apiCodeDao;

    @Autowired
    private BuildApiCodeTools buildApiCodeTools;

    @Override
    @ApiCodeMethod
    public JsonResult findAll() {
        List<ApiCode> list = apiCodeDao.findAll();
        return JsonResult.getInstance().set("size", list.size()).set("datas", list);
    }

    @Override
    public void save(ApiCode apiCode) {
        apiCodeDao.save(apiCode);
    }

    @Override
    public ApiCode findByCode(String code) {
        return apiCodeDao.findByCode(code);
    }

    @Override
    public JsonResult init() {
        buildApiCodeTools.buildApiCode();
        return JsonResult.success("初始化完成");
    }
}
