package com.lihebin.manage.service.common;

import com.lihebin.manage.model.MerchantConsumer;
import com.lihebin.manage.model.MerchantConsumerWallet;

/**
 * Created by lihebin on 2019/6/25.
 */
public interface ConsumerDomainService {


    /**
     * 获取会员信息
     *
     * @param consumerId
     * @return
     */
    MerchantConsumer getMerchantConsumer(Long consumerId);


    /**
     * 清除会员缓存
     *
     * @param consumerId
     */
    void removeMerchantConsumerCache(Long consumerId);
    /**
     * 获取商户会员钱包
     *
     * @param consumerId
     * @return
     */
    MerchantConsumerWallet getMerchantConsumerWallet(Long consumerId);

    /**
     * 清除会员钱包缓存
     *
     * @param consumerId
     */
    void removeMerchantConsumerWalletCache(Long consumerId);
}
