package com.lihebin.manage.service;

import com.lihebin.manage.bean.*;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Created by lihebin on 2019/4/29.
 */
public interface MerchantService {


    /**
     * 获取商户下会员信息
     *
     * @param token
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<MerchantConsumerRes> listMerchantConsumerPaging(String token, Optional<String> name, Optional<String> cellphone, int pageNo, int pageSize);

    /**
     * 创建商户
     *
     * @param merchant
     * @return
     */
    MerchantRes addMerchant(MerchantNew merchant);

    /**
     * 创建商户
     *
     * @param merchant
     * @return
     */
    MerchantRes updateMerchant(MerchantUpdate merchant);

    /**
     * 删除商户会员
     *
     * @param id
     */
    void deleteMerchantConsumer(String token, long id);

    /**
     * 获取商户会员余额信息
     *
     * @param token
     * @param consumerId
     * @return
     */
    MerchantConsumerRes getMerchantConsumer(String token, long consumerId);

    /**
     * 新增商户会员
     *
     * @param token
     * @param merchantConsumerAdd
     * @return
     */
    MerchantConsumerRes addMerchantConsumer(String token, MerchantConsumerAdd merchantConsumerAdd);

    /**
     * 编辑商户会员
     *
     * @param token
     * @param merchantConsumerUpdate
     * @return
     */
    MerchantConsumerRes updateMerchantConsumer(String token, MerchantConsumerUpdate merchantConsumerUpdate);

    /**
     * 会员充值
     *
     * @param token
     * @param consumerBalanceReCharge
     * @return
     */
    MerchantConsumerWalletRes rechargeMerchantConsumerBalance(String token, ConsumerBalanceReCharge consumerBalanceReCharge);

}
