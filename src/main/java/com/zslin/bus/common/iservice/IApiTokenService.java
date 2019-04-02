package com.zslin.bus.common.iservice;

import com.zslin.bus.common.model.ApiToken;

import java.util.List;

/**
 * Created by zsl on 2018/7/5.
 */
public interface IApiTokenService {

    List<ApiToken> findAll();

    void save(ApiToken apiToken);

    ApiToken findByToken(String token);
}
