package com.lihebin.manage.service;

import com.lihebin.manage.bean.Code;
import com.lihebin.manage.bean.Login;
import com.lihebin.manage.bean.LoginRes;
import com.lihebin.manage.bean.UserMessage;
import com.lihebin.manage.dao.cache.RedisDao;
import com.lihebin.manage.dao.manage.MerchantUserDao;
import com.lihebin.manage.exception.BackendException;
import com.lihebin.manage.model.MerchantUser;
import com.lihebin.manage.utils.MD5Util;
import com.lihebin.manage.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by lihebin on 2019/4/29.
 */
@Service
public class MerchantUserServiceImpl implements MerchantUserService{

    @Value("${salt_password}")
    private String SALT;

    @Autowired
    private RedisDao redisDao;

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
        String uuid = UUID.randomUUID().toString();
        String con = login.getUsername().concat(SALT).concat(uuid);
        String sign = MD5Util.getSign(con);
        String value = String.format("%s-%d", login.getUsername(), merchantUser.getMerchant_id());
        redisDao.removeValue(sign);
        redisDao.cacheValue(sign, value, 60, TimeUnit.MINUTES);
        LoginRes loginRes = new LoginRes();
        loginRes.setToken(sign);
        loginRes.setMerchantId(merchantUser.getMerchant_id());
        loginRes.setType(merchantUser.getType());
        return loginRes;
    }

    @Override
    public void logout(String token) {
        boolean result = redisDao.removeValue(token);
        if (!result) {
            throw new BackendException(Code.CODE_NOT_EXIST, "退出失败");
        }
    }

    @Override
    public UserMessage getUserMessage(String token) {
        UserMessage userMessage = new UserMessage();
        String value = redisDao.getValue(token);
        if (StringUtil.empty(value)) {
            throw new BackendException(Code.CODE_NOT_EXIST, "用户信息不存在");
        }
        String[] userValue = value.split("-");
        userMessage.setUsername(userValue[0]);
        userMessage.setMerchantId(Long.valueOf(userValue[1]));
        return userMessage;
    }
}
