package com.lihebin.manage.model;

import com.lihebin.manage.bean.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 会员
 * Created by lihebin on 2019/4/15.
 */
@Entity
@Table(name = "consumer")
public class Consumer extends Base {

    @Column
    private String sn;

    @Column
    private String name;

    @Column
    private String cellphone;

    @Column
    private String email;

    @Column
    private String wechat;

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

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
}
