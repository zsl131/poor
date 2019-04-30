package com.zslin.bus.tools;

import com.zslin.basic.tools.ConfigTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.bus.dao.*;
import com.zslin.bus.dto.PictureDto;
import com.zslin.bus.model.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 照片处理
 * Created by zsl on 2019/4/26.
 */
@Component
public class PictureTools {

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private ConfigTools configTools;

    @Autowired
    private ITownDao townDao;

    @Autowired
    private IPictureUploadDao pictureUploadDao;

    @Autowired
    private IAssetsDao assetsDao;

    @Autowired
    private IPictureUploadRecordDao pictureUploadRecordDao;

    private static final String UPLOAD_PATH_PRE = "/publicFile/upload";

    private String buildBatchNo() {
        return NormalTools.curDate("yyyyMMddHHmmss");
    }

    /**
     * 上传照片
     * @param hqfs 获取方式
     * @param xzid 乡镇ID
     * @param zipFile ZIP文件
     */
    public void readFile(String hqfs, Integer xzid, File zipFile) {
        String batchNo = buildBatchNo(); //批次号
        Integer amount = 0;
        ZipInputStream zis = null;
        String xzmc = townDao.findOne(xzid).getName();
        boolean isSfzh = ("1".equals(hqfs) || "sfzh".equals(hqfs)); //hqfs为1或sfzh时表示是以身份证号命名
        try {
            zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipFile zf = new ZipFile(zipFile);
            ZipEntry ze = null;
            while((ze = zis.getNextEntry()) !=null ) {
                String name = ze.getName();
                if(!ze.isDirectory() && isPicture(name)) { //如果不是文件夹
                    try {
//                        String xm = getFileNameNoSuffix(name); //
                        PictureDto pd = buildDto(name);
                        Personal p = null;
                        if(isSfzh) {
                            p = personalDao.findByCzidAndSfzh(xzid, pd.getName());
                        } else {
                            p = personalDao.findByCzidAndXm(xzid, pd.getName());
                        }
                        if(p==null) {
                            addErrorPic(batchNo, name, "未检索到数据");
                            amount ++;
                        } /*else if(p.getZplj()!=null && !"".equals(p.getZplj())) {
                            addErrorPic(batchNo, name, "已有照片");
                            amount ++;
                        } */else {
                            if(pd.isHouse()) {

                                File outFile = new File(configTools.getUploadPath(UPLOAD_PATH_PRE) + File.separator + NormalTools.curDate("yyyyMMdd") + File.separator + p.getId() + NormalTools.getFileType(getFileName(name)));
                                String result = outFile.getAbsolutePath().replace(configTools.getUploadPath(), File.separator);
                                FileUtils.copyInputStreamToFile(zf.getInputStream(ze), outFile);
                                Integer w = 800, h = 800;
                                Thumbnails.of(outFile).size(w, h).toFile(outFile);

                                Assets a = new Assets();
                                a.setHzxm(p.getHzxm());
                                a.setHzsfzh(p.getHzsfzh());
                                a.setHzid(p.getHzid());
                                a.setGsxm(p.getXm());
                                a.setGsid(p.getId());
                                a.setGssfzh(p.getSfzh());
                                a.setMc("房子");
                                a.setUrl(result);
                                assetsDao.save(a);
                            } else if(p.getZplj()!=null && !"".equals(p.getZplj())) {
                                addErrorPic(batchNo, name, "已有照片");
                                amount ++;
                            } else {
                                File outFile = new File(configTools.getUploadPath(UPLOAD_PATH_PRE) + File.separator + NormalTools.curDate("yyyyMMdd") + File.separator + p.getId() + NormalTools.getFileType(getFileName(name)));
                                String result = outFile.getAbsolutePath().replace(configTools.getUploadPath(), File.separator);
                                FileUtils.copyInputStreamToFile(zf.getInputStream(ze), outFile);
                                Integer w = 800, h = 800;
                                Thumbnails.of(outFile).size(w, h).toFile(outFile);
                                personalDao.updateZplj(result, p.getSfzh());
                            }
                        }
                    } catch (Exception e) {
                        addErrorPic(batchNo, name, "重名了");
                        amount ++;
                    }
                }

            }
            if(amount>0) {
                PictureUpload pu = new PictureUpload();
                pu.setBatchNo(batchNo);
                pu.setAmount(amount);
                pu.setCreateDate(NormalTools.curDate());
                pu.setCreateLong(System.currentTimeMillis());
                pu.setCreateTime(NormalTools.curDatetime());
                pu.setXzid(xzid);
                pu.setXzmc(xzmc);
                pu.setHqfs(isSfzh?"身份证":"姓名");
                pictureUploadDao.save(pu);
            }
            zf.close();
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            zipFile.deleteOnExit();
            zipFile.delete();
        }
    }

    private void addErrorPic(String batchNo, String name, String reason) {
//        String msg = reason+":"+name;
//        ErrorTools.error2File("picture_error.txt", msg, true);
        PictureUploadRecord record = new PictureUploadRecord();
        record.setBatchNo(batchNo);
        record.setReason(reason);
        record.setContent(name);
        pictureUploadRecordDao.save(record);
    }

    public PictureDto buildDto(String name) {
        name = getFileNameNoSuffix(name); //
        name = name.replace("户主", "");
        boolean isHouse = isHouse(name);
        return new PictureDto(name, isHouse);
    }

    private boolean isHouse(String name) {
        boolean res = false;
        String [] array = new String[]{"房屋", "住房", "房子"};
        for(String a : array) {if(name.contains(a)) {res = true; break;}}
        return res;
    }

    /** 获取村庄名称 */
    public String getCzmc(List<Town> townList, String name) {
        String res = "";
        String [] array = name.split("/"); //将文件名分组
        for(int i=array.length-1; i>=0;i--) { //倒过来取
            String tempStr = array[i];
            tempStr = tempStr.length()>=2?tempStr.substring(0,2):tempStr;
            for(Town t : townList) {
                if(t.getName().contains(tempStr)) {res = t.getName(); break;}
            }
        }
        return res;
    }

    public boolean isPicture(String name) {
        name = name.toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
    }

    public String getFileName(String name) {
        name = name.substring(name.lastIndexOf("/")+1);
        return name;
    }

    public String getFileNameNoSuffix(String name) {
        name = getFileName(name);
        name = name.substring(0, name.indexOf("."));
        name.replaceAll(" ", "").replaceAll("，", "").replaceAll("。", "");
        name = rebuildName(name, ":");
        name = rebuildName(name, "：");
        return name;
    }

    private String rebuildName(String name, String split) {
        String res = name;
        if(res.contains(split)) {
            String [] array = res.split(split);
            res = array[1];
        }
        return res;
    }
}
