package com.zslin.bus.common.service;

import com.zslin.bus.common.idao.IApiTokenCodeDao;
import com.zslin.bus.common.iservice.IApiTokenCodeService;
import com.zslin.bus.common.model.ApiTokenCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zsl on 2018/7/5.
 */
@Service
public class ApiTokenCodeSerivce implements IApiTokenCodeService {

    @Autowired
    private IApiTokenCodeDao apiTokenCodeDao;

    @Override
    public ApiTokenCode findByTokenAndCode(String token, String code) {
        return apiTokenCodeDao.findByTokenAndCode(token, code);
    }
}
