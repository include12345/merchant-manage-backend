package com.lihebin.manage.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by lihebin on 2019/5/27.
 */
public class JWTToken implements AuthenticationToken {
    private String token;

    public JWTToken(String token) {

        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
