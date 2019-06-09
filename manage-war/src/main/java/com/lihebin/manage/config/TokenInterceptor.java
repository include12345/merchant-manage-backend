package com.lihebin.manage.config;

import com.lihebin.manage.bean.Code;
import com.lihebin.manage.dao.cache.RedisDao;
import com.lihebin.manage.dao.manage.MerchantUserDao;
import com.lihebin.manage.exception.BackendException;
import com.lihebin.manage.exception.BackendLoginException;
import com.lihebin.manage.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lihebin on 03/01/2018.
 */
@Configuration
public class TokenInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MerchantUserDao merchantUserDao;

    @Autowired
    private RedisDao redisDao;





    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token;
        try {
            token = request.getHeader("token");
        } catch (Exception e) {
            throw new BackendLoginException("登录超时");
        }
        log.info("TokenInterceptor:{}", token);
//        String[] param = token.split("-");
//        String method = request.getRequestURI();
        String username = redisDao.getValue(token);
        if (!StringUtil.empty(username)) {
            throw new BackendException(Code.CODE_TIME_OUT, "登录超时");
        }
//
        return true;
    }

}
