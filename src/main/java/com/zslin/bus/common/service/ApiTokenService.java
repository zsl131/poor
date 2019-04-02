package com.zslin.bus.common.service;

import com.zslin.bus.common.annotations.ApiCodeClass;
import com.zslin.bus.common.annotations.ApiCodeMethod;
import com.zslin.bus.common.idao.IApiTokenDao;
import com.zslin.bus.common.iservice.IApiTokenService;
import com.zslin.bus.common.model.ApiToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2018/7/5.
 */
@Service
@ApiCodeClass
public class ApiTokenService implements IApiTokenService {

    @Autowired
    private IApiTokenDao apiTokenDao;

    @Override
    @ApiCodeMethod
    public List<ApiToken> findAll() {
        return apiTokenDao.findAll();
    }

    @Override
    @ApiCodeMethod
    public void save(ApiToken apiToken) {
        apiTokenDao.save(apiToken);
    }

    @Override
    public ApiToken findByToken(String token) {
        return apiTokenDao.findByToken(token);
    }
}
