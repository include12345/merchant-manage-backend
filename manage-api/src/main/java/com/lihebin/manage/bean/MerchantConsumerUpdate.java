package com.lihebin.manage.bean;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 商户会员
 * Created by lihebin on 2019/4/15.
 */
public class MerchantConsumerUpdate {

    @NotNull
    private Long id;

    @NotBlank
    @Length(max = 128, message = "会员名称长度范围0-128个字符")
    private String name;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号不合法")
    private String cellphone;


    @Pattern(regexp = "/^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$/", message = "邮箱不合法")
    private String email;

    @Length(max = 128, message = "会员微信号长度范围0-128个字符")
    private String wechat;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
