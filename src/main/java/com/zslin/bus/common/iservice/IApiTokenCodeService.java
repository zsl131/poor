package com.zslin.bus.common.iservice;

import com.zslin.bus.common.model.ApiTokenCode;

/**
 * Created by zsl on 2018/7/5.
 */
public interface IApiTokenCodeService {

    ApiTokenCode findByTokenAndCode(String token, String code);
}
