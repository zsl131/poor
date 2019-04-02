package com.zslin.basic.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zsl-pc on 2016/9/14.
 */
public class NormalTools {

    public static String getFileType(String fileName) {
        if(fileName!=null && fileName.indexOf(".")>=0) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }

    /**
     * 判断文件是否为图片文件
     * @param fileName
     * @return
     */
    public static Boolean isImageFile(String fileName) {
        String [] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if(fileName==null) {return false;}
        fileName = fileName.toLowerCase();
        for(String type : img_type) {
            if(fileName.endsWith(type)) {return true;}
        }
        return false;
    }

    public static String curDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    public static String curDatetime() {
        return curDate("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 生成两位小数的数字
     * @param d double类型的数字
     * @return
     */
    public static double buildPoint(double d) {
        /*BigDecimal bg = new BigDecimal(d);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        f1 = Math.rint(f1);
        return f1;*/
        return Math.ceil(d);
    }

    public static String curDate() {
        return curDate("yyyy-MM-dd");
    }
}
