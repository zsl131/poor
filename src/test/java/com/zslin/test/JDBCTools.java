package com.zslin.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by zsl on 2019/4/28.
 */
public class JDBCTools {

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
