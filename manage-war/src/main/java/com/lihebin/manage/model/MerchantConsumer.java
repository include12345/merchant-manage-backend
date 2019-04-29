package com.lihebin.manage.model;

import com.lihebin.manage.bean.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商户会员映射
 * Created by lihebin on 2019/4/15.
 */
@Entity
@Table(name = "merchant_consumer")
public class MerchantConsumer extends Base {

    @Column
    private String merchant_id;

    @Column
    private String consumer_id;

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(String consumer_id) {
        this.consumer_id = consumer_id;
    }
}
