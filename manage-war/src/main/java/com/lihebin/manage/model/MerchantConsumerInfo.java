package com.lihebin.manage.model;


import java.io.Serializable;
import java.util.Date;

/**
 * 会员
 * Created by lihebin on 2019/4/15.
 */
public class MerchantConsumerInfo implements Serializable {

    private Merchant merchant;

    private Consumer consumer;

    private MerchantConsumer merchantConsumer;

    public MerchantConsumerInfo(Consumer consumer) {
        this.consumer = consumer;
    }

    public MerchantConsumerInfo(Merchant merchant, Consumer consumer, MerchantConsumer merchantConsumer) {
        this.merchant = merchant;
        this.consumer = consumer;
        this.merchantConsumer = merchantConsumer;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public MerchantConsumer getMerchantConsumer() {
        return merchantConsumer;
    }

    public void setMerchantConsumer(MerchantConsumer merchantConsumer) {
        this.merchantConsumer = merchantConsumer;
    }
}
