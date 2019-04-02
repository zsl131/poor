package com.zslin.basic.dto;

import com.zslin.basic.model.Role;

import java.util.List;

/**
 * Created by zsl on 2018/7/15.
 */
public class AuthRoleDto {

    private List<Integer> authIds;

    private List<Role> roleList;

    public AuthRoleDto() {
    }

    public AuthRoleDto(List<Integer> authIds, List<Role> roleList) {
        this.authIds = authIds;
        this.roleList = roleList;
    }

    public List<Integer> getAuthIds() {
        return authIds;
    }

    public void setAuthIds(List<Integer> authIds) {
        this.authIds = authIds;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
