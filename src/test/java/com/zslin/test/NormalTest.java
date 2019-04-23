package com.zslin.test;

import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.dto.PieDto;
import com.zslin.bus.model.Personal;
import com.zslin.bus.model.Town;
import com.zslin.bus.tools.ExcelBasicTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by zsl on 2019/4/15.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class NormalTest {

    @Autowired
    private ITownDao townDao;

    @Autowired
    private IPersonalDao personalDao;

    /*@Test
    public void test04() {
        Integer res = personalDao.test("530628200105291721", null);
        System.out.println("res:::"+res);
    }*/

    @Test
    public void test03() {
        List<PieDto> list = personalDao.findPieByXb();
        for(PieDto pd : list) {
            System.out.println(pd);
        }
    }

    @Test
    public void test02() {
        List<Personal> list = personalDao.findAll();
        Integer total = 0, error = 0;
        for(Personal p : list){
            String sfzh = p.getSfzh();
            if(!ExcelBasicTools.isCard(sfzh)) {
                System.out.println("=="+sfzh);
                error ++;
            }
            total++;
        }
        System.out.println("total::"+total+", error::"+error);
    }

    @Test
    public void test01() {
        Town t = townDao.findByNameLike("海子");
        System.out.println(t);
        System.out.println(t.getName()+"=="+t.getLevel());
    }
}
