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
import com.zslin.bus.tools.IDCardTimer;
import com.zslin.bus.tools.PersonalTools;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
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
public class ExcelTest {

    @Autowired
    private ITownDao townDao;

    @Autowired
    private IPersonalDao personalDao;

    @Autowired
    private IFamilyDao familyDao;

    @Autowired
    private IDictionaryDao dictionaryDao;

    @Test
    public void test14() {
        try {
            Long start = System.currentTimeMillis();
            String excelFile = "D:/temp/ylydbq0429.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            List<Personal> list = ExcelBasicTools.buildByExcel(fis, 4, 0, "buildPersonal0429", true);
            System.out.println("===personal count :"+ list.size());
            Integer errorCount = 0;
            StringBuffer sb = new StringBuffer();

            List<Town> townList = townDao.findParent(SimpleSortBuilder.generateSort("orderNo"));
            List<Town> cunList = townDao.findAllCun();

            Town t = null;
            Town c = null;
            Personal hz = null;//户主
            Integer amount = 0;
            for(Personal p : list) {
                String wgdd = p.getWgdd();
                String wgdy = PersonalTools.buildWgdy(wgdd);
                String wgsf = PersonalTools.buildWgsf(wgdd);
                p.setWgdy(wgdy);
                p.setWgsf(wgsf);
                if(p.getSfzh().length()>18) {p.setSfzh(p.getSfzh().substring(0, 18));} //处理身份证号
                if(isFamily(p)) { hz = p;}
                t = getTown(townList, p.getJtdz());
                c = getTown(cunList, p.getJtdz());
                if(t!=null) {
                    p.setXzid(t.getId());
                    p.setXzmc(t.getName());
                } else {
                    System.out.println(p.getXm()+"========"+p.getSfzh()+"-----未检索到乡镇");
                    sb.append(p.getXm()).append(":").append(p.getSfzh()).append("::未检索到乡镇：").append(p.getJtdz());
                    errorCount ++;
                }

                if(c!=null) {
                    p.setCzid(c.getId());
                    p.setCzmc(c.getName());
                } else {
                    System.out.println(p.getXm()+"========"+p.getSfzh()+"-----未检索对应村");
                    sb.append(p.getXm()).append(":").append(p.getSfzh()).append("::未检索到村：").append(p.getJtdz());
                    errorCount ++;
                }

                if(hz!=null) {
                    p.setHzid(hz.getId());
                    p.setHzsfzh(hz.getSfzh());
                    p.setHzxm(hz.getXm());
                    p.setXh(hz.getXh());
                }

                String lxdh = p.getLxdh();
                if(lxdh!=null && lxdh.contains("E10")) {
                    DecimalFormat df = new DecimalFormat("#");
                    lxdh = df.format(Double.parseDouble(lxdh));
                }
                p.setLxdh(lxdh);

                p.setSfsldl("是".equals(p.getSfsldl())?"劳动力":"无劳动力");

                p.setJylx(PersonalTools.buildJyLx(p));

                personalDao.save(p);
                addFamily(p);
            }
            System.out.println("totalCount::"+list.size()+"========errorCount::"+errorCount);
            ErrorTools.error2File("base_error.txt", sb.toString());
            Long end = System.currentTimeMillis();
            System.out.println("花时间："+((end-start)/1000) + " 秒。处理："+amount+" 条");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test13() {
        try {
            Long start = System.currentTimeMillis();
            String excelFile = "D:/temp/ylydbq0419.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            List<Personal> list = ExcelBasicTools.buildByExcel0419(fis, 4, 0);
            System.out.println("total::"+list.size());
            String lxdh = null;
            Integer amount = 0;
            for(Personal p : list) {
                if(p.getXh()!=null && p.getXh()>0) {
                    amount ++;
//                    personalDao.updateXh(p.getXh(), p.getSfzh());
                    JDBCTools.updateXh(p.getXh(), p.getSfzh());
                }
            }
            Long end = System.currentTimeMillis();
            System.out.println("花时间："+((end-start)/1000) + " 秒。处理："+amount+" 条");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test12() {
        try {
            Long start = System.currentTimeMillis();
            String excelFile = "D:/temp/ylydbq0419.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            List<Personal> list = ExcelBasicTools.buildByExcel0419(fis, 4, 0);
            System.out.println("total::"+list.size());
            String lxdh = null;
            Integer amount = 0;
            for(Personal p : list) {
                System.out.println("xh======="+p.getXh());
                if(p.getXh()!=null && p.getXh()>0) {
                    amount ++;
                    personalDao.updateXh(p.getXh(), p.getSfzh());
                }
            }
            Long end = System.currentTimeMillis();
            System.out.println("花时间："+((end-start)/1000) + " 秒。处理："+amount+" 条");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test11() {
        try {
            Long start = System.currentTimeMillis();
            String excelFile = "D:/temp/ylydbq0419.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            List<Personal> list = ExcelBasicTools.buildByExcel0419(fis, 4, 0);
            System.out.println("total::"+list.size());
            String lxdh = null;
            Integer amount = 0;
            for(Personal p : list) {
                lxdh = p.getLxdh();
                if("532129198202211763".equals(p.getSfzh()) || "532129197807221732".equals(p.getSfzh())) {
                    System.out.println("========="+p);
                }
                if(lxdh!=null) {
                    System.out.println(p.getSfzh()+"======"+lxdh);
                    if(lxdh.contains("E10")) {
                        DecimalFormat df = new DecimalFormat("#");
                        lxdh = df.format(Double.parseDouble(lxdh));
                    }
                    personalDao.updateLxdh(lxdh, p.getSfzh());
                    amount ++;
                }
            }
            Long end = System.currentTimeMillis();
            System.out.println("花时间："+((end-start)/1000) + " 秒。处理："+amount+" 条");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test10() {
        try {
            String excelFile = "D:/temp/ybj-just.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            List<Personal> list = ExcelBasicTools.buildByExcel(fis, 4, 0, "buildPersonalYbj0423", true);
            System.out.println("===personal count :"+ list.size());
            Integer errorCount = 0;
            StringBuffer sb = new StringBuffer();
            String sfzh;
            for(Personal p : list) {
                sfzh = p.getSfzh();
//                Personal obj= personalDao.findBySfzh(sfzh);
//                Integer count = personalDao.updateYbj(p.getCjhzpx(), p.getWgdd(), p.getQymc(), p.getGwmc(), p.getWgsj(), p.getYgz(), p.getCyxm(), p.getCydd(), p.getCysj(), p.getYsr(),
//                        p.getWgqx(), p.getGyxgw(), p.getZzcy(), p.getWfwcyy(), p.getPxxq(), p.getLxdh(), p.getSfyb(), p.getCbxz(), p.getCbdw(), p.getSfhb(), sfzh);
                Integer count = personalDao.updateYbj(p.getSfyb(), p.getCbxz(), p.getCbdw(), p.getSfhb(), sfzh);
                if(count==null || count<=0) {
                    errorCount ++;
                    System.out.println("error========="+sfzh);
                    sb.append(sfzh).append(":未找到\n");
                }
            }
            System.out.println("totalCount::"+list.size()+"========errorCount::"+errorCount);
            ErrorTools.error2File("ybj_error.txt", sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test09() {
        try {
            String excelFile = "D:/temp/jyj-just.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            List<Personal> list = ExcelBasicTools.buildByExcel(fis, 4, 0, "buildPersonalJyj0423", true);
            System.out.println("===personal count :"+ list.size());
            Integer errorCount = 0;
            List<Dictionary> dataList = dictionaryDao.findByPcode("DICT_SUPPORT");
            StringBuffer sb = new StringBuffer();
            String sfzh, zzxmmc;
            for(Personal p : list) {
                sfzh = p.getSfzh();
//                Personal obj= personalDao.findBySfzh(sfzh);
                zzxmmc = buildZzxmmc(dataList, p.getZzxm());
                Integer count = personalDao.updateJxqk(p.getSfzx(), p.getJyjd(), p.getJdxx(), p.getJdnj(), p.getSfxszz(), p.getZzje(), p.getZzxm(), zzxmmc, sfzh);
                if(count==null || count<=0) {
                    errorCount ++;
                    System.out.println("error========="+sfzh);
                    sb.append(sfzh).append(":未找到\n");
                }
                /*if(obj==null) {
                    errorCount ++;
                    System.out.println("error========="+sfzh);
                    sb.append(sfzh).append(":未找到\n");
                } else {
                    obj.setSfzx(p.getSfzx());
                    obj.setJyjd(p.getJyjd());
                    obj.setJdxx(p.getJdxx());
                    obj.setJdnj(p.getJdnj());
                    obj.setSfxszz(p.getSfxszz());
                    obj.setZzje(p.getZzje());
                    obj.setZzxm(p.getZzxm());
                    obj.setZzxmmc(buildZzxmmc(dataList, p.getZzxm()));
                    personalDao.save(obj);
                }*/
            }
            System.out.println("totalCount::"+list.size()+"========errorCount::"+errorCount);
            ErrorTools.error2File("jyj_error.txt", sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    //处理性别异常的数据
    @Test
    public void test08() {
        try {
            String excelFile = "D:/temp/ylydbq0419.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            List<Personal> list = ExcelBasicTools.buildByExcel0419(fis, 5739, 0);
            for(Personal p : list) {
                if(p.getSfzh().length()>18) {p.setSfzh(p.getSfzh().substring(0, 18));} //处理身份证号
                String xb = p.getXb().trim();
                if(xb!=null && xb.length()==1) {
                    personalDao.updateXb(p.getSfzh(), xb);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 导入数据到数据库：人数最全的表格 */
    @Test
    public void test07() {
        try {
            String excelFile = "D:/temp/ylydbq0419.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            List<Personal> list = ExcelBasicTools.buildByExcel0419(fis, 4, 0);
            Town t = null;
            Personal hz = null;//户主
            for(Personal p : list) {
                if(p.getSfzh().length()>18) {p.setSfzh(p.getSfzh().substring(0, 18));} //处理身份证号
                if(isFamily(p)) { hz = p;}
                t = getTown(p.getJtdz());
                if(t!=null) {
                    p.setXzid(t.getId());
                    p.setXzmc(t.getName());
                } else {
                    System.out.println(p.getXm()+"========"+p.getSfzh()+"-----未检索到乡镇");
                }
                if(hz!=null) {
                    p.setHzid(hz.getId());
                    p.setHzsfzh(hz.getSfzh());
                    p.setHzxm(hz.getXm());
                }
                personalDao.save(p);
                addFamily(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 导入数据到数据库 */
    @Test
    public void test06() {
        try {
            String excelFile = "D:/temp/ylydbq.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            List<Personal> list = ExcelBasicTools.buildByExcel(fis);
            StringBuffer error = new StringBuffer();
            Town t = null;
            Personal hz = null;//户主
            for(Personal p : list) {
                if(p.getSfzh().length()>18) {p.setSfzh(p.getSfzh().substring(0, 18));} //处理身份证号
                if(isFamily(p)) { hz = p;}
                t = getTown(p.getJtdz());
                if(t!=null) {
                    p.setXzid(t.getId());
                    p.setXzmc(t.getName());
                } else {
                    System.out.println(p.getXm()+"========"+p.getSfzh()+"-----未检索到乡镇");
                    error.append(p.getXm()).append(":").append(p.getSfzh()).append("::未检索到乡镇：").append(p.getJtdz());
                }
                if(hz!=null) {
                    p.setHzid(hz.getId());
                    p.setHzsfzh(hz.getSfzh());
                    p.setHzxm(hz.getXm());
                }
                personalDao.save(p);
                addFamily(p);
            }
            ErrorTools.error2File("base.txt", error.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isFamily(Personal p) {
        return (p.getJtrs()!=null && p.getJtrs()>0 && p.getXh()!=null && p.getXh()>0) || "户主".equals(p.getYhzgx());
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
            familyDao.save(f);
        }
    }

    private Town getTown(String address) {
        Town t = null;
        if(address!=null && address.length()>2) {
            t = townDao.findByNameLike(address.substring(0, 2));
        }
        return t;
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

    /*@Test
    public void testAge() {
        System.out.println(IDCardTimer.buildAge("532127198803011115"));
        System.out.println(IDCardTimer.buildAge("532127198804161115"));
        System.out.println(IDCardTimer.buildAge("532127198804171115"));
        System.out.println(IDCardTimer.buildAge("532127198804151115"));
        System.out.println(IDCardTimer.buildAge("532127198805171115"));
    }*/

    @Test
    public void test05()  {
        String card = "532127198803011115";
        System.out.println(ExcelBasicTools.isCard(card));
        System.out.println(ExcelBasicTools.isCard("532127198803011112"));
    }

    @Test
    public void test04() {
        try {
            String excelFile = "D:/temp/ylydbq.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            List<Personal> list = ExcelBasicTools.buildByExcel(fis);
            for(Personal p : list) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test03() {
        String str = "1.0";
//        Integer res = ExcelBasicTools.buildInteger(str);
        Integer res = Integer.parseInt(str);
        System.out.println(res);
    }

    @Test
    public void test02() {
        String excelFile = "D:/temp/ylydbq.xls";
        int beginLine = 3, sheetNum = 0;
        Workbook wb = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(excelFile);
            wb = WorkbookFactory.create(fis);
            Sheet s = wb.getSheetAt(sheetNum);
            for(int i=beginLine;i<=s.getLastRowNum();i++) {
                Row row = s.getRow(i);
                Personal p = ExcelBasicTools.buildPersonal(row);
                System.out.print(p);
                System.out.println("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test01() {
        String excelFile = "D:/temp/ylydbq.xls";
        int beginLine = 4, sheetNum = 0;
        Workbook wb = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(excelFile);
            wb = WorkbookFactory.create(fis);
            Sheet s = wb.getSheetAt(sheetNum);
            for(int i=beginLine;i<=s.getLastRowNum();i++) {
                Row row = s.getRow(i);
                boolean flag = false;
                String cur = "";
                String as = "";
                for (Cell rc : row) {
                    System.out.print(ExcelBasicTools.getBaseCellValue(rc) + "|");
                    /*int cellType = rc.getCellType();
                    if(Cell.CELL_TYPE_NUMERIC == cellType) { //数字
                        Double d = rc.getNumericCellValue();
                        System.out.print("number::"+d);
                    } else if(Cell.CELL_TYPE_STRING == cellType) { //字符串
                        RichTextString rts = rc.getRichStringCellValue();
                        System.out.print(rts==null?"null":rts.getString()+"|");
                    } else if(Cell.CELL_TYPE_BLANK == cellType) {
                        System.out.print("-blank-");
                    }*/
//                    System.out.print(rc.getColumnIndex()+"-"+row.getLastCellNum()+"|");
                }
                System.out.println("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
