package com.zslin.basic.tools;

import java.util.Base64;

/**
 * Created by zsl on 2018/7/18.
 */
public class Base64Utils {

    /*public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }*/

    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            Base64.Decoder decoder = Base64.getDecoder();
            try {
                b = decoder.decode(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
