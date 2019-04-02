package com.zslin.basic.dto;

import com.zslin.basic.model.Menu;

import java.util.List;

/**
 * Created by zsl on 2018/7/15.
 */
public class MenuTreeDto {

    private Menu menu;

    private List<Menu> children;

    public MenuTreeDto() {
    }

    public MenuTreeDto(Menu menu, List<Menu> children) {
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
