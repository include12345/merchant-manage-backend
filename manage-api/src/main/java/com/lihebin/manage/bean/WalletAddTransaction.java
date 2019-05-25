package com.lihebin.manage.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 充值流水
 * Created by lihebin on 2019/4/15.
 */
public class WalletAddTransaction extends Base {

    private String wallet_id;

    private Long before_balance;

    private Long add_amount;

    private Long after_balance;

    public String getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(String wallet_id) {
        this.wallet_id = wallet_id;
    }

    public Long getBefore_balance() {
        return before_balance;
    }

    public void setBefore_balance(Long before_balance) {
        this.before_balance = before_balance;
    }

    public Long getAdd_amount() {
        return add_amount;
    }

    public void setAdd_amount(Long add_amount) {
        this.add_amount = add_amount;
    }

    public Long getAfter_balance() {
        return after_balance;
    }

    public void setAfter_balance(Long after_balance) {
        this.after_balance = after_balance;
    }
}
