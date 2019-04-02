package com.zslin.bus.common.idao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.common.model.ApiCode;

/**
 * Created by zsl on 2018/7/5.
 */
public interface IApiCodeDao extends BaseRepository<ApiCode, Integer> {

    ApiCode findByCode(String code);
}
