package com.lihebin.manage.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 充值流水
 * Created by lihebin on 2019/4/15.
 */
@Entity
@Table(name = "wallet_add_transaction")
@EntityListeners(AuditingEntityListener.class)
public class WalletAddTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "ctime")
    private Date ctime;

    @LastModifiedDate
    @Column(name = "mtime")
    private Date mtime;

    @Column
    private Boolean deleted;

    @Column
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }


    @Column
    private String wallet_id;

    @Column
    private Long before_balance;

    @Column
    private Long add_amount;

    @Column
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
