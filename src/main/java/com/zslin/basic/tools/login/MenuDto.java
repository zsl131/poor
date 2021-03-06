package com.zslin.basic.tools.login;

import com.zslin.basic.model.Menu;

import java.util.List;

/**
 * Created by zsl on 2018/7/13.
 */
public class MenuDto {

    private Menu menu;

    private List<Menu> children;

    public MenuDto(Menu menu, List<Menu> children) {
        this.menu = menu;
        this.children = children;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}
