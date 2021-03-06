package com.zslin.bus.common.controller;

import com.zslin.basic.tools.ConfigTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.bus.dao.*;
import com.zslin.bus.model.*;
import com.zslin.bus.tools.ExcelBasicTools;
import com.zslin.bus.tools.PersonalXhTools;
import com.zslin.bus.tools.PictureTools;
import com.zslin.bus.tools.TownTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by zsl on 2019/4/28.
 */
@RestController
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    private PersonalXhTools personalXhTools;

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IDictionaryDao dictionaryDao;

    @Autowired
    private IFamilyDao familyDao;

    @Autowired
    private ConfigTools configTools;

    @RequestMapping(value = "rebuildBqdd")
    public String rebuildBqdd() {
        List<Family> list = familyDao.listByHql("FROM Family f WHERE f.bqdd IS NOT NULL");
        for(Family f : list) {
            String bqdd = f.getBqdd();
            Integer ddid = TownTools.buildBqddid(bqdd);
            personalDao.updateBqddid(ddid, f.getSfzh());
            f.setBqddid(ddid);
            familyDao.save(f);
        }
        return "操作完成";
    }

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, String p) {
        return "连接正常，参数："+p;
    }

    @RequestMapping(value = "processYbj")
    public String processYbj() {
//            String excelFile = "D:/temp/ybj-just.xls";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    File excelFile = ResourceUtils.getFile("classpath:data/ybj-just.xls");
                    File excelFile = new File(configTools.getUploadPath("/publicFile") + File.separator + "ybj-just.xls");
                    FileInputStream fis = new FileInputStream(excelFile);
                    List<Personal> list = ExcelBasicTools.buildByExcel(fis, 4, 0, "buildPersonalYbj0423", true);
                    System.out.println("===personal count :"+ list.size());
                    Integer errorCount = 0;
                    StringBuffer sb = new StringBuffer();
                    String sfzh;
                    for(Personal p : list) {
                        sfzh = p.getSfzh();
                        Integer count = personalDao.updateYbj(p.getSfyb(), p.getCbxz(), p.getCbdw(), p.getSfhb(), sfzh);
                        if(count==null || count<=0) {
                            errorCount ++;
                            System.out.println("error========="+sfzh);
                            sb.append(sfzh).append(":未找到\n");
                        }
                    }
                    System.out.println("totalCount::"+list.size()+"========errorCount::"+errorCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return "正在处理。。";
    }

    @RequestMapping(value = "processJyj")
    public String processJyj(Integer sheet) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer start = sheet==null?0:sheet;
//                    String excelFile = "D:/temp/jyj-just.xls";
//                    File excelFile = ResourceUtils.getFile("classpath:data/jyj-just.xls");
                    File excelFile = new File(configTools.getUploadPath("/publicFile") + File.separator + "jyj-just.xls");
                    FileInputStream fis = new FileInputStream(excelFile);
                    List<Personal> list = ExcelBasicTools.buildByExcel(fis, 4, start, "buildPersonalJyj0423", true);
                    System.out.println("===personal count :"+ list.size());
                    Integer errorCount = 0;
                    List<Dictionary> dataList = dictionaryDao.findByPcode("DICT_SUPPORT");
                    StringBuffer sb = new StringBuffer();
                    String sfzh, zzxmmc;
                    for(Personal p : list) {
                        sfzh = p.getSfzh();
                        zzxmmc = buildZzxmmc(dataList, p.getZzxm());
                        Integer count = personalDao.updateJxqk(p.getSfzx(), p.getJyjd(), p.getJdxx(), p.getJdnj(), p.getSfxszz(), p.getZzje(), p.getZzxm(), zzxmmc, sfzh);
                        if(count==null || count<=0) {
                            errorCount ++;
                            System.out.println("error========="+sfzh);
                            sb.append(sfzh).append(":未找到\n");
                        }
                    }
                    System.out.println("totalCount::"+list.size()+"========errorCount::"+errorCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return "正在处理。。。";
    }

    private String buildZzxmmc(List<Dictionary> dataList, String zzxm) {
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

    /** 处理家庭人数 */
    @RequestMapping(value = "processCount")
    public String processCount() {
        List<Family> familyList = familyDao.findAll();
        for(Family f : familyList) {
            String sfzh = f.getSfzh();
            Integer ldlrs = personalDao.findLdlCountByHz(sfzh);
            Integer jyrs = personalDao.findJyCountByHz(sfzh);
            familyDao.updateCount(ldlrs, jyrs, sfzh);
        }
        return "处理完成";
    }

    @RequestMapping(value = "processXh")
    public String processXh() {
        Long start = System.currentTimeMillis();
        personalXhTools.resetXh();
        Long end = System.currentTimeMillis();
        Long use = (end - start) /1000;
        return "处理完成，用时 ： "+use + " 秒。";
    }

    @RequestMapping(value = "processPicture")
    public String processPicture(String from) {
        from = from==null?"1":"0"; //1-身份证；0-姓名
        File dir = new File(configTools.getUploadPath("/publicFile/temp_zip"));
        if(!dir.exists()) {dir.mkdirs();}
        /*new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();*/
        System.out.println("========file path ::: "+dir.getAbsolutePath());
        processPic(dir, from);

        return "正在处理照片。。。";
    }

    @Autowired
    private PictureTools pictureTools;

    @Autowired
    private ITownDao townDao;

    @Autowired
    private IPictureUploadDao pictureUploadDao;

    private void processPic(File f, String from) {
        System.out.println("20190811====fileName::"+f.getName());
        if(f.isFile()) {
            String name = f.getName().toLowerCase().replace(".zip", "");
            Town t = null ;
            try {
                t = townDao.findCunByNameLike(name.length()>2?name.substring(0, 2):name);
            } catch (Exception e) {
                t = townDao.findCunByNameLike(name);
            }
            if(!"1".equals(from) && t==null) {
                addPictureUpload(name);
            } else {
                Integer townId = t==null?0:t.getId();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pictureTools.readFile("1".equals(from)?"sfzh":"xm", townId, f);
                    }
                }).start();
            }
        } else if(f.isDirectory()) {
            File [] files = f.listFiles();
            if(files!=null) {
                for (File file : files) {
                    processPic(file, from);
                }
            }
            if((files==null || files.length<=0) && !f.getName().contains("temp_zip")) {f.delete();}
        }
    }

    private void addPictureUpload(String name) {
        PictureUpload pu = new PictureUpload();
        pu.setCreateTime(NormalTools.curDatetime());
        pu.setCreateLong(System.currentTimeMillis());
        pu.setCreateDate(NormalTools.curDate());
        pu.setXzmc(name+"-未找到");
        pictureUploadDao.save(pu);
    }
}
