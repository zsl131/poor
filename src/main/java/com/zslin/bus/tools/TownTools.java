package com.zslin.bus.tools;

import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.dao.IUserTownDao;
import com.zslin.bus.dto.TownDto;
import com.zslin.bus.model.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsl on 2019/4/8.
 */
@Component
public class TownTools {

    @Autowired
    private ITownDao townDao;

    @Autowired
    private IUserTownDao userTownDao;

    public static Integer buildBqddid(String bqdd) {
        //17:昭阳区靖安易迁安置点 - 靖安易迁安置点
        //231:彝良县发界安置点 - 发界安置点
        //234:鲁甸县卯家湾易迁安置点 - 卯家湾
        if(bqdd==null) { return null; }
        else if(bqdd.contains("靖安易迁安置点")) {return 17;}
        else if(bqdd.contains("发界安置点")) {return 231;}
        else if(bqdd.contains("卯家湾")) {return 234;}

        return null;
    }

    public List<TownDto> buildTown() {
        List<TownDto> result = new ArrayList<>();
        Sort sort = SimpleSortBuilder.generateSort("orderNo");
        List<Town> list = townDao.findParent(sort);
        for(Town d : list) {
            if(d.getId()!=1) { //去除ID为1的彝良县
                List<Town> children = townDao.findByPid(d.getId(), sort);
                result.add(new TownDto(d, children));
            }
        }
        return result;
    }

    /**
     * 构建当前访问的乡镇
     * @param params 请求参数
     * @param townId 请求的乡镇ID
     * @return
     */
    public Town buildCurrentTown(String params, Integer townId) {
        Town t = null;
        //try { townId = Integer.parseInt(JsonTools.getJsonParam(params, "townId")); } catch (Exception e) { townId = 0; }
        if(townId==null || townId<=0) {
            String username = JsonTools.getHeaderParams(params, "username");
            try { townId = userTownDao.findTownId(username).get(0); } catch (Exception e) { townId = 0; }
        }
        if(townId>0) {
            t = townDao.findOne(townId);
        }
        return t;
    }
}
