package com.lihebin.manage.bean;


/**
 * Created by lihebin on 2019/4/16.
 */
public class LoginRes {

    private String merchantId;

    private String token;

    private Integer type;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
