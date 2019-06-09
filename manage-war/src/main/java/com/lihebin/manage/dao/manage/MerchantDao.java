package com.lihebin.manage.dao.manage;

import com.lihebin.manage.model.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lihebin on 2019/4/16.
 */
@Repository
public interface MerchantDao extends JpaRepository<Merchant, Long> {


    /**
     * 根据商户名查商户
     *
     * @param name
     * @return
     */
    Merchant findByName(String name);

}
