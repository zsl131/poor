package com.zslin.basic.dto;

import com.zslin.basic.model.Menu;

import java.util.List;

/**
 * Created by zsl on 2018/7/17.
 */
public class TreeRootDto {

    private List<MenuTreeDto> treeList;

    private List<Menu> menuList;

    public TreeRootDto() {
    }

    public TreeRootDto(List<MenuTreeDto> treeList, List<Menu> menuList) {
        this.treeList = treeList;
        this.menuList = menuList;
    }

    public List<MenuTreeDto> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<MenuTreeDto> treeList) {
        this.treeList = treeList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
