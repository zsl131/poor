package com.zslin.basic.tools.login;

import com.zslin.basic.model.Menu;
import com.zslin.basic.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsl on 2018/7/13.
 */
public class LoginDto {

    private User user;

    private List<MenuDto> navMenus;

    private List<Menu> authMenus;

    public LoginDto() {
        navMenus = new ArrayList<>();
        authMenus = new ArrayList<>();
    }

    public LoginDto(List<MenuDto> navMenus, List<Menu> authMenus) {
        this.navMenus = navMenus;
        this.authMenus = authMenus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<MenuDto> getNavMenus() {
        return navMenus;
    }

    public void setNavMenus(List<MenuDto> navMenus) {
        this.navMenus = navMenus;
    }

    public List<Menu> getAuthMenus() {
        return authMenus;
    }

    public void setAuthMenus(List<Menu> authMenus) {
        this.authMenus = authMenus;
    }
}
