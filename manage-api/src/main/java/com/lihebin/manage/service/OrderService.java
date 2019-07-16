package com.lihebin.manage.service;


import com.lihebin.manage.bean.OrderNew;
import com.lihebin.manage.bean.OrderRes;

/**
 * Created by lihebin on 2019/4/29.
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderNew
     * @return
     */
    OrderRes pay(String token, OrderNew orderNew);
}
