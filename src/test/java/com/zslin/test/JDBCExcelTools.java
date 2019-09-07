package com.zslin.test;

import com.zslin.bus.model.Personal;
import com.zslin.bus.tools.ExcelBasicTools;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * Created by zsl on 2019/4/28.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class JDBCExcelTools {

    /** 重新计算各户种植地面积 */
    @Test
    public void test02() {
        Connection con = getCon(false); //TODO 选择连接方式
        processArea(con);
    }

    /** 处理面积 */
    private void processArea(Connection con) {
        try {

            PreparedStatement ps = con.prepareStatement("SELECT hzsfzh, SUM(zzmj) FROM t_family_plant GROUP BY hzsfzh");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String hzsfzh = rs.getString(1);
                Float zzmj = rs.getFloat(2);
//                System.out.println("身份证号："+hzsfzh +", "+ "面积："+zzmj);

                Statement statement = con.createStatement();
                statement.executeUpdate("UPDATE t_family o SET o.zzdmj="+zzmj +" WHERE o.sfzh='"+hzsfzh+"'");
            }

            /*//2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            //3.ResultSet类，用来存放获取的结果集！！
            Integer amount = statement.executeUpdate("UPDATE t_family o SET o.ktgmj="+ktgmj +", o.ld="+ldmj+", o.zjd="+zjdmj+", o.gd="+gdmj+" WHERE o.sfzh='"+sfzh+"'");
            Integer amount2 = statement.executeUpdate("UPDATE t_personal o SET o.ktgmj="+ktgmj +", o.ld="+ldmj+", o.zjd="+zjdmj+", o.gd="+gdmj+" WHERE o.hzsfzh='"+sfzh+"'");
            String insertSql = "insert into t_family_plant(hzsfzh, hzxm, zzmj, zzpzdm, zzpzmc) values(?,?,?,?,?)";*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 处理三块地 */
    @Test
    public void test01() {
        try {
            Integer amount = 0;
            Long start = System.currentTimeMillis();
            String excelFile = "D:/temp/sankuaidi.xlsx";
            Connection con = getCon(true); //TODO 选择连接方式
            FileInputStream fis = new FileInputStream(excelFile);
            try {
                Workbook wb = WorkbookFactory.create(fis);
                Sheet s = wb.getSheetAt(0);
                System.out.println("=====共【"+s.getLastRowNum()+"】行数据");
                for(int i=1;i<=s.getLastRowNum();i++) {
                    Row row = s.getRow(i);
                    String xm = "";
                    try {
                        xm = ExcelBasicTools.getBaseCellValue(row.getCell(3)); //姓名
                    } catch (Exception e) {
                    }
                    String sfzh = "";
                    try {
                        sfzh = ExcelBasicTools.getBaseCellValue(row.getCell(4)); //身份证号
                        sfzh = sfzh.length()>=18?sfzh.substring(0, 18):sfzh; //处理身份证号
                    } catch (Exception e) {
                    }
                    Float gdmj = getValue(row, 6);
                    Float ldmj = getValue(row, 7);
                    Float zjdmj = getValue(row, 8);
                    Float ktgmj = getValue(row ,9);

                    Float mj1 = getValue(row, 12);
                    Float mj2 = getValue(row ,13);
                    Float mj3 = getValue(row, 14);
                    Float mj4 = getValue(row, 15);

                    Float mj5 = getValue(row, 16);
                    Float mj6 = getValue(row, 17);
                    Float mj7 = getValue(row, 18);
                    Float mj8 = getValue(row, 19);
                    Float mj9 = getValue(row, 20);
                    Float mj10 = getValue(row, 21);
                    Float mj11 = getValue(row, 22);
                    Float mj12 = getValue(row, 23);
                    Float mj13 = getValue(row, 24);
                    Float mj14 = getValue(row, 25);
                    Float mj15 = getValue(row, 26);
                    Float mj30 = getValue(row, 27);

                    process(con, sfzh, xm, gdmj, ldmj, zjdmj, ktgmj, mj1, mj2, mj3, mj4, mj5, mj6, mj7, mj8, mj9, mj10, mj11, mj12, mj13, mj14, mj15, mj30);
//                    System.out.println(sfzh+"处理完成= mj1:"+mj1+",mj2:"+mj2+",mj3:"+mj3+",mj4:"+mj4+",mj5:"+mj5+",mj6:"+mj6+",mj7:"+mj7+",mj8:"+mj8+",mj9:"+mj9+
//                            ",mj10:"+mj10+",mj11:"+mj11+",mj12:"+mj12+",mj13:"+mj13+",mj14:"+mj14+",mj15:"+mj15+",mj30:"+mj30);
                    amount ++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Long end = System.currentTimeMillis();
            System.out.println("花时间："+((end-start)/1000) + " 秒。处理："+amount+" 条");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Float getValue(Row row, Integer cellIndex) {
        try {
            String val = ExcelBasicTools.getBaseCellValue(row.getCell(cellIndex), true);
            Float f = Float.parseFloat(val);
            return f;
        } catch (Exception e) {
            return 0f;
//            e.printStackTrace();
        }
    }

    private static JDBCPOJO getLocal() {
        JDBCPOJO res = new JDBCPOJO("jdbc:mysql://localhost:3306/poor", "root", "123");
        return res;
    }

    private static JDBCPOJO getRemote() {
//        JDBCPOJO res = new JDBCPOJO("jdbc:mysql://121.40.210.94:13326/poor", "root", "ynzslzsl**");
        JDBCPOJO res = new JDBCPOJO("jdbc:mysql://123.58.6.13:13326/poor", "root", "ynzslzsl**");
        return res;
    }

    /**
     * 获取数据库连接
     * @param isLocal 是否获取本地连接
     * @return
     */
    private static Connection getCon(boolean isLocal) {
        Connection con = null;
        try {
            //驱动程序名
            String driver = "com.mysql.jdbc.Driver";
            //加载驱动程序
            Class.forName(driver);
            JDBCPOJO jdbc = isLocal?getLocal():getRemote();
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(jdbc.getUrl(),jdbc.getUser(),jdbc.getPwd());
        } catch(ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            System.out.println("数据库数据成功获取！！");
        }
        return con;
    }

    private void process(Connection con, String sfzh, String xm, Float gdmj, Float ldmj, Float zjdmj, Float ktgmj,
                         Float mj1, Float mj2, Float mj3, Float mj4, Float mj5, Float mj6, Float mj7, Float mj8, Float mj9,
                         Float mj10, Float mj11, Float mj12, Float mj13, Float mj14, Float mj15, Float mj30) {
        try {
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            //3.ResultSet类，用来存放获取的结果集！！
            Integer amount = statement.executeUpdate("UPDATE t_family o SET o.ktgmj="+ktgmj +", o.ld="+ldmj+", o.zjd="+zjdmj+", o.gd="+gdmj+" WHERE o.sfzh='"+sfzh+"'");
            Integer amount2 = statement.executeUpdate("UPDATE t_personal o SET o.ktgmj="+ktgmj +", o.ld="+ldmj+", o.zjd="+zjdmj+", o.gd="+gdmj+" WHERE o.hzsfzh='"+sfzh+"'");
            String insertSql = "insert into t_family_plant(hzsfzh, hzxm, zzmj, zzpzdm, zzpzmc) values(?,?,?,?,?)";
            if(mj1>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj1);
                pstmt.setString(4, "1");
                pstmt.setString(5, "方竹");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }

            if(mj2>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj2);
                pstmt.setString(4, "2");
                pstmt.setString(5, "筇竹");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj3>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj3);
                pstmt.setString(4, "3");
                pstmt.setString(5, "青花椒");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj4>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj4);
                pstmt.setString(4, "4");
                pstmt.setString(5, "贡椒（大红袍）");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj5>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj5);
                pstmt.setString(4, "5");
                pstmt.setString(5, "核桃");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj6>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj6);
                pstmt.setString(4, "6");
                pstmt.setString(5, "板栗");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj7>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj7);
                pstmt.setString(4, "7");
                pstmt.setString(5, "茶叶");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj8>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj8);
                pstmt.setString(4, "8");
                pstmt.setString(5, "桃子");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj9>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj9);
                pstmt.setString(4, "9");
                pstmt.setString(5, "李子");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj10>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj10);
                pstmt.setString(4, "10");
                pstmt.setString(5, "梨子");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj11>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj11);
                pstmt.setString(4, "11");
                pstmt.setString(5, "樱桃");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj12>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj12);
                pstmt.setString(4, "12");
                pstmt.setString(5, "柑桔");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj13>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj13);
                pstmt.setString(4, "13");
                pstmt.setString(5, "杉树");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj14>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj14);
                pstmt.setString(4, "14");
                pstmt.setString(5, "退耕还草");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj15>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj15);
                pstmt.setString(4, "15");
                pstmt.setString(5, "天麻菌材林");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
            if(mj30>0) {
                PreparedStatement pstmt=con.prepareStatement(insertSql);//获得预置对象
                pstmt.setString(1, sfzh);
                pstmt.setString(2, xm);
                pstmt.setFloat(3, mj30);
                pstmt.setString(4, "30");
                pstmt.setString(5, "其他");
                pstmt.executeUpdate();
                pstmt.close();//关闭资源
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateXh(Integer xh, String sfzh) {
        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://121.40.210.94:13326/poor";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "ynzslzsl**";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            String sql = "select * from t_town";
            //3.ResultSet类，用来存放获取的结果集！！
            Integer amount = statement.executeUpdate("UPDATE t_personal p SET p.xh="+xh +" WHERE p.sfzh='"+sfzh+"'");
            System.out.println(xh+"==="+sfzh+"==="+amount);
            con.close();
        } catch(ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            System.out.println("数据库数据成功获取！！");
        }
    }
}
