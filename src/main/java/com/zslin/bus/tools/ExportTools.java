package com.zslin.bus.tools;

import com.zslin.basic.tools.NormalTools;
import com.zslin.bus.model.Personal;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 导出数据工具类
 * Created by zsl on 2018/11/30.
 */
@Component
public class ExportTools {

    private static String [] columnName;

    private static final String FIELD_SPE = "__";

    static {
        columnName = new String [] {"活动标题", "活动时间", "姓名", "电话", "来源", "状态", "签到", "备注"};
    }

    /** 获取Get方法 */
    private String getFieldName(String field) {
        return "get"+(field.substring(0,1).toUpperCase())+field.substring(1);
    }

    private String getValue(String field, Object obj) {
        try {
            String processStr = null;
            if(field.contains(FIELD_SPE)) {
                String [] array = field.split(FIELD_SPE);
                field = array[0];
                processStr = array[1];
            }
            String methodName = getFieldName(field);
//            System.out.println("========="+methodName);
            Class clz = obj.getClass();
            Method m = clz.getMethod(methodName);
            Object o = m.invoke(obj);
            String res = o.toString();
            if(processStr!=null && !"".equals(processStr)) {
                if("inRange".equalsIgnoreCase(processStr)) { //是否在课标范围
                    if(res==null) {return "未配置";}
                    else if("0".equals(res)) {return "不在";}
                    else if("1".equals(res)) {return "在范围";}
                }
            }
            return res;
        } catch (Exception e) {
            return "";
        }
    }

    /*public void export(String title, String sheetName, List data, String[]columns, String[]fields, OutputStream os) {
        XSSFWorkbook workbook = new XSSFWorkbook();                        // 创建工作簿对象
        XSSFSheet sheet = workbook.createSheet(sheetName);                     // 创建工作表

        // 产生表格标题行
        XSSFRow rowm = sheet.createRow(0);
        XSSFCell cellTiltle = rowm.createCell(0);
        //设置标题和单元格样式
        XSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);  //获取列头样式对象
        XSSFCellStyle style = this.getStyle(workbook);                    //单元格样式对象

        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (columns.length - 1)));
        cellTiltle.setCellStyle(columnTopStyle);
        cellTiltle.setCellValue(title);

        // 定义所需列数
        int columnNum = columns.length;
        XSSFRow rowRowName = sheet.createRow(2);

        // 将列头设置到sheet的单元格中
        for (int n = 0; n < columnNum; n++) {
            XSSFCell cellRowName = rowRowName.createCell(n);                  //创建列头对应个数的单元格
            cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
            XSSFRichTextString text = new XSSFRichTextString(columns[n]);
            cellRowName.setCellValue(text);                                    //设置列头单元格的值
            cellRowName.setCellStyle(columnTopStyle);                          //设置列头单元格样式
        }

        //将查询出的数据设置到sheet对应的单元格中
        for (int i = 0; i < data.size(); i++) {
            Object obj = data.get(i);//遍历每个对象
            XSSFRow row = sheet.createRow(i + 3);//创建所需的行数

            for (int j = 0; j < columns.length; j++) {
                XSSFCell cell = row.createCell(j, XSSFCell.CELL_TYPE_STRING);//设置单元格的数据类型
                //第一列为数字类型并设置单元格的值
                cell.setCellValue(getValue(fields[j], obj)); //设置值
                cell.setCellStyle(style); //设置单元格样式
            }
        }

        //让列宽随着导出的列长自动适应
        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                XSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    //取得当前的单元格
                    XSSFCell currentCell = currentRow.getCell(colNum);
                    //如果当前单元格类型为字符串
                    if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            //将单元格里面值大小作为列宽度
                            columnWidth = length;
                        }
                    }
                }
            }
            //再根据不同列单独做下处理
            if (colNum == 0) {
                sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
            } else {
                sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
            }
        }

        if (workbook != null) {
            try {
                workbook.write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    public void exportData(String placeName, List<Personal> data, OutputStream os) {
        String title = placeName+"易地扶贫搬迁对象花名册-"+ NormalTools.curDate("yyyyMMdd")+"-（"+data.size()+"人）";
        XSSFWorkbook workbook = new XSSFWorkbook();                        // 创建工作簿对象
        XSSFSheet sheet = workbook.createSheet("花名册");                     // 创建工作表

        // 产生表格标题行
        XSSFRow rowm = sheet.createRow(0);
        XSSFCell cellTiltle = rowm.createCell(0);
        //设置标题和单元格样式
        XSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);  //获取列头样式对象
        XSSFCellStyle style = this.getStyle(workbook);                    //单元格样式对象

        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (columnName.length - 1)));
        cellTiltle.setCellStyle(columnTopStyle);
        cellTiltle.setCellValue(title);

        // 定义所需列数
        int columnNum = columnName.length;
        XSSFRow rowRowName = sheet.createRow(2);

        // 将列头设置到sheet的单元格中
        for (int n = 0; n < columnNum; n++) {
            XSSFCell cellRowName = rowRowName.createCell(n);                  //创建列头对应个数的单元格
            cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
            XSSFRichTextString text = new XSSFRichTextString(columnName[n]);
            cellRowName.setCellValue(text);                                    //设置列头单元格的值
            cellRowName.setCellStyle(columnTopStyle);                          //设置列头单元格样式
        }

        //将查询出的数据设置到sheet对应的单元格中
        for (int i = 0; i < data.size(); i++) {
            //TODO 导出数据
            /*ActivityStudent as = data.get(i);//遍历每个对象
            XSSFRow row = sheet.createRow(i + 3);//创建所需的行数

            for (int j = 0; j < columnName.length; j++) {
                XSSFCell cell = row.createCell(j, XSSFCell.CELL_TYPE_STRING);//设置单元格的数据类型
                //第一列为数字类型并设置单元格的值
                if (j == 0) { cell.setCellValue(as.getActTitle()); }
                else if(j==1) {cell.setCellValue(as.getHoldTime());}
                else if(j==2) {cell.setCellValue(as.getStuName());}
                else if(j==3) {cell.setCellValue(as.getPhone());}
                else if(j==4) {cell.setCellValue("0".equals(as.getFromFlag())?"社会":"学校");}
                else if(j==5) {cell.setCellValue("1".equals(as.getStatus())?"通过":"驳回");}
                else if(j==6) {cell.setCellValue("1".equals(as.getHasCheck())?"已签到":"未签到");}
                else if(j==7) {cell.setCellValue("             ");}
                cell.setCellStyle(style); //设置单元格样式
            }*/
        }

        //让列宽随着导出的列长自动适应
        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                XSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    //取得当前的单元格
                    XSSFCell currentCell = currentRow.getCell(colNum);
                    //如果当前单元格类型为字符串
                    if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            //将单元格里面值大小作为列宽度
                            columnWidth = length;
                        }
                    }
                }
            }
            //再根据不同列单独做下处理
            if (colNum == 0) {
            sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
            } else {
            sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
            }
        }

        if (workbook != null) {
            try {
                workbook.write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     180      * @param
     181      * @return
     182      * @author jiaqing.xu@hand-china.com
     183      * @date 2017/10/19 13:31
     184      * @description 标题行的单元格样式
     185      */
    public XSSFCellStyle getColumnTopStyle(XSSFWorkbook workbook) {
        // 设置字体
        XSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    /**
     227      * @param
     228      * @return
     229      * @author jiaqing.xu@hand-china.com
     230      * @date 2017/10/19 13:31
     231      * @description 列数据信息单元格样式
     232      */
    public XSSFCellStyle getStyle(XSSFWorkbook workbook) {
        // 设置字体
        XSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        XSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }
}
