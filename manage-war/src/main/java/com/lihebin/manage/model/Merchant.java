package com.lihebin.manage.model;

import com.lihebin.manage.bean.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商户
 * Created by lihebin on 2019/4/15.
 */
@Entity
@Table(name = "merchant")
public class Merchant extends Base {

    @Column
    private String sn;

    @Column
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
