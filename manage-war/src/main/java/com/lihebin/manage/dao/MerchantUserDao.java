package com.lihebin.manage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by lihebin on 2019/4/16.
 */
@Repository
public class MerchantUserDao {

    @Resource(name = "manageJdbcTemplate")
    JdbcTemplate manageJdbcTemplate;


}
