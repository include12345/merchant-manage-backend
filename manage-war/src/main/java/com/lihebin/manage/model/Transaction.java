package com.lihebin.manage.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 流水
 * Created by lihebin on 2019/4/15.
 */
@Entity
@Table(name = "transaction")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column
    private String tsn;

    @Column
    private String order_sn;

    @Column
    private String name;

    @Column
    private String remark;

    @Column
    private Long original_amount;

    @Column
    private Long pay_amount;

    @Column
    private Long discount;

    @Column
    private String merchant_id;

    @Column
    private String consumer_id;

    @Column
    private Integer type;

    @Column
    private Integer status;

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOriginal_amount() {
        return original_amount;
    }

    public void setOriginal_amount(Long original_amount) {
        this.original_amount = original_amount;
    }

    public Long getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(Long pay_amount) {
        this.pay_amount = pay_amount;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(String consumer_id) {
        this.consumer_id = consumer_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
