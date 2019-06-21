package com.lihebin.manage.dao.manage;

import com.lihebin.manage.model.MerchantConsumerWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by lihebin on 2019/4/16.
 */
@Repository
public interface MerchantConsumerWalletDao extends JpaRepository<MerchantConsumerWallet, Long> {


    MerchantConsumerWallet findByConsumerId(long consumerId);


    @Modifying
    @Query(nativeQuery = true, value ="delete from merchant_consumer_wallet where id = ?1")
    void deleteById(long id);
}
