package com.zslin.test;

import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.model.Personal;
import com.zslin.bus.poi.model.User;
import com.zslin.bus.poi.util.ExcelUtil;
import com.zslin.bus.tools.ExportTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zsl on 2019/4/15.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class ExportPersonalTest {

    @Autowired
    private ExportTools exportTools;

    @Test
    public void test01() throws Exception {
        List<Personal> data = new ArrayList<>();
        FileOutputStream fos = new FileOutputStream(new File("D:/temp/poor-export.xlsx"));
        exportTools.exportData("角奎镇", data, fos);
    }

    @Test
    public void testObj2Xls() {
        //基于user.xls模板的导出，user.xls是在classpath路径下
        List<User> users = new ArrayList<User>();
        users.add(new User(1,"aaa","水水水",11,"222",323));
        users.add(new User(2,"sdf","水水水",11,"222",323));
        users.add(new User(3,"sdfde","水水水",11,"222",323));
        users.add(new User(4,"aaa","水水水",11,"222",323));
        users.add(new User(54,"aaa","水水水",11,"222",323));
        users.add(new User(16,"aaa","水水水",11,"222",323));
        //基于user.xls模板的导出，最后的true表示，user.xls是在classpath路径下，第一个参数表示模板中要替换的值
        ExcelUtil.getInstance().exportObj2ExcelByTemplate(new HashMap<String, String>(),
                "/excel/user.xls",
                "d:/temp/tus.xls",
                users,
                User.class, true);
    }

    @Autowired
    private IPersonalDao personalDao;

    @Test
    public void test02() {
        List<Personal> list = personalDao.findAll();
        Map<String, String> map = new HashMap<>();
        map.put("title", "花名册");
        ExcelUtil.getInstance().exportObj2ExcelByTemplate(map,
                "/excel/personal.xls",
                "d:/temp/p_export.xls",
                list,
                Personal.class, true);
    }
}
