package com.zslin.bus.tools;

import com.zslin.bus.dao.IFamilyPlantDao;
import com.zslin.bus.dto.PlantDto;
import com.zslin.bus.model.Family;
import com.zslin.bus.model.FamilyPlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 下载种植品种工具类
 * Created by zsl on 2019/7/12.
 */
@Component
public class DownloadPlantTools {

    @Autowired
    private IFamilyPlantDao familyPlantDao;

    public List<PlantDto> buildDatas(List<Family> familyList) {
        List<PlantDto> res = new ArrayList<>();
        List<FamilyPlant> allList = familyPlantDao.findAll();
        for(Family f : familyList) {res.add(buildDto(f, buildPlantList(f.getSfzh(), allList)));}
        return res;
    }

    private List<FamilyPlant> buildPlantList(String sfzh, List<FamilyPlant> allList) {
        List<FamilyPlant> res = new ArrayList<>();
        for(FamilyPlant fp : allList) {
            if(fp.getHzsfzh().equals(sfzh)) {res.add(fp);}
        }
        return res;
    }

    private PlantDto buildDto(Family f, List<FamilyPlant> fpList) {
//        List<FamilyPlant> fpList = familyPlantDao.findByHzsfzh(f.getSfzh());
        PlantDto dto = new PlantDto();
        dto.setCzmc(f.getCzmc());
        dto.setZrc(f.getZrc());
        dto.setXzmc(f.getXzmc());
        dto.setXm(f.getXm());
        dto.setSfzh(f.getSfzh());
        dto.setLx(f.getLx());
        dto.setGd(f.getGd());
        dto.setLd(f.getLd());
        dto.setZjd(f.getZjd());
        dto.setKtgdmj(f.getKtgmj());
        String dm = ""; Float mj = 0f;
        for(FamilyPlant fp : fpList) {
            dm = fp.getZzpzdm(); mj = fp.getZzmj();
            if("1".equals(dm)) { dto.setFz(mj); } //fz
            else if("2".equals(dm)) { dto.setQz(mj);} //qz
            else if("3".equals(dm)) {dto.setQhj(mj);} //qhj
            else if("4".equals(dm)) {dto.setDhb(mj);} //dhb
            else if("5".equals(dm)) {dto.setHt(mj);}
            else if("6".equals(dm)) {dto.setBl(mj);}
            else if("7".equals(dm)) {dto.setCy(mj);}
            else if("8".equals(dm)) {dto.setTz(mj);}
            else if("9".equals(dm)) {dto.setLz(mj);}
            else if("10".equals(dm)) {dto.setL(mj);}
            else if("11".equals(dm)) {dto.setTz(mj);}
            else if("12".equals(dm)) {dto.setGj(mj);}
            else if("13".equals(dm)) {dto.setSs(mj);}
            else if("14".equals(dm)) {dto.setTghc(mj);}
            else if("30".equals(dm)) {dto.setQt(mj);}
        }
        return dto;
    }
}
