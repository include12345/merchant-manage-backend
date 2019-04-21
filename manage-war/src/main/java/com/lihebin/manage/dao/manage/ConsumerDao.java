package com.lihebin.manage.dao.manage;

import com.lihebin.manage.bean.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by lihebin on 2019/4/16.
 */
@Repository
public interface ConsumerDao extends JpaRepository<Consumer, Long>{


}
