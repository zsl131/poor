package com.zslin.bus.dto;

import com.zslin.bus.model.Town;

import java.util.List;

/**
 * Created by zsl on 2019/4/29.
 */
public class TownDto {

    private Town town;

    private List<Town> children;

    public TownDto() {
    }

    public TownDto(Town town, List<Town> children) {
        this.town = town;
        this.children = children;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public List<Town> getChildren() {
        return children;
    }

    public void setChildren(List<Town> children) {
        this.children = children;
    }
}
