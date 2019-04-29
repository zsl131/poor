package com.zslin.bus.tools;

import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.bus.dao.ITownDao;
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
}
