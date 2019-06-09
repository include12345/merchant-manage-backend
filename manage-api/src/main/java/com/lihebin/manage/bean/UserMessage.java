package com.lihebin.manage.bean;

/**
 * 用户信息
 * Created by lihebin on 2019/4/15.
 */
public class UserMessage{

    private String username;

    private Long merchantId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
}
