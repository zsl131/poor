package com.zslin.bus.tools;

import com.zslin.bus.model.Dictionary;
import com.zslin.bus.model.Personal;

import java.util.List;

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

    public static String buildZzxmmc(List<Dictionary> dataList, String zzxm) {
        StringBuffer sb = new StringBuffer();
        if(zzxm!=null && !"".equals(zzxm)) {
            zzxm.replaceAll("，", ",");
            String [] array = zzxm.split(",");
            for(String a : array) {
                String code = a.trim();
                if(code!=null && !"".equals(code)) {
                    for(Dictionary d : dataList) {
                        if(d.getCode().equals(code)) {
                            sb.append(d.getName()).append(",");
                        }
                    }
                }
            }
        }
        return sb.toString();
    }
}
