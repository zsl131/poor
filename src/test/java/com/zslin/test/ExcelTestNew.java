package com.zslin.test;

import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.bus.dao.IDictionaryDao;
import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.model.Dictionary;
import com.zslin.bus.model.Family;
import com.zslin.bus.model.Personal;
import com.zslin.bus.model.Town;
import com.zslin.bus.tools.ExcelBasicTools;
import com.zslin.bus.tools.PersonalTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 数据导入：
 * 1、基础数据导入（test14）
 * 2、处理就业类型（NormalTest.test06） //已处理
 * 3、医保局（test10）
 * 4、教育局（test09）
 * Created by zsl on 2019/4/15.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class ExcelTestNew {

    @Autowired
    private ITownDao townDao;

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IFamilyDao familyDao;

    @Autowired
    private IDictionaryDao dictionaryDao;

    /** 导入数据到数据库 */
    @Test
    public void test01() {
        try {
            Long start = System.currentTimeMillis();
            String excelFile = "D:/temp/yl0601.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            List<Personal> list = ExcelBasicTools.buildByExcel(fis, 4, 0, "buildPersonal0602", true);
            StringBuffer error = new StringBuffer();

            List<Dictionary> dataList = dictionaryDao.findByPcode("DICT_SUPPORT");

            Sort sort = SimpleSortBuilder.generateSort("orderNo");
            List<Town> xzList = townDao.findParent(sort); //乡镇
            List<Town> cunList = townDao.findAllCun(); //村
            Town xz = null, cun = null;
            Personal hz = null;//户主
            for(Personal p : list) {
//                if(p.getSfzh().length()>18) {p.setSfzh(p.getSfzh().substring(0, 18));} //处理身份证号
                p.setXb(buildXb(p.getSfzh())); //重设性别
                p.setJtdz("彝良县"+p.getXzmc()+p.getCzmc()+p.getZrc());
                xz = getTown(xzList, p.getXzmc());
                cun = getTown(cunList, p.getCzmc());
                if(xz!=null) {
                    p.setXzid(xz.getId());
                    p.setXzmc(xz.getName());
                }
                if(cun!=null) {
                    p.setCzmc(cun.getName());
                    p.setCzid(cun.getId());
                }

                String zzxmmc = PersonalTools.buildZzxmmc(dataList, p.getZzxm()); //资助项目名称
                p.setZzxmmc(zzxmmc);
                p.setLxdh(rebuildLxdh(p.getLxdh()));

                p.setSfsldl("是".equals(p.getSfsldl())?"劳动力":"无劳动力");

                p.setJylx(PersonalTools.buildJyLx(p));

                String wgdd = p.getWgdd();
                String wgdy = PersonalTools.buildWgdy(wgdd);
                String wgsf = PersonalTools.buildWgsf(wgdd);
                p.setWgdy(wgdy);
                p.setWgsf(wgsf);
                p.setNl(PersonalTools.buildAge(p.getSfzh())); //年龄

                if(isFamily(p)) { hz = p; addFamily(p);}

                if(hz!=null) {
                    p.setHzid(hz.getId());
                    p.setHzsfzh(hz.getSfzh());
                    p.setHzxm(hz.getXm());
                }
                personalDao.save(p);
            }
            ErrorTools.error2File("base.txt", error.toString());
            Long end = System.currentTimeMillis();
            System.out.println("花时间："+((end-start)/1000) + " 秒。处理："+list.size()+" 条");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String rebuildLxdh(String lxdh) {
        if(lxdh!=null && lxdh.contains("E10")) {
            DecimalFormat df = new DecimalFormat("#");
            lxdh = df.format(Double.parseDouble(lxdh));
        }
        return lxdh;
    }

    private String buildXb(String sfzh) {
        String sex = "";
        if (Integer.parseInt(sfzh.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
            sex = "女";
        } else {
            sex = "男";
        }
        return sex;
    }

    private boolean isFamily(Personal p) {
//        return (p.getJtrs()!=null && p.getJtrs()>0 && p.getXh()!=null && p.getXh()>0) || "户主".equals(p.getYhzgx());
        return "户主".equals(p.getYhzgx());
    }

    private void addFamily(Personal p) {
        if(isFamily(p)) {
            Family f = new Family();
            f.setXzmc(p.getXzmc());
            f.setXzid(p.getXzid());
            f.setBqdd(p.getBqdd());
            f.setBqsj(p.getBqsj());
            f.setBz(p.getBz());
            f.setJtdz(p.getJtdz());
            f.setJtrs(p.getJtrs());
            f.setLxdh(p.getLxdh());
            f.setMz(p.getMz());
            f.setPksx(p.getPksx());
            f.setSfzh(p.getSfzh());
            f.setXb(p.getXb());
            f.setXh(p.getXh());
            f.setJtdz(p.getJtdz());
            f.setYqhsx(p.getYqhsx());
            f.setXm(p.getXm());
            f.setGd(p.getGd());
            f.setZjd(p.getZjd());
            f.setZzpz(p.getZzpz());
            f.setZzdmj(p.getZzdmj());
            f.setLx(p.getLx());
            f.setCzid(p.getCzid());
            f.setCzmc(p.getCzmc());
            f.setZrc(p.getZrc());
            f.setHbh(p.getHbh());
            f.setAzfs(p.getAzfs());
            familyDao.save(f);
        }
    }

    /** 获取乡镇 */
    private Town getTown(List<Town> townList, String address) {
        Town t = null;
        if(address!=null && address.length()>2) {
            for(Town town : townList) {
                if(address.contains(town.getName().substring(0, 2))) {t = town; break;}
            }
        }
        return t;
    }
}
