package com.lihebin.manage.dao.manage;

import com.lihebin.manage.bean.Merchant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by lihebin on 2019/4/16.
 */
@Repository
public class MerchantDao {
    private final static Logger log = LoggerFactory.getLogger(MerchantDao.class);


    @Resource(name = "manageJdbcTemplate")
    JdbcTemplate manageJdbcTemplate;

    private static final String ID = "id";
    private static final String SN = "sn";
    private static final String NAME = "name";
    private static final String CELLPHONE = "cellphone";


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public


    /**
     * 根据id查询商户信息
     *
     * @param id
     * @return
     */
    public Merchant getMerchant(String id) {
        String sql = "select * from merchant where id = :id";
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(manageJdbcTemplate);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(ID, id);
        try {
            RowMapper<Merchant> rowMapper = new BeanPropertyRowMapper<>(Merchant.class);
            return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, rowMapper);
        } catch (Exception e) {
            log.error("getMerchant: {}, {}, {}", sql, id, e);
            return new Merchant();
        }
    }




}
