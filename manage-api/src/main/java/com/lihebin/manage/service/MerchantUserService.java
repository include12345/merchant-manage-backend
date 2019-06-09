package com.lihebin.manage.service;

import com.lihebin.manage.bean.Login;
import com.lihebin.manage.bean.LoginRes;
import com.lihebin.manage.bean.UserMessage;

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

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    UserMessage getUserMessage(String token);
}
