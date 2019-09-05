package com.zslin.bus.tools;

import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.MyBeanUtils;
import com.zslin.bus.dao.IDictionaryDao;
import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.model.Dictionary;
import com.zslin.bus.model.Family;
import com.zslin.bus.model.Personal;
import com.zslin.bus.model.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

/**
 *  人员导入工具
 * Created by zsl on 2019/8/24.
 */
@Component
public class ImportTools {

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IFamilyDao familyDao;

    @Autowired
    private IDictionaryDao dictionaryDao;

    @Autowired
    private ITownDao townDao;

    /** 处理上传的Excel */
    public void process(InputStream inputStream) {
        System.out.println("==========开始处理上传的Excel文件===========");
        List<Dictionary> dataList = dictionaryDao.findByPcode("DICT_SUPPORT");
        List<Personal> personalList = ExcelBasicTools.buildByExcel(inputStream, 4, 0, "buildPersonal0822", true);
        System.out.println("=======共解析出【"+personalList.size()+"】条人员信息=======");

        Sort sort = SimpleSortBuilder.generateSort("orderNo");
        List<Town> xzList = townDao.findParent(sort); //乡镇
        List<Town> cunList = townDao.findAllCun(); //村
        Town xz = null, cun = null;
        Personal hz = null;//户主
        int count = 0;
        for(Personal p : personalList) {
            System.out.println("=====处理：："+(count++));
//                if(p.getSfzh().length()>18) {p.setSfzh(p.getSfzh().substring(0, 18));} //处理身份证号
            p.setXb(buildXb(p.getSfzh(), p.getXb())); //重设性别
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

            p.setSfsldl(isLdl(p.getSfsldl())?"劳动力":"无劳动力");

            p.setJylx(PersonalTools.buildJyLx(p));

            String wgdd = p.getWgdd();
            String wgdy = PersonalTools.buildWgdy(wgdd);
            String wgsf = PersonalTools.buildWgsf(wgdd);
            p.setWgdy(wgdy);
            p.setWgsf(wgsf);
            p.setNl(PersonalTools.buildAge(p.getSfzh())); //年龄

            try {
                Family oldFamily = loadFamily(p.getSfzh()); //获取数据

                if(isFamily(p)) { hz = p;
                    Family f = buildFamily(p);
                    if(f!=null) {
                        addOrUpdateFamily(oldFamily, f);
                    }
                }

                if(hz!=null) {
                    p.setHzid(hz.getId());
                    p.setHzsfzh(hz.getSfzh());
                    p.setHzxm(hz.getXm());
                }

                addOrUpdatePersonal(loadPersonal(p.getSfzh()), p);
            } catch (Exception e) { //主要是处理Personal和Family有重复数据的情况
            }
        }

        //全部处理完后重新统计就业人数和劳动力人数
        processCount();
        System.out.println("+++++++处理完成+++++++");
    }

    private boolean isLdl(String val) {
        return !(val.contains("无劳动力") || val.contains("丧失劳动力") || "否".equals(val));
    }

    private Family addOrUpdateFamily(Family old, Family f) {
        if(old==null) {
            familyDao.save(f);
            return f;
        } else {
            MyBeanUtils.copyProperties(f, old, "id", "sfzh");
            familyDao.save(old);
            return old;
        }
    }

    private void addOrUpdatePersonal(Personal old, Personal p) {
        if(old==null) {
            personalDao.save(p);
        } else {
            MyBeanUtils.copyProperties(p, old, "id", "sfzh");
            personalDao.save(old);
        }
    }

    private Family loadFamily(String sfzh) {
        Family f = familyDao.findBySfzh(sfzh);
        return f;
    }

    private Personal loadPersonal(String sfzh) {
        Personal p = personalDao.findBySfzh(sfzh);
        return p;
    }

    /** 处理就业人数和劳动力人数 */
    private void processCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Family> familyList = familyDao.findAll();
                for(Family f : familyList) {
                    String sfzh = f.getSfzh();
                    Integer ldlrs = personalDao.findLdlCountByHz(sfzh);
                    Integer jyrs = personalDao.findJyCountByHz(sfzh);
                    familyDao.updateCount(ldlrs, jyrs, sfzh);
                }
            }
        }).start();
    }

    private String rebuildLxdh(String lxdh) {
        if(lxdh!=null && lxdh.contains("E10")) {
            DecimalFormat df = new DecimalFormat("#");
            lxdh = df.format(Double.parseDouble(lxdh));
        }
        return lxdh;
    }

    private String buildXb(String sfzh, String xb) {
        if(sfzh==null || sfzh.length()<18) {return xb;}
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

    private Family buildFamily(Personal p) {
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
            return f;
        }
        return null;
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
