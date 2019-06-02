package com.zslin.bus.tools;

import com.zslin.bus.model.Personal;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel处理工具类
 * Created by zsl on 2019/4/15.
 */
public class ExcelBasicTools {

    public static List<Personal> buildByExcel(InputStream is) {
        return buildByExcel(is, 4, 0);
    }

    public static List<Personal> buildByExcel(InputStream is, Integer beginLine, Integer sheetNum, String methodName, boolean isStr) {
        beginLine=beginLine==null?0:beginLine;
        sheetNum = sheetNum==null?0:sheetNum;
        List<Personal> result = new ArrayList<>();
//        Workbook wb = null;
        try {
            Workbook wb = WorkbookFactory.create(is);
            Sheet s = wb.getSheetAt(sheetNum);
            for(int i=beginLine;i<=s.getLastRowNum();i++) {
                Row row = s.getRow(i);
                Personal p = null;
                if("buildPersonalJyj0423".equals(methodName)) {
                    p = buildPersonalJyj0423(row, isStr);
                } else if("buildPersonalYbj0423".equals(methodName)) {
                    p = buildPersonalYbj0423(row, isStr);
                } else if("buildPersonal0429".equals(methodName)) {
                    p = buildPersonal0429(row, isStr);
                } else if("buildPersonal0602".equals(methodName)) {
                    p = buildPersonal0602(row, isStr);
                }
                if(p!=null) {
                    result.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if(is!=null) {is.close();}
            } catch (IOException e) {
            }
        }
        return result;
    }

    public static List<Personal> buildByExcel0419(InputStream is, Integer beginLine, Integer sheetNum) {
        beginLine=beginLine==null?0:beginLine;
        sheetNum = sheetNum==null?0:sheetNum;
        List<Personal> result = new ArrayList<>();
//        Workbook wb = null;
        try {
            Workbook wb = WorkbookFactory.create(is);
            Sheet s = wb.getSheetAt(sheetNum);
            for(int i=beginLine;i<=s.getLastRowNum();i++) {
                Row row = s.getRow(i);
                Personal p = buildPersonal0419(row);
                result.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if(is!=null) {is.close();}
            } catch (IOException e) {
            }
        }
        return result;
    }

    /** 将一行数据转换成一个对象 */
    public static Personal buildPersonalYbj0423(Row row, boolean isStr) {
        Personal p = new Personal();
        int index = 0;
        for(Cell cell : row) {
            String val = getBaseCellValue(cell, isStr);
            switch (index) {
                case 0: //序号
                    p.setXh(buildInteger(val)); break;
                case 1: //姓名
                    p.setXm(val); break;
                case 2: //家庭人数
                    p.setJtrs(buildInteger(val)); break;
                case 3: //姓名
                    p.setXm(val); break;
                case 4: //与户主关系
                    p.setYhzgx(val); break;
                case 5: //身份证号
                    val = val.length()>=18?val.substring(0, 18):val; //处理身份证号
                    p.setSfzh(val); break;
                case 6: //6-9医保相关
                    p.setSfyb(val); break;
                case 7:
                    p.setCbxz(val); break;
                case 8:
                    p.setCbdw(val); break;
                case 9:
                    p.setSfhb(val); break;
                //16-32
                /*case 16:
                    p.setCjhzpx(val); break;
                case 17:
                    p.setWhcd(val); break;
                case 18:
                    p.setWgdd(val); break;
                case 19:
                    p.setQymc(val); break;
                case 20:
                    p.setGwmc(val); break;
                case 21:
                    p.setWgsj(val); break;
                case 22:
                    p.setYgz(buildFloat(val)); break;
                case 23:
                    p.setCyxm(val); break;
                case 24:
                    p.setCydd(val); break;
                case 25:
                    p.setCysj(val); break;
                case 26:
                    p.setYsr(buildFloat(val)); break;
                case 27:
                    p.setWgqx(val); break;
                case 28:
                    p.setGyxgw(val); break;
                case 29:
                    p.setZzcy(val); break;
                case 30:
                    p.setWfwcyy(val); break;
                case 31:
                    p.setPxxq(val); break;
                case 32:
                    p.setLxdh(val); break;*/
            }
            index++;
        }
        return p;
    }

    /** 将一行数据转换成一个对象 */
    public static Personal buildPersonalJyj0423(Row row, boolean isStr) {
        Personal p = new Personal();
        int index = 0;
        for(Cell cell : row) {
            String val = getBaseCellValue(cell, isStr);
            switch (index) {
                case 0: //序号
                    p.setXh(buildInteger(val)); break;
                case 1: //姓名
                    p.setXm(val); break;
                case 2: //家庭人数
                    p.setJtrs(buildInteger(val)); break;
                case 3: //姓名
                    p.setXm(val); break;
                case 4: //与户主关系
                    p.setYhzgx(val); break;
                case 5: //身份证号
                    val = val.length()>=18?val.substring(0, 18):val; //处理身份证号
                    p.setSfzh(val); break;
                case 6: //性别
                    p.setXb(val); break;
                case 7: //民族
                    p.setMz(val); break;
                case 8: //年龄
                    p.setNl(buildInteger(val)); break;
                case 9: //是否是劳动力
                    p.setSfsldl(val); break;
                case 10: //家庭地址
                    p.setJtdz(val); break;
                case 11: //脱贫属性
                    p.setPksx(val); break;
                case 15: //15-21就学相关
                    p.setSfzx(val); break;
                case 16:
                    p.setJyjd(val); break;
                case 17:
                    p.setJdxx(val); break;
                case 18:
                    p.setJdnj(val); break;
                case 19:
                    p.setSfxszz(val); break;
                case 20:
                    p.setZzxm(val); break;
                case 21:
                    p.setZzje(buildFloat(val)); break;

            }
            index++;
        }
        return p;
    }

    /** 将一行数据转换成一个对象 */
    public static Personal buildPersonal0429(Row row, boolean isStr) {
        Personal p = new Personal();
        int index = 0;
        for(Cell cell : row) {
            String val = getBaseCellValue(cell, isStr);
            switch (index) {
                case 0: //序号
                    p.setXh(buildInteger(val)); break;
                case 1: //姓名
                    p.setHzxm(val); break;
                case 2: //家庭人数
                    p.setJtrs(buildInteger(val)); break;
                case 3: //姓名
                    p.setXm(val); break;
                case 4: //与户主关系
                    p.setYhzgx(val); break;
                case 5: //身份证号
                    val = val.length()>=18?val.substring(0, 18):val; //处理身份证号
                    p.setSfzh(val); break;
                case 6: //性别
                    p.setXb(val); break;
                case 7: //民族
                    p.setMz(val); break;
                case 8: //年龄
                    p.setNl(buildInteger(val)); break;
                case 9: //是否是劳动力
                    p.setSfsldl(val); break;
                case 10: //家庭地址
                    p.setJtdz(val); break;
                case 11: //脱贫属性
                    p.setPksx(val); break;
                case 12: //参加过何种培训
                    p.setCjhzpx(val); break;
                case 13: //文化程度
                    p.setWhcd(val); break;

                case 14: //务工地点
                    p.setWgdd(val); break;
                case 15: //企业名称
                    p.setQymc(val); break;
                case 16: //岗位名称
                    p.setGwmc(val); break;
                case 17: //务工时间
                    p.setWgsj(val); break;
                case 18: //月工资收入
                    p.setYgz(buildFloat(val)); break;
                case 19: //创业项目
                    p.setCyxm(val); break;
                case 20: //创业地点
                    p.setCydd(val); break;
                case 21: //创业时间
                    p.setCysj(val); break;
                case 22: //月收入
                    p.setYsr(buildFloat(val)); break;
                case 23: //务工去向
                    p.setWgqx(val); break;
                case 24: //公益性岗位
                    p.setGyxgw(val); break;
                case 25: //自主创业
                    p.setZzcy(val); break;
                case 26: //无法外出原因
                    p.setWfwcyy(val); break;
                case 27: //培训需求
                    p.setPxxq(val); break;
                case 28: //联系电话
                    p.setLxdh(val); break;
                case 29: //搬迁地点
                    p.setBqdd(val); break;
                case 30: //搬迁时间
                    p.setBqsj(val); break;
                case 31: //备注
                    p.setBz(val); break;
                case 32: //类型：卡户/随迁户
                    p.setLx(val); break;
            }
            index++;
        }
        return p;
    }

    /** 将一行数据转换成一个对象 */
    public static Personal buildPersonal0419(Row row) {
        Personal p = new Personal();
        int index = 0;
        for(Cell cell : row) {
            String val = getBaseCellValue(cell, true);
            switch (index) {
                case 0: //序号
                    p.setXh(buildInteger(val)); break;
                case 1: //姓名
                    p.setXm(val); break;
                case 2: //家庭人数
                    p.setJtrs(buildInteger(val)); break;
                case 3: //姓名
                    p.setXm(val); break;
                case 4: //与户主关系
                    p.setYhzgx(val); break;
                case 5: //身份证号
                    val = val.length()>=18?val.substring(0, 18):val; //处理身份证号
                    p.setSfzh(val); break;
                case 6: //性别
                    p.setXb(val); break;
                case 7: //民族
                    p.setMz(val); break;
                case 8: //年龄
                    p.setNl(buildInteger(val)); break;
                case 9: //是否是劳动力
                    p.setSfsldl(val); break;
                case 10: //家庭地址
                    p.setJtdz(val); break;
                case 11: //脱贫属性
                    p.setPksx(val); break;
                case 12: //参加过何种培训
                    p.setCjhzpx(val); break;
                case 13: //文化程度
                    p.setWhcd(val); break;

                case 14: //务工地点
                    p.setWgdd(val); break;
                case 15: //企业名称
                    p.setQymc(val); break;
                case 16: //岗位名称
                    p.setGwmc(val); break;
                case 17: //务工时间
                    p.setWgsj(val); break;
                case 18: //月工资收入
                    p.setYgz(buildFloat(val)); break;
                case 19: //创业项目
                    p.setCyxm(val); break;
                case 20: //创业地点
                    p.setCydd(val); break;
                case 21: //创业时间
                    p.setCysj(val); break;
                case 22: //月收入
                    p.setYsr(buildFloat(val)); break;
                case 23: //务工去向
                    p.setWgqx(val); break;
                case 24: //公益性岗位
                    p.setGyxgw(val); break;
                case 25: //自主创业
                    p.setZzcy(val); break;
                case 26: //无法外出原因
                    p.setWfwcyy(val); break;
                case 27: //培训需求
                    p.setPxxq(val); break;
                case 28: //联系电话
                    p.setLxdh(val); break;
                case 29: //搬迁地点
                    p.setBqdd(val); break;
                case 30: //搬迁时间
                    p.setBqsj(val); break;
                case 31: //备注
                    p.setBz(val); break;
                case 32: //类型：卡户/随迁户
                    p.setLx(val); break;
            }
            index++;
        }
        return p;
    }

    /** 将一行数据转换成一个对象 */
    public static Personal buildPersonal0602(Row row, boolean isStr) {
        Personal p = new Personal();
        int index = 0;
        for(Cell cell : row) {
            String val = getBaseCellValue(cell, isStr);
            switch (index) {
                case 0: //序号
                    p.setXh(buildInteger(val)); break;
                case 2: //乡镇
                    p.setXzmc(val); break;
                case 3: //乡村
                    p.setCzmc(val); break;
                case 4: //自然村
                    p.setZrc(val); break;
                case 5: //户编号
                    p.setHbh(val); break;
                case 6: //姓名
                    p.setXm(val); break;
                case 7: //身份证号
                    val = val.length()>=18?val.substring(0, 18):val; //处理身份证号
                    p.setSfzh(val); break;
                case 8: //家庭人数
                    p.setJtrs(buildInteger(val)); break;
                case 9: //与户主关系
                    p.setYhzgx(val); break;
                case 10: //民族
                    p.setMz(val); break;
                case 11: //文化程度
                    p.setWhcd(val); break;
                case 12: //国办劳动力情况
                    p.setGbldlqk(val); break;
                case 13: //是否是劳动力
                    p.setSfsldl(val); break;
                case 14: //贫困属性
                    p.setPksx(val); break;
                case 15: //是否在校
                    p.setSfzx(val); break;
                case 16: //就读学校
                    p.setJdxx(val); break;
                case 17: //就读年级
                    p.setJdnj(val); break;
                case 18: //是否享受资助
                    p.setSfxszz(val); break;
                case 19: //资助项目
                    p.setZzxm(val); break;
                case 20: //资助金额
                    p.setZzje(buildFloat(val)); break;
                case 21: //健康状况
                    p.setJkzk(val); break;
                case 22: //是否养老保险
                    p.setSfylbx(val); break;
                case 23: //是否医疗保险
                    p.setSfyb(val); break;
                case 24: //参保险种
                    p.setCbxz(val); break;
                case 25: //参保单位
                    p.setCbdw(val); break;
                case 26: //是否患病
                    p.setSfhb(val); break;
                case 27: //参加过何种培训
                    p.setCjhzpx(val); break;
                case 28: //务工地点
                    p.setWgdd(val); break;
                case 29: //企业名称
                    p.setQymc(val); break;
                case 30: //岗位名称
                    p.setGwmc(val); break;
                case 31: //务工时间
                    p.setWgsj(val); break;
                case 32: //月工资收入
                    p.setYgz(buildFloat(val)); break;
                case 33: //创业项目
                    p.setCyxm(val); break;
                case 34: //创业地点
                    p.setCydd(val); break;
                case 35: //创业时间
                    p.setCysj(val); break;
                case 36: //月收入
                    p.setYsr(buildFloat(val)); break;
                case 37: //务工去向
                    p.setWgqx(val); break;
                case 38: //公益性岗位
                    p.setGyxgw(val); break;
                case 39: //自主创业
                    p.setZzcy(val); break;
                case 40: //无法外出原因
                    p.setWfwcyy(val); break;
                case 41: //培训需求
                    p.setPxxq(val); break;
                //42 - 47：三块地和产业
                case 48: //联系电话
                    p.setLxdh(val); break;
                case 49: //致贫原因
                    p.setZpyy(val); break;
                case 50: //搬迁时间
                    p.setBqsj(val); break;
                case 51: //人口自然增减
                    p.setRkzrzj(val); break;
                case 52: //搬迁地点
                    p.setBqdd(val); break;
                case 53: //安置方式
                    p.setAzfs(val); break;
                case 54: //备注
                    p.setBz(val); break;
            }
            index++;
        }
        return p;
    }

    public static List<Personal> buildByExcel(InputStream is, Integer beginLine, Integer sheetNum) {
        beginLine=beginLine==null?0:beginLine;
        sheetNum = sheetNum==null?0:sheetNum;
        List<Personal> result = new ArrayList<>();
//        Workbook wb = null;
        try {
            Workbook wb = WorkbookFactory.create(is);
            Sheet s = wb.getSheetAt(sheetNum);
            for(int i=beginLine;i<=s.getLastRowNum();i++) {
                Row row = s.getRow(i);
                Personal p = buildPersonal(row);
                result.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if(is!=null) {is.close();}
            } catch (IOException e) {
            }
        }
        return result;
    }

    public static String getBaseCellValue(Cell cell, boolean isStr) {
        String res = null;
        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    res = ""; break;
                case Cell.CELL_TYPE_BOOLEAN:
                    res = String.valueOf(cell.getBooleanCellValue()); break;
                case Cell.CELL_TYPE_FORMULA:
                    if(isStr) {
                        res = String.valueOf(cell.getStringCellValue()); break;
                    } else {
                        res = String.valueOf(cell.getCellFormula()); break;
                    }
//                    res = String.valueOf(cell.getNumericCellValue()); break;
                case Cell.CELL_TYPE_NUMERIC:
                    res = String.valueOf(cell.getNumericCellValue()); break;
                case Cell.CELL_TYPE_STRING:
                    res = cell.getStringCellValue(); break;
                default:
                    res = null; break;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(cell.getRowIndex()+"===="+cell.getCellType()+"=="+cell.getColumnIndex()+"==="+e.getMessage());
        }
        return res;
    }

    public static String getBaseCellValue(Cell cell) {
        return getBaseCellValue(cell, false);
    }

    /** 将一行数据转换成一个对象 */
    public static Personal buildPersonal(Row row) {
        Personal p = new Personal();
        int index = 0;
        for(Cell cell : row) {
            String val = getBaseCellValue(cell);
            switch (index) {
                case 0: //序号
                    p.setXh(buildInteger(val)); break;
                case 1: //姓名
                    p.setXm(val); break;
                case 2: //家庭人数
                    p.setJtrs(buildInteger(val)); break;
                case 3: //姓名
                    p.setXm(val); break;
                case 4: //与户主关系
                    p.setYhzgx(val); break;
                case 5: //身份证号
                    val = val.length()>=18?val.substring(0, 18):val; //处理身份证号
                    p.setSfzh(val); break;
                case 6: //性别
                    p.setXb(val); break;
                case 7: //民族
                    p.setMz(val); break;
                case 8: //年龄
                    p.setNl(buildInteger(val)); break;
                case 9: //家庭地址
                    p.setJtdz(val); break;
                case 10: //脱贫属性
                    p.setPksx(val); break;
                case 11: //易迁户属性
                    p.setYqhsx(val); break;
                case 12: //是否是劳动力
                    p.setSfsldl(val); break;
                case 13: //文化程度
                    p.setWhcd(val); break;
                case 14: //是否在校
                    p.setSfzx(val); break;
                case 15: //就读学校
                    p.setJdxx(val); break;
                case 16: //就读年级
                    p.setJdnj(val); break;
                case 17: //是否享受资助
                    p.setSfxszz(val); break;
                case 18: //资助项目
                    p.setZzxm(val); break;
                case 19: //资助金额
                    p.setZzje(buildFloat(val)); break;
                case 20: //健康状况
                    p.setJkzk(val); break;
                case 21: //养老保险
                    p.setSfylbx(val); break;
                case 22: //医院保险
                    p.setSfylbx(val); break;
                case 23: //参保险种
                    p.setCbxz(val); break;
                case 24: //参保单位
                    p.setCbdw(val); break;
                case 25: //是否患病
                    p.setSfhb(val); break;
                case 26: //参加过何种培训
                    p.setCjhzpx(val); break;
                case 27: //务工地域
                    p.setWgdy(val); break;
                case 28: //务工地点
                    p.setWgdd(val); break;
                case 29: //企业名称
                    p.setQymc(val); break;
                case 30: //岗位名称
                    p.setGwmc(val); break;
                case 31: //务工时间
                    p.setWgsj(val); break;
                case 32: //月工资收入
                    p.setYgz(buildFloat(val)); break;
                case 33: //创业项目
                    p.setCyxm(val); break;
                case 34: //创业地点
                    p.setCydd(val); break;
                case 35: //创业时间
                    p.setCysj(val); break;
                case 36: //月收入
                    p.setYsr(buildFloat(val)); break;
                case 37: //务工去向
                    p.setWgqx(val); break;
                case 38: //公益性岗位
                    p.setGyxgw(val); break;
                case 39: //自主创业
                    p.setZzcy(val); break;
                case 40: //无法外出原因
                    p.setWfwcyy(val); break;
                case 41: //培训需求
                    p.setPxxq(val); break;
                case 42: //宅基地
                    p.setZjd(buildFloat(val)); break;
                case 43: //耕地
                    p.setGd(buildFloat(val)); break;
                case 44: //种植品种
                    p.setZzpz(val); break;
                case 45: //种植面积
                    p.setZzdmj(buildFloat(val)); break;
                case 46: //联系电话
                    p.setLxdh(val); break;
                case 47: //搬迁地点
                    p.setBqdd(val); break;
                case 48: //搬迁时间
                    p.setBqsj(val); break;
                case 49: //备注
                    p.setBz(val); break;
            }
            index++;
        }
        return p;
    }

    /** 判断是否为身份证号 */
    public static boolean isCard(String id) {
        if(id==null || id.length()!=18) {return false;}
         int[] w = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        char[] c=id.toCharArray();
        int sum=0;
        for (int i = 0; i < w.length; i++) {
            sum+=(c[i]-'0')*w[i];
        }
//        System.out.println(sum);
        char[] verifyCode="10X98765432".toCharArray();
        char ch =verifyCode[sum%11];
//        System.out.println(ch);
        return c[17]==ch;

    }

    private static Integer buildInteger(String val) {
        Integer res = null;
        if(val!=null) {
            if(val.contains(".")) {
                try { res = Integer.parseInt(val.split("\\.")[0]); } catch (Exception e) { }
            } else {
                try { res = Integer.parseInt(val); } catch (Exception e) { }
            }
        }
        return res;
    }

    private static Float buildFloat(String val) {
        Float res = null;
        if(val!=null) {
            try { res = Float.parseFloat(val); } catch (Exception e) { }
        }
        return res;
    }
}
