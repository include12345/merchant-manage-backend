package com.lihebin.manage.bean;


/**
 * 商户会员
 * Created by lihebin on 2019/4/15.
 */
public class MerchantConsumerRes {


    private String consumerSn;

    private String consumerName;

    private String consumerCellphone;

    private String consumerEmail;

    private String consumerWechat;

    private Long merchantId;

    private Long consumerId;

    public String getConsumerSn() {
        return consumerSn;
    }

    public void setConsumerSn(String consumerSn) {
        this.consumerSn = consumerSn;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getConsumerCellphone() {
        return consumerCellphone;
    }

    public void setConsumerCellphone(String consumerCellphone) {
        this.consumerCellphone = consumerCellphone;
    }

    public String getConsumerEmail() {
        return consumerEmail;
    }

    public void setConsumerEmail(String consumerEmail) {
        this.consumerEmail = consumerEmail;
    }

    public String getConsumerWechat() {
        return consumerWechat;
    }

    public void setConsumerWechat(String consumerWechat) {
        this.consumerWechat = consumerWechat;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }
}
