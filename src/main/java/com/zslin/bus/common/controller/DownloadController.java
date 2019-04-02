package com.zslin.bus.common.controller;

import com.zslin.basic.tools.ConfigTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by zsl on 2018/9/12.
 */
@RestController
@RequestMapping(value = "api/download")
public class DownloadController {

    @Autowired
    private ConfigTools configTools;

    /**
     * 下载文件
     * @param res response
     * @param from 源文件所在位置，0-文件夹中（默认）;1-classpath下的public中
     * @param filename
     */
    @GetMapping(value = {"", "/", "index"})
    public void download(HttpServletResponse res, String from, String filename) {
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + filename);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            File file ;
            if("1".equals(from)) {
                file = ResourceUtils.getFile("classpath:public/" + filename);
            } else {
                file = new File(configTools.getUploadPath(filename));
            }
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
