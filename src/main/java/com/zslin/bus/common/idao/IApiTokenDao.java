package com.zslin.bus.common.idao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.common.model.ApiToken;

/**
 * Created by zsl on 2018/7/5.
 */
public interface IApiTokenDao extends BaseRepository<ApiToken, Integer> {

    ApiToken findByToken(String token);
}
