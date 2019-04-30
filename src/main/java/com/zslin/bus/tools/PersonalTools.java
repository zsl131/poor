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
        if(!"劳动力".equals(p.getSfsldl())) {return "";} //如果不是劳动力则不做任何处理
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

    /**
     * 生成务工地域
     * @param wgdd 务工地点
     * @return
     */
    public static String buildWgdy(String wgdd) {
        String res = "";
        if(wgdd==null || "".equals(wgdd.trim())) {return res;}
        if(wgdd.contains("彝良县")) {res = "县内";}
        else if((wgdd.contains("云南省") || wgdd.contains("昆明")) && !wgdd.contains("彝良县")) {res = "省内县外";}
        else {res = "省外";}
        return res;
    }

    /**
     * 生成务工省份
     * @param wgdd
     * @return
     */
    public static String buildWgsf(String wgdd) {
        String res = "";
        if(wgdd==null || "".equals(wgdd.trim())) {return res;}
        String [] array = new String[]{"中山", "东莞", "江苏", "上海", "浙江", "福建", "新疆", "云南"};
        for(String sf:array) {
            if(wgdd.contains(sf)) {res = sf; break;}
            else if(wgdd.contains("昆明")) {res = "云南"; break;}
        }
        return res;
    }
}
