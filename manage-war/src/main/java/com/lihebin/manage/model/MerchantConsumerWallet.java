package com.lihebin.manage.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lihebin on 2019/4/15.
 */
@Entity
@Table(name = "merchant_consumer_wallet")
@EntityListeners(AuditingEntityListener.class)
public class MerchantConsumerWallet implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "merchant_id")
    private Long merchantId;

    @Column(name = "consumer_id")
    private Long consumerId;

    @Column
    private Long balance;

    @CreatedDate
    @Column(name = "ctime")
    private Date ctime;

    @LastModifiedDate
    @Column(name = "mtime")
    private Date mtime;

    @Column(name = "operator_create")
    private String operatorCreate;

    @Column(name = "operator_update")
    private String operatorUpdate;

    @Column
    private Boolean deleted = false;

    @Version
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


    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
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
}
