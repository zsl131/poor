package com.zslin.test;

import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.dto.PieDto;
import com.zslin.bus.model.Personal;
import com.zslin.bus.model.Town;
import com.zslin.bus.tools.ExcelBasicTools;
import com.zslin.bus.tools.PersonalTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
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

    @Autowired
    private IFamilyDao familyDao;

    @Test
    public void test07() {
        String str = "1.3248582209E10";
        DecimalFormat df = new DecimalFormat("#");
        System.out.println(df.format(Double.parseDouble(str)));
    }

    @Test
    public void test06() {
        List<Personal> list = personalDao.findAll();
        for(Personal p : list) {
            personalDao.updateJylx(PersonalTools.buildJyLx(p), p.getSfzh());
        }
    }

    @Test
    public void test05() {
        familyDao.findAll();
    }

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
