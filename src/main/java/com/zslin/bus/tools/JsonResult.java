package com.zslin.bus.tools;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zsl on 2018/7/3.
 */
@JsonInclude(value= JsonInclude.Include.NON_NULL)
public class JsonResult {

    public static final String SUCCESS_REASON = "请求成功";
    public static final String SUCCESS_CODE = "0";
    public static final String BUSSINESS_ERR_CODE = "10001"; //业务错误代码

    private String reason = SUCCESS_REASON;

    /**
     * 错误代码
     * 0-成功返回
     */
    private String errCode = SUCCESS_CODE;

    private Map<String, Object> result;

    @Override
    public String toString() {
        return "JsonResult{" +
                "reason='" + reason + '\'' +
                ", errCode='" + errCode + '\'' +
                ", result=" + result +
                '}';
    }

    public static JsonResult getInstance() {
        return new JsonResult();
    }

    public static JsonResult getInstance(String sucMsg) {
        return new JsonResult(sucMsg);
    }

    private JsonResult(String sucMsg) {
        this.reason = SUCCESS_REASON;
        this.errCode = SUCCESS_CODE;
        this.result = new HashMap<>();
        this.result.put("message", sucMsg);
    }

    public static JsonResult error(String errMsg) {
        JsonResult that = getInstance().fail(errMsg);
        return that;
    }

    public static JsonResult success() {
        return success(SUCCESS_REASON);
    }

    public static JsonResult success(String sucMsg) {
        JsonResult that = getInstance(sucMsg);
        return that;
    }

    public static JsonResult succ(Object obj) {
        JsonResult that = getInstance().set("obj", obj);
        return that;
    }

    public JsonResult ok(String result) {
        this.reason = SUCCESS_REASON;
        this.errCode = SUCCESS_CODE;
        this.result.put("message", result);
        return this;
    }

    public JsonResult fail(String errMsg) {
        this.reason = errMsg;
        this.errCode = BUSSINESS_ERR_CODE;
        this.result.put("message", errMsg);
        return this;
    }

    public JsonResult set(String key, Object data) {
        result.put(key, data);
        return this;
    }

    private JsonResult(){
        result = new HashMap<>();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
