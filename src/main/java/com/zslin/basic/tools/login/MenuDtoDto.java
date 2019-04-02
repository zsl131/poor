package com.zslin.basic.tools.login;

import com.zslin.basic.model.Menu;

import java.util.List;

/**
 * Created by zsl on 2018/7/13.
 */
public class MenuDtoDto {

    private Menu menu;
    private List<MenuDto> children;

    public MenuDtoDto(Menu menu, List<MenuDto> children) {
        this.menu = menu;
        this.children = children;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<MenuDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDto> children) {
        this.children = children;
    }
}
