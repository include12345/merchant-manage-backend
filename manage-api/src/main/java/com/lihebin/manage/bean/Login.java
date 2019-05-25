package com.lihebin.manage.bean;

import javax.validation.constraints.NotNull;

/**
 * Created by lihebin on 2019/4/16.
 */
public class Login {

    @NotNull
    private String username;

    @NotNull
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
