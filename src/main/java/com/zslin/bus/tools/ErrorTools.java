package com.zslin.bus.tools;

import java.io.*;

/**
 * Created by zsl on 2019/4/23.
 */
public class ErrorTools {

    public static void error2File(String fileName, String str) {
        error2File(fileName, str, false);
    }

    public static void error2File(String fileName, String str, boolean isAppend) {
        File f = new File("D:/temp/errors/"+fileName);
        StringBuffer sb = new StringBuffer();
        if(isAppend) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                String line ;
                while((line=br.readLine())!=null) {
                    sb.append(line).append("\n");
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sb.append(str).append("\n");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
            bw.write(sb.toString());
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
