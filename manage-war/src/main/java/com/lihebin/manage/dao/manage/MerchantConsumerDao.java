package com.lihebin.manage.dao.manage;

import com.lihebin.manage.model.MerchantConsumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by lihebin on 2019/4/16.
 */
@Repository
public interface MerchantConsumerDao extends JpaRepository<MerchantConsumer, Long>, JpaSpecificationExecutor<MerchantConsumer> {

    /**
     * 根据name查询会员是否存在
     *
     * @param merchantId
     * @param name
     * @return
     */
    MerchantConsumer findByMerchantIdAndName(long merchantId, String name);


    /**
     * 根据cellphone查询会员是否存在
     *
     * @param merchantId
     * @param cellphone
     * @return
     */
    MerchantConsumer findByMerchantIdAndCellphone(long merchantId, String cellphone);
}
