package com.lihebin.manage.model;

import com.lihebin.manage.bean.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 订单
 * Created by lihebin on 2019/4/15.
 */
@Entity
@Table(name = "order")
public class Order extends Base {

    @Column
    private String sn;

    @Column
    private String name;

    @Column
    private String remark;

    @Column
    private Long original_amount;

    @Column
    private Long pay_amount;

    @Column
    private Long discount;

    @Column
    private String merchant_id;

    @Column
    private String consumer_id;

    @Column
    private Integer type;

    @Column
    private Integer status;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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