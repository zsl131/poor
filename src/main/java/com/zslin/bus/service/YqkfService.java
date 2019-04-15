package com.zslin.bus.service;

import com.zslin.bus.dto.YqkfDto;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class YqkfService {

    /**
     * 返回失败的dto，之所以返回dto对象，是因为数据内容太多，dto对象中只用存储较少的几个关键信息
     * @return
     */
    public List<YqkfDto> importYqkf() {
        excelToField("d:/test/01.xls",1,1);
        return null;
    }

    public void excelToField(String excelFile, int beginLine, int sheetNum) {
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
                    System.out.print(rc.getColumnIndex()+"-"+row.getLastCellNum()+"|");
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
