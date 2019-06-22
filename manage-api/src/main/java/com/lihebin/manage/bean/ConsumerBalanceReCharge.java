package com.lihebin.manage.bean;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 商户会员充值
 * Created by lihebin on 2019/4/15.
 */
public class ConsumerBalanceReCharge {

    @NotNull
    private Long consumerId;

    private Long walletId;

    @NotNull
    @Pattern(regexp = "\\d{9}", message = "金额不合法")
    private Long amount;

    @NotBlank
    @Length(max = 128, message = "备注长度范围0-128个字符")
    private String remark;


    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
