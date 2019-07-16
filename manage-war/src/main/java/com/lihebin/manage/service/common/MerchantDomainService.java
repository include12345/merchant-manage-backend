package com.lihebin.manage.service.common;

import com.lihebin.manage.bean.UserMessage;
import com.lihebin.manage.model.Merchant;

/**
 * Created by lihebin on 2019/6/25.
 */
public interface MerchantDomainService {

    /**
     * 获取商户信息
     *
     * @param id
     * @return
     */
    Merchant getMerchant(Long id);

    /**
     * 清除会员缓存
     *
     * @param id
     */
    void removeMerchantCache(Long id);


    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    UserMessage getUserMessage(String token);

}
