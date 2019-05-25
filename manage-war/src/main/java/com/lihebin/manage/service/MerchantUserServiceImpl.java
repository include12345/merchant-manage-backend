package com.lihebin.manage.service;

import com.lihebin.manage.bean.Code;
import com.lihebin.manage.bean.Login;
import com.lihebin.manage.bean.LoginRes;
import com.lihebin.manage.dao.manage.MerchantUserDao;
import com.lihebin.manage.exception.BackendException;
import com.lihebin.manage.model.MerchantUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by lihebin on 2019/4/29.
 */
@Service
public class MerchantUserServiceImpl implements MerchantUserService{

    @Value("${salt_password}")
    private String SALT;

    @Autowired
    private MerchantUserDao merchantUserDao;

    @Override
    public LoginRes login(Login login) {
        MerchantUser merchantUser = merchantUserDao.findMerchantUserByUsername(login.getUsername());
        if (merchantUser == null) {
            throw new BackendException(Code.CODE_NOT_EXIST, "用户名密码错误");
        }
        if (!login.getPassword().equals(merchantUser.getPassword())) {
            throw new BackendException(Code.CODE_NOT_EXIST, "用户名密码错误");
        }
        return null;
    }

    @Override
    public void logout(String token) {

    }
}
