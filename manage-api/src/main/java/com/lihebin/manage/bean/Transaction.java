package com.lihebin.manage.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 流水
 * Created by lihebin on 2019/4/15.
 */
public class Transaction extends Base {

    private String tsn;

    private String order_sn;

    private String name;

    private String remark;

    private Long original_amount;

    private Long pay_amount;

    private Long discount;

    private String merchant_id;

    private String consumer_id;

    private Integer type;

    private Integer status;

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOriginal_amount() {
        return original_amount;
    }

    public void setOriginal_amount(Long original_amount) {
        this.original_amount = original_amount;
    }

    public Long getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(Long pay_amount) {
        this.pay_amount = pay_amount;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
