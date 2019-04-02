package com.zslin.bus.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.basic.repository.SpecificationOperator;
import com.zslin.bus.common.tools.JsonTools;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by zsl on 2018/8/10.
 */
public class JsonParamTools {

    private static final String HEADER_PARAM_NAME = "headerParams";

    /*private static List<String> ignoreNames = new ArrayList<>();

    static {
        ignoreNames.add("accept-language");
        ignoreNames.add("accept-encoding");
        ignoreNames.add("referer");
        ignoreNames.add("accept");
        ignoreNames.add("auth-token");
        ignoreNames.add("user-agent");
        ignoreNames.add("api-code");
        ignoreNames.add("connection");
        ignoreNames.add("host");
    }*/

    public static String rebuildParams(String params, HttpServletRequest request) throws Exception {

        List<String> ignoreNames = new ArrayList<>();

        ignoreNames.add("accept-language");
        ignoreNames.add("accept-encoding");
        ignoreNames.add("referer");
        ignoreNames.add("accept");
        ignoreNames.add("auth-token");
        ignoreNames.add("user-agent");
        ignoreNames.add("api-code");
        ignoreNames.add("connection");
        ignoreNames.add("host");

        Enumeration<String> names = request.getHeaderNames();
        Map<String, Object> headerMap = new HashMap<>();
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            if(!ignoreNames.contains(name)) {
                headerMap.put(name, request.getHeader(name));
            }
        }

        JSONObject jsonObj = JSON.parseObject(params);
        if(!headerMap.isEmpty()) {
            jsonObj.put(HEADER_PARAM_NAME, headerMap);
        }
        String result = jsonObj.toJSONString();
        return result;
    }

    public static String getHeaderParams(String params) {
        return JsonTools.getJsonParam(params, HEADER_PARAM_NAME);
    }

    public static Map<String, String> getHeaderMap(String params) {
        Map<String, String> result = new HashMap<>();
        try {
            JSONObject jsonObj = JSON.parseObject(getHeaderParams(params));
            Iterator<String> keys = jsonObj.keySet().iterator();
            while(keys.hasNext()) {
                String key = keys.next();
                result.put(key, jsonObj.getString(key));
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static Integer [] getDepIds(String params) {
        try {
            String depIds = getHeaderMap(params).get("depids");
            return getDepIdsByField(depIds);
        } catch (Exception e) {
            return new Integer[]{};
        }
    }

    private static Integer[] getDepIdsByField(String depIds) {
        try {
            String [] array = depIds.split(",");
            Integer [] result = new Integer[array.length];
            int index = 0;
            for(String id : array) {
                result[index++] = Integer.parseInt(id);
            }
            return result;
        } catch (Exception e) {
            return new Integer[]{};
        }
    }

    /** 判断是否为管理员用户 */
    public static boolean isAdminUser(String params) {
        Map<String, String> map = getHeaderMap(params);
        Integer [] depIds = getDepIdsByField(map.get("depids"));
        if(depIds==null || depIds.length<=0) return true; //如果没有管理部门则默认为管理员，主要用于在未精确授权部门或微信端调用
        String isAdminUser = map.get("isAdminUser".toLowerCase());
        return "1".equalsIgnoreCase(isAdminUser);
    }

    /** 判断部门Id是否包含 */
    public static boolean containDepId(String params, Integer depId) {
        Integer [] depIds = getDepIds(params);
        boolean result = false;
        for(Integer id : depIds) {
            if(id == depId) {result = true; break;}
        }
        return result;
    }

    public static SpecificationOperator buildAuthDepSpe(String params) {
        Map<String, String> map = getHeaderMap(params);
        SpecificationOperator result = null;
        if(!isAdminUser(params)) {
            Integer [] depIds = getDepIdsByField(map.get("depids"));
            result = new SpecificationOperator("depId", "in", depIds);
        }
        return result;
    }
}
