package com.lihebin.manage.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by lihebin on 2018/12/2.
 */
@Configuration
public class DataSourceConfig {

    @Bean(name= "userDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.user")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }


    @Bean(name = "userJdbcTemplate")
    public JdbcTemplate userJdbcTemplate(@Qualifier("userDataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setQueryTimeout(3);
        return jdbcTemplate;
    }


    @Bean(name= "manageDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.manage")
    public DataSource manageDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }


    @Bean(name = "manageJdbcTemplate")
    public JdbcTemplate manageJdbcTemplate(@Qualifier("manageDataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setQueryTimeout(3);
        return jdbcTemplate;
    }
}
