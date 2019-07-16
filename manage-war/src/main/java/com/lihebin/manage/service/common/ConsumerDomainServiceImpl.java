package com.lihebin.manage.service.common;

import com.lihebin.manage.bean.Code;
import com.lihebin.manage.dao.manage.MerchantConsumerDao;
import com.lihebin.manage.dao.manage.MerchantConsumerWalletDao;
import com.lihebin.manage.exception.BackendException;
import com.lihebin.manage.model.MerchantConsumer;
import com.lihebin.manage.model.MerchantConsumerWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by lihebin on 2019/6/25.
 */
@Service
public class ConsumerDomainServiceImpl implements ConsumerDomainService {

    @Autowired
    private MerchantConsumerDao merchantConsumerDao;

    @Autowired
    private MerchantConsumerWalletDao merchantConsumerWalletDao;

    @Override
    @Cacheable(value = "merchantConsumer", key = "'merchantConsumer_' + #consumerId")
    public MerchantConsumer getMerchantConsumer(Long consumerId) {
        MerchantConsumer merchantConsumer = merchantConsumerDao.findOne(consumerId);
        if (merchantConsumer == null) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户会员不存在");
        }
        return merchantConsumer;
    }

    @Override
    @CacheEvict(value = "merchantConsumer", key = "'merchantConsumer_' + #consumerId")
    public void removeMerchantConsumerCache(Long consumerId) {

    }

    @Override
//    @Cacheable(value = "merchantConsumerWallet", key = "'merchantConsumerWallet_' + #consumerId")
    public MerchantConsumerWallet getMerchantConsumerWallet(Long consumerId) {
        MerchantConsumerWallet merchantConsumerWallet = merchantConsumerWalletDao.findByConsumerId(consumerId);
        if (merchantConsumerWallet == null) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户会员钱包不存在");
        }
        return merchantConsumerWallet;
    }

    @Override
//    @CacheEvict(value = "merchantConsumerWallet", key = "'merchantConsumerWallet_' + #consumerId")
    public void removeMerchantConsumerWalletCache(Long consumerId) {

    }
}
