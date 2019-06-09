package com.lihebin.manage.service;

import com.lihebin.manage.bean.MerchantNew;
import com.lihebin.manage.bean.MerchantConsumerRes;
import com.lihebin.manage.bean.MerchantRes;
import com.lihebin.manage.bean.MerchantUpdate;
import org.springframework.data.domain.Page;

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
    Page<MerchantConsumerRes> listMerchantCustomerPaging(String token, int pageNo, int pageSize);

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
}
