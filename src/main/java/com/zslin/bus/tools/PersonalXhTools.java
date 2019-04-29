package com.zslin.bus.tools;

import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.model.Personal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 处理人员序号的工具类
 * Created by zsl on 2019/4/28.
 */
@Component
public class PersonalXhTools {

    @Autowired
    private IPersonalDao personalDao;

    public void resetXh() {
        List<Personal> list = personalDao.findNoXh();
        String hzsfzh = "";
        Integer xh= 0;
        for(Personal p : list) {
            if(!hzsfzh.equals(p.getHzsfzh())) {
                hzsfzh = p.getHzsfzh();
                xh = personalDao.findBySfzh(hzsfzh).getXh();
            }
            personalDao.updateXh(xh, p.getSfzh());
        }
    }
}
