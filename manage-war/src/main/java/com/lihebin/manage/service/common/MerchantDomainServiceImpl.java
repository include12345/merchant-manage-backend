package com.lihebin.manage.service.common;

import com.lihebin.manage.bean.Code;
import com.lihebin.manage.bean.UserMessage;
import com.lihebin.manage.dao.cache.RedisDao;
import com.lihebin.manage.dao.manage.MerchantDao;
import com.lihebin.manage.exception.BackendException;
import com.lihebin.manage.model.Merchant;
import com.lihebin.manage.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by lihebin on 2019/6/25.
 */
@Service
public class MerchantDomainServiceImpl implements MerchantDomainService {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private MerchantDao merchantDao;

    @Override
    @Cacheable(value = "merchant", key = "'merchant_' + #id")
    public Merchant getMerchant(Long id) {
        Merchant merchant = merchantDao.findOne(id);
        if (merchant == null) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户不存在");
        }
        return merchant;
    }

    @Override
    @CacheEvict(value = "merchant", key = "'merchant_' + #id")
    public void removeMerchantCache(Long id) {

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
