package com.lihebin.manage.model;

import com.lihebin.manage.bean.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lihebin on 2019/4/15.
 */
@Entity
@Table(name = "merchant_consumer_wallet")
public class MerchantConsumerWallet extends Base {

    @Column
    private String merchant_id;

    @Column
    private String consumer_id;

    @Column
    private Long balance;

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

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
