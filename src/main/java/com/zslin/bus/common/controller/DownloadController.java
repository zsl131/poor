package com.zslin.bus.common.controller;

import com.zslin.basic.tools.ConfigTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.dao.IUserTownDao;
import com.zslin.bus.dto.PlantDto;
import com.zslin.bus.model.Family;
import com.zslin.bus.model.Personal;
import com.zslin.bus.poi.util.ExcelUtil;
import com.zslin.bus.threadlocal.SystemThreadLocalHolder;
import com.zslin.bus.tools.DownloadPlantTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zsl on 2018/9/12.
 */
@RestController
@RequestMapping(value = "api/download")
public class DownloadController {

    @Autowired
    private ConfigTools configTools;

    @Autowired
    private IUserTownDao userTownDao;

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private DownloadPlantTools downloadPlantTools;

    @Autowired
    private IFamilyDao familyDao;

    /** 导出种植品种 */
    @GetMapping(value = "downloadPlant")
    public void downloadPlant(HttpServletResponse res, String username) {
        SystemThreadLocalHolder.remove();
        List<Integer> townIds = userTownDao.findTownId(username);

        List<Family> familyList ;
        if("root".equals(username)) {
            familyList = familyDao.findAll();
        } else {
            familyList = familyDao.listOwn(townIds);
        }

        List<PlantDto> plantDtoList = downloadPlantTools.buildDatas(familyList);

        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + NormalTools.curDate("yyyyMMdd") + ".xls");
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            Map<String, String> map = new HashMap<>();
//            map.put("title", "彝良县易地扶贫搬迁对象花名册-"+NormalTools.curDate("yyyyMMdd"));
            ExcelUtil.getInstance().exportObj2ExcelByTemplate(map,
                    "/excel/plant.xls",
                    os,
                    plantDtoList,
                    PlantDto.class, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /** 导出人员 */
    @GetMapping(value = "downloadPersonal")
    public void downloadPersonal(HttpServletResponse res, String username) {
        SystemThreadLocalHolder.remove();
        List<Integer> townIds = userTownDao.findTownId(username);

        List<Personal> personalList ;
        if("root".equals(username)) {
            personalList = personalDao.findAll();
        } else {
            personalList = personalDao.listOwn(townIds);
        }

        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + NormalTools.curDate("yyyyMMdd") + ".xls");
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            Map<String, String> map = new HashMap<>();
            map.put("title", "彝良县易地扶贫搬迁对象花名册-"+NormalTools.curDate("yyyyMMdd"));
            ExcelUtil.getInstance().exportObj2ExcelByTemplate(map,
                    "/excel/personal.xls",
                    os,
                    personalList,
                    Personal.class, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }

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
