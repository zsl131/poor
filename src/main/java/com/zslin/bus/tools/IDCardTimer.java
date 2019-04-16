package com.zslin.bus.tools;

import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.model.Personal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zsl on 2019/4/16.
 */
@Component
public class IDCardTimer {

    @Autowired
    private IPersonalDao personalDao;

    private static int CUR_YEAR;
    private static int CUR_MONTH;
    private static int CUR_DAY;

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dayStr = sdf.format(new Date());
        CUR_YEAR = Integer.parseInt(dayStr.substring(0, 4));
        CUR_MONTH = Integer.parseInt(dayStr.substring(4, 6));
        CUR_DAY = Integer.parseInt(dayStr.substring(6, 8));
    }

    /** 每天早上1点执行，重新执行年龄 */
    @Scheduled(cron = "0 0 1 * * ?")
    public void resetAge() {
        int id = 0; Integer size = null;
        Sort sort = SimpleSortBuilder.generateSort("id_asc");
        while(size==null || size>0) {
            List<Personal> list = personalDao.findById(id, sort);
            size = list.size();
            if (list != null && list.size() > 0) {
                for (Personal p : list) {
                    Integer age = buildAge(p.getSfzh());
                    if (age != null) {
                        personalDao.updateNl(p.getSfzh(), age);
                    } //设置年龄
                    id = p.getId();
                }
            }
        }
    }

    /** 根据身份证号获取年龄 */
    private Integer buildAge(String sfzh) {
        try {
            Integer year = Integer.parseInt(sfzh.substring(6, 10));
            Integer month = Integer.parseInt(sfzh.substring(10, 12));
            Integer day = Integer.parseInt(sfzh.substring(12, 14));
            Integer age = CUR_YEAR - year;
            if(CUR_MONTH==month && CUR_DAY<day) {
                age = age - 1;
            } else if(CUR_MONTH<month) {age = age - 1;}
            return age;
        } catch (Exception e) {
        }
        return null;
    }
}
