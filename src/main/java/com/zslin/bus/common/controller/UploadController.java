package com.zslin.bus.common.controller;

import com.zslin.basic.tools.ConfigTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.bus.tools.ImportTools;
import com.zslin.bus.tools.PictureTools;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zsl on 2018/7/12.
 */
@RestController
@RequestMapping(value = "api/upload")
public class UploadController {

    @Autowired
    private ConfigTools configTools;

    @Autowired
    private PictureTools pictureTools;

    @Autowired
    private ImportTools importTools;

    private static final String PATH_PRE = "/wangeditor/images";
    private static final String UPLOAD_PATH_PRE = "/publicFile/upload";

    @RequestMapping(value="image")
    public UploadResult add(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
//        String authToken = request.getHeader("auth-token");
//        System.out.println("=========="+files.length);
//        System.out.println(authToken);
        UploadResult result = new UploadResult(0);
        if(files!=null) {
            for(MultipartFile file : files) {
                BufferedOutputStream bw = null;
                try {
                    String fileName = file.getOriginalFilename();
//                    System.out.println("========fileName::"+fileName);
                    if (fileName != null && !"".equalsIgnoreCase(fileName.trim()) && NormalTools.isImageFile(fileName)) {
                        File outFile = new File(configTools.getUploadPath(PATH_PRE) + File.separator + NormalTools.curDate("yyyyMMdd") + File.separator + UUID.randomUUID().toString() + NormalTools.getFileType(fileName));
                        String uploadPath = outFile.getAbsolutePath().replace(configTools.getUploadPath(), File.separator);
                        FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                        result.add(uploadPath);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    /**
     * 保存文件，直接以multipartFile形式
     * @param multipartFile
     * @param extra 扩展参数
     * @return 返回文件名
     * @throws IOException
     */
    @RequestMapping(value = "uploadFile")
    public String uploadFile(@RequestParam("file")MultipartFile[] multipartFile,String extra) throws IOException {
        String result = "error";
        if(multipartFile!=null && multipartFile.length>=1) {

            MultipartFile file = multipartFile[0];
            String fileName = file.getOriginalFilename();
            if (fileName != null && !"".equalsIgnoreCase(fileName.trim()) && NormalTools.isImageFile(fileName)) {
                File outFile = new File(configTools.getUploadPath(UPLOAD_PATH_PRE) + File.separator + NormalTools.curDate("yyyyMMdd") + File.separator + UUID.randomUUID().toString() + NormalTools.getFileType(fileName));
                result = outFile.getAbsolutePath().replace(configTools.getUploadPath(), File.separator);
                FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                Integer w = 1000, h = 1000;
                try { w = Integer.parseInt(buildExtra(extra, "w")); } catch (Exception e) { }
                try { h = Integer.parseInt(buildExtra(extra, "h")); } catch (Exception e) { }
                Thumbnails.of(outFile).size(w, h).toFile(outFile);
//                result.add(uploadPath);
            }
        }
        return result;
    }

    //w:4000_h:2400
    private String buildExtra(String extra, String field) {
        String result = null;
        try {
            String [] array = extra.split("_");
            for(String a : array) {
                String [] tmp = a.split(":");
                if(field.equals(tmp[0])) {result = tmp[1];}
            }
        } catch (Exception e) {
        }
        return result;
    }

    //通过Excel上传人员数据
    @RequestMapping(value = "uploadPersonalExcel")
    public String uploadPersonalExcel(@RequestParam("file")MultipartFile[] multipartFile,String extra) throws  Exception {
        String result = "error";
        if(multipartFile!=null && multipartFile.length>=1) {
            MultipartFile file = multipartFile[0];
            if(file!=null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("----线程启动----");
                        try {
                            importTools.process(file.getInputStream()); //
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
        return result;
    }

    /**
     * 上传文件，直接以multipartFile形式
     * @param multipartFile
     * @param extra 扩展参数
     * @return 返回文件名
     * @throws IOException
     */
    @RequestMapping(value = "uploadZipFile")
    public String uploadZipFile(@RequestParam("file")MultipartFile[] multipartFile,String extra) throws IOException {
//        System.out.println("==========="+extra);
        String result = "error";
        if(multipartFile!=null && multipartFile.length>=1) {
            MultipartFile file = multipartFile[0];
            String fileName = file.getOriginalFilename();
//                    System.out.println("========fileName::"+fileName);
            if (fileName != null && !"".equalsIgnoreCase(fileName.trim()) && NormalTools.isZipFile(fileName)) {
                File outFile = new File(configTools.getUploadPath(UPLOAD_PATH_PRE) + File.separator + "zip" + File.separator + UUID.randomUUID().toString() + NormalTools.getFileType(fileName));
                result = outFile.getAbsolutePath().replace(configTools.getUploadPath(), File.separator);
                FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                String hqfs = buildExtra(extra, "hqfs"); Integer xzid = Integer.parseInt(buildExtra(extra, "xzid"));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pictureTools.readFile(hqfs, xzid, outFile);
                    }
                }).start();
            }
        }
        return result;
    }
}

class UploadResult {
    private Integer errno = 0;
    private List<String> data;

    public UploadResult(){
        data = new ArrayList<>();
    }

    public UploadResult(Integer errno) {
        this();
        this.errno = errno;
    }

    public UploadResult(Integer errno, String imagePath) {
        this(errno);
        this.errno = errno;
        this.data.add(imagePath);
    }

    public UploadResult add(String imagePath) {
        this.data.add(imagePath);
        return this;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}