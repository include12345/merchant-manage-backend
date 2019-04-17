package com.lihebin.manage.dao.manage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by lihebin on 2019/4/16.
 */
@Repository
public class MerchantConsumerWalletDao {

    @Resource(name = "manageJdbcTemplate")
    JdbcTemplate manageJdbcTemplate;


}
