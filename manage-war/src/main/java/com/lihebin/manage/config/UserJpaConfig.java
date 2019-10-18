package com.lihebin.manage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * Created by lihebin on 2019/4/17.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entitySsoFactoryUser",
        transactionManagerRef = "transactionSsoUser",
        basePackages = {"com.lihebin.manage.dao.user"}) //设置Repository所在位置
public class UserJpaConfig {

    @Autowired
    private DataSource userDataSource;

    @Autowired
    private JpaProperties jpaUserProperties;

    @Bean
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entitySsoFactoryUser(builder).getObject().createEntityManager();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entitySsoFactoryUser(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(userDataSource).packages("com.lihebin.manage.sso.model")//设置实体类所在位置
                .properties(jpaUserProperties.getProperties())
                .persistenceUnit("userPersistenceUnit")//持久化单元创建一个默认即可，多个便要分别命名
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionSsoUser(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entitySsoFactoryUser(builder).getObject());
    }
}
