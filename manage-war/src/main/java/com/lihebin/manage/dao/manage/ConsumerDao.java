package com.lihebin.manage.dao.manage;

import com.lihebin.manage.model.Consumer;
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
public interface ConsumerDao extends JpaRepository<Consumer, Long>{


    /**
     * 获取商户下所有的粉丝
     *
     * @param pageable
     * @return
     */
    @Query(nativeQuery = true, value = "select c.*, mc.merchant_id" +
            " FROM consumer c " +
            "left join merchant_consumer mc " +
            "on mc.consumer_id = c.id " +
            "where mc.merchant_id = ?1 " +
            "order by ?#{#pageable}",
            countQuery = "select count(c.id)" +
                    "FROM consumer c " +
                    "left join merchant_consumer mc " +
                    "on mc.consumer_id = c.id " +
                    "where mc.merchant_id = ?1 "
    )
    Page<Consumer> findAllByMerchantId(Long id, Pageable pageable);
}
