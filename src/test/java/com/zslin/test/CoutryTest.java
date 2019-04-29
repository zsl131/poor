package com.zslin.test;

import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.model.Town;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by zsl on 2019/4/15.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class CoutryTest {

    @Autowired
    private ITownDao townDao;

    /** 处理村 */
    @Test
    public void test01() {
        try {
            String excelFile = "D:/temp/cun.xls";
            FileInputStream fis = new FileInputStream(excelFile);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet s = wb.getSheetAt(0);
            Integer count = 0;
            for(int i=2;i<=s.getLastRowNum();i++) {
                Row row = s.getRow(i);
                String name1 = row.getCell(1).getStringCellValue();
                String name2 = row.getCell(2).getStringCellValue();
                Town town = townDao.findParentByNameLike(name1);
                if(town==null) {
                    System.out.println("未找到乡镇："+name1);
                } else {
                    Town t = new Town();
                    t.setName(name2);
                    t.setPid(town.getId());
                    t.setPname(town.getName());
                    townDao.save(t);
                }
                count ++;
            }

            System.out.println("合计："+count+" 条");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
