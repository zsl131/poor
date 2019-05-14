package com.zslin.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.dao.IUserTownDao;
import com.zslin.bus.dto.PersonalCountDto;
import com.zslin.bus.dto.PieDto;
import com.zslin.bus.model.Personal;
import com.zslin.bus.model.Town;
import com.zslin.bus.tools.ExcelBasicTools;
import com.zslin.bus.tools.PersonalCountTools;
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

    @Autowired
    private IUserTownDao userTownDao;

    @Autowired
    private PersonalCountTools personalCountTools;

    @Test
    public void test10() {
        String params = "{\"zzpz_4\":4,\"zzpz_3\":3,\"zzpz_2\":2,\"zzpz_1\":1,\"zzpz_14\":14,\"headerParams\":{\"cookie\":\"Webstorm-2add6c74=f18bb877-a306-431a-a8db-a204040b153f\",\"isadminuser\":\"1\",\"userid\":\"1\",\"username\":\"root\"},\"zzpz_15\":15,\"zjd\":0,\"ld\":0,\"zzpz_8\":5,\"zzpz_7\":6,\"zzpz_6\":7,\"id\":1035,\"zzpz_5\":8,\"zzpz_12\":13,\"gd\":0,\"zzpz_13\":16,\"zzpz_10\":11,\"zzpz_9\":9,\"zzpz_11\":12}";
        JSONObject jsonObj = JSON.parseObject(params);
        for(String key : jsonObj.keySet()) {
            System.out.println(key);
        }
        Float f = jsonObj.getFloat("ktgmj");
        System.out.println(f);
    }

    @Test
    public void test09() {
        List<PersonalCountDto> list = personalCountTools.buildCountDto(3);
        for(PersonalCountDto dto : list) {
            System.out.println(dto);
        }
    }

    @Test
    public void test08() {
        userTownDao.deleteTownByUserId(2);
    }

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
