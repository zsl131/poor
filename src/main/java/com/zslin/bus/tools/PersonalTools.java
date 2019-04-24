package com.zslin.bus.tools;

import com.zslin.bus.model.Personal;

/**
 * Created by zsl on 2019/4/24.
 */
public class PersonalTools {

    /** 生成就业类型 */
    public static String buildJyLx(Personal p) {
        String res = "未配置";
        if(!isNull(p.getQymc())) { res = "外出务工";}
        else if(!isNull(p.getCyxm())) {res = "自主创业";}
        else if(!isNull(p.getWgqx()) || !isNull(p.getGyxgw()) || !isNull(p.getZzcy()) || !isNull(p.getWfwcyy())) {res = "未就业";}
        return res;
    }

    private static boolean isNull(String val) {
        return (val==null || "".equals(val));
    }
}
