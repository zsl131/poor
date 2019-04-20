package com.zslin.bus.service;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.bus.common.dto.QueryListDto;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.common.tools.QueryTools;
import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.model.Family;
import com.zslin.bus.model.Personal;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2019/4/17.
 */
@Service
@AdminAuth(psn = "业务管理", name = "易迁人员管理", orderNum = 1, type = "1", url = "/admin/personal")
public class PersonalService {

    @Autowired
    private IFamilyDao familyDao;

    @Autowired
    private IPersonalDao personalDao;

    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<Personal> res = personalDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));
        return JsonResult.success().set("size", (int)res.getTotalElements()).set("data", res.getContent());
    }

    /** 获取家庭信息 */
    public JsonResult onShow(String params) {
        JsonResult result = JsonResult.getInstance();
        String type = JsonTools.getJsonParam(params, "type"); //类型，f-family，p-personal
        Integer id = JsonTools.getId(params); //ID
        Family f = null;
        if("f".equals(type)) {
            f = familyDao.findOne(id);
        } else if("p".equals(type)) {
            Personal p = personalDao.findOne(id);
            f = familyDao.findBySfzh(p.getHzsfzh());
            result.set("personal", p);
        }
        if(f!=null) {
            List<Personal> personalList = personalDao.findByHzsfzh(f.getSfzh());
            result.set("personalList", personalList);
        }
        result.set("family", f);

        return result;
    }

    public JsonResult loadOne(String params) {
        Integer id = JsonTools.getId(params);
        Personal p = personalDao.findOne(id);
        return JsonResult.getInstance().set("personal", p);
    }
}
