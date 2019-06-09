package com.lihebin.manage.model;



import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 商户会员映射
 * Created by lihebin on 2019/4/15.
 */
@Entity
@Table(name = "merchant_consumer")
@EntityListeners(AuditingEntityListener.class)
public class MerchantConsumer {


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
    private Long merchant_id;

    @Column
    private Long consumer_id;


    public Long getMerchant() {
        return merchant_id;
    }

    public void setMerchant(Long merchant_id) {
        this.merchant_id = merchant_id;
    }

    public Long getConsumer() {
        return consumer_id;
    }

    public void setConsumer(Long consumer_id) {
        this.consumer_id = consumer_id;
    }
}
