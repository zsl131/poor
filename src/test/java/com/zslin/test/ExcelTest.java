package com.zslin.test;

import com.zslin.bus.model.Personal;
import com.zslin.bus.tools.ExcelBasicTools;
import com.zslin.bus.tools.IDCardTimer;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
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
