package com.lihebin.manage.bean;


import java.util.Date;

/**
 * 会员余额流水
 * Created by lihebin on 2019/4/15.
 */
public class WalletTransactionRes {

    private Long walletId;

    private Long consumerId;

    private Long beforeBalance;

    private Long addAmount;

    private Long afterBalance;

    private String remark;

    private String operatorCreate;

    private String operatorUpdate;

    private Date ctime;

    private Date mtime;


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

    public Long getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(Long beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public Long getAddAmount() {
        return addAmount;
    }

    public void setAddAmount(Long addAmount) {
        this.addAmount = addAmount;
    }

    public Long getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(Long afterBalance) {
        this.afterBalance = afterBalance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperatorCreate() {
        return operatorCreate;
    }

    public void setOperatorCreate(String operatorCreate) {
        this.operatorCreate = operatorCreate;
    }

    public String getOperatorUpdate() {
        return operatorUpdate;
    }

    public void setOperatorUpdate(String operatorUpdate) {
        this.operatorUpdate = operatorUpdate;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
}
