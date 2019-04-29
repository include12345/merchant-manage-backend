package com.lihebin.manage.dao.manage;

import com.lihebin.manage.bean.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lihebin on 2019/4/16.
 */
@Repository
public interface MerchantDao extends JpaRepository<Merchant, Long> {

}
