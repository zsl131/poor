package com.zslin.test;

import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.model.Family;
import com.zslin.bus.model.Personal;
import com.zslin.bus.model.Town;
import com.zslin.bus.tools.ExcelBasicTools;
import com.zslin.bus.tools.IDCardTimer;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
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
