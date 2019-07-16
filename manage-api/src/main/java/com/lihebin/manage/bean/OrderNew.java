package com.lihebin.manage.bean;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 订单
 * Created by lihebin on 2019/4/15.
 */
public class OrderNew {

    @NotNull
    private Long consumerId;

    @NotBlank
    @Length(max = 128, message = "订单名称长度范围0-128个字符")
    private String name;

    @NotBlank
    @Length(max = 128, message = "备注长度范围0-128个字符")
    private String remark;

    @NotNull
    @Pattern(regexp = "\\d{9}", message = "原始金额不合法")
    private Long originalAmount;

    @NotNull
    @Pattern(regexp = "\\d{9}", message = "优惠金额不合法")
    private Long discount;


    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
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

    public Long getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Long originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }
}
