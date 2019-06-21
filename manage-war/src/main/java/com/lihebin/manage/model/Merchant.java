package com.lihebin.manage.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 商户
 * Created by lihebin on 2019/4/15.
 */
@Entity
@Table(name = "merchant")
@EntityListeners(AuditingEntityListener.class)
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String sn;

    @Column
    private String name;

    @Column
    private String cellphone;

    @CreatedDate
    @Column(name = "ctime")
    private Date ctime;

    @LastModifiedDate
    @Column(name = "mtime")
    private Date mtime;

    @Column
    private Boolean deleted = false;

    @Version
    private Long version;

//    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<MerchantConsumer> merchantConsumerSet;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

//    public Set<MerchantConsumer> getMerchantConsumerSet() {
//        return merchantConsumerSet;
//    }
//
//    public void setMerchantConsumerSet(Set<MerchantConsumer> merchantConsumerSet) {
//        this.merchantConsumerSet = merchantConsumerSet;
//    }
}
