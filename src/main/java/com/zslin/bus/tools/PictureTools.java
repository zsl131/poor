package com.zslin.bus.tools;

import com.zslin.basic.tools.ConfigTools;
import com.zslin.basic.tools.NormalTools;
import com.zslin.bus.common.tools.RandomTools;
import com.zslin.bus.dao.*;
import com.zslin.bus.dto.PictureDto;
import com.zslin.bus.model.Assets;
import com.zslin.bus.model.Personal;
import com.zslin.bus.model.PictureUpload;
import com.zslin.bus.model.PictureUploadRecord;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
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
        return NormalTools.curDate("yyyyMMddHHmmss")+"_"+ RandomTools.randomString(6);
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
        Integer sucAmount = 0;
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
                            p = personalDao.findBySfzh(pd.getName());
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
                            if(pd.isHouse() || pd.isCar()) {
                                File outFile = new File(configTools.getUploadPath(UPLOAD_PATH_PRE) + File.separator + NormalTools.curDate("yyyyMMdd") + File.separator + UUID.randomUUID() + NormalTools.getFileType(getFileName(name)));
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
                                a.setMc(pd.isHouse()?"房子":(pd.isCar()?"车子":"其他"));
                                a.setUrl(result);
                                assetsDao.save(a);
                                sucAmount++;
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
                                sucAmount++;
                            }
                        }
                    } catch (Exception e) {
                        addErrorPic(batchNo, name, "重名了");
                        amount ++;
                    }
                }

            }
            PictureUpload pu = new PictureUpload();
            pu.setBatchNo(batchNo);
            pu.setAmount(amount);
            pu.setCreateDate(NormalTools.curDate());
            pu.setCreateLong(System.currentTimeMillis());
            pu.setCreateTime(NormalTools.curDatetime());
            pu.setXzid(xzid);
            pu.setXzmc(xzmc);
            pu.setHqfs(isSfzh?"身份证":"姓名");
            pu.setSucAmount(sucAmount);
            pictureUploadDao.save(pu);
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

    String [] HOUSE_ARRAY = new String[]{"家房屋", "房屋", "住房", "房子"};
    String [] CAR_ARRAY = new String[]{"家车子", "车辆", "车", "车子"};

    public PictureDto buildDto(String name) {
        boolean isHouse = isHouse(name);
        boolean isCar = isCar(name);
        name = getFileNameNoSuffix(name); //
        name = name.replace("户主", "");
        for(String a : HOUSE_ARRAY) {name = name.replace(a, "");}
        for(String a : CAR_ARRAY) {name = name.replace(a, "");}

        return new PictureDto(name, isHouse, isCar);
    }

    private boolean isCar(String name) {
        boolean res = false;
        for(String a : CAR_ARRAY) {if(name.contains(a)) {res = true; break;}}
        return res;
    }

    private boolean isHouse(String name) {
        boolean res = false;
        for(String a : HOUSE_ARRAY) {if(name.contains(a)) {res = true; break;}}
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
