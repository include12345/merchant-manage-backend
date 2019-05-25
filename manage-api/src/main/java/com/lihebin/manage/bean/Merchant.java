package com.lihebin.manage.bean;

import javax.persistence.Column;

/**
 * 商户
 * Created by lihebin on 2019/4/15.
 */
public class Merchant extends Base{

    private String sn;

    private String cellphone;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
