package com.lihebin.manage.dao.manage;

import com.lihebin.manage.model.MerchantConsumer;
import com.lihebin.manage.model.MerchantConsumerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by lihebin on 2019/4/16.
 */
@Repository
public interface MerchantConsumerDao extends JpaRepository<MerchantConsumer, Long> {


//    /**
//     * 获取商户下所有的粉丝
//     *
//     * @param pageable
//     * @return
//     */
//    @Query(value = "select new MerchantConsumerInfo(c)" +
//            " FROM Consumer c, MerchantConsumer mc " +
//            "where mc.consumer_id = c.id and mc.merchant_id = :merchant_id " +
//            "order by mc.ctime desc"
////            countQuery = "select count(c.id)" +
////                    "FROM consumer c " +
////                    "left join merchant_consumer mc " +
////                    "on mc.consumer_id = c.id " +
////                    "where mc.merchant_id = :merchant_id " +
////                    "order by mc.ctime desc"
//    )
//    Page<MerchantConsumerInfo> findAllByMerchantId(@Param("merchant_id") Long id, Pageable pageable);
}
