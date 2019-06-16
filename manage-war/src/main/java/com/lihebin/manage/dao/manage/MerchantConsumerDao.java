package com.lihebin.manage.dao.manage;

import com.lihebin.manage.model.MerchantConsumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by lihebin on 2019/4/16.
 */
@Repository
public interface MerchantConsumerDao extends JpaRepository<MerchantConsumer, Long>, JpaSpecificationExecutor<MerchantConsumer> {



}
