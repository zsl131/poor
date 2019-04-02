package com.zslin.basic.tools.login;

import com.zslin.basic.dao.IMenuDao;
import com.zslin.basic.model.Menu;
import com.zslin.basic.repository.SimpleSortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsl on 2018/7/13.
 */
@Component
public class LoginTools {

    @Autowired
    private IMenuDao menuDao;

    public LoginDto buildAuthMenus(Integer userId) {
        List<Menu> rootMenuList = menuDao.findRootByUser(userId, SimpleSortBuilder.generateSort("orderNum_a"));
        List<MenuDto> navMenuDtoList = new ArrayList<>();
        for(Menu rootMenu : rootMenuList) { //root
            List<Menu> secondMenuList = menuDao.findByUser(userId, rootMenu.getSn(), SimpleSortBuilder.generateSort("orderNum_a"));
            navMenuDtoList.add(new MenuDto(rootMenu, secondMenuList));
        }

        List<Menu> authMenuList = menuDao.findAuthMenuByUser(userId);

        return new LoginDto(navMenuDtoList, authMenuList);
    }
}
