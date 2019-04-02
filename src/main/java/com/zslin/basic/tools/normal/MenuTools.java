package com.zslin.basic.tools.normal;

import com.zslin.basic.dao.IMenuDao;
import com.zslin.basic.dto.MenuTreeDto;
import com.zslin.basic.model.Menu;
import com.zslin.basic.repository.SimpleSortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsl on 2018/7/15.
 */
@Component
public class MenuTools {

    @Autowired
    private IMenuDao menuDao;

    public List<MenuTreeDto> buildMenuTree() {
        List<MenuTreeDto> result = new ArrayList<>();
        Sort sort = SimpleSortBuilder.generateSort("orderNum_a");
        List<Menu> rootList = menuDao.findRootMenu(sort);
        for(Menu m : rootList) {
            List<Menu> children = menuDao.findByParent(m.getId(), sort);
            result.add(new MenuTreeDto(m, children));
        }
        return result;
    }
}
