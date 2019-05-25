package com.lihebin.manage.service;

import com.lihebin.manage.bean.Login;
import com.lihebin.manage.bean.LoginRes;

/**
 * Created by lihebin on 2019/4/29.
 */
public interface MerchantUserService {

    /**
     * 登录
     *
     * @param login
     * @return
     */
    LoginRes login(Login login);

    /**
     * 登出
     *
     * @param token
     */
    void logout(String token);
}
