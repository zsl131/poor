package com.zslin.bus.common.idao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.common.model.ApiTokenCode;

/**
 * Created by zsl on 2018/7/5.
 */
public interface IApiTokenCodeDao extends BaseRepository<ApiTokenCode, Integer> {

    ApiTokenCode findByTokenAndCode(String token, String code);
}
