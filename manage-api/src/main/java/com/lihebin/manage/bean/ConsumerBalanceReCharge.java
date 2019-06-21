package com.lihebin.manage.bean;


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
    private Long balance;


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

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
