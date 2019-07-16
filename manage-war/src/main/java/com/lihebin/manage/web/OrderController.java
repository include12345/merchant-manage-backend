package com.lihebin.manage.web;

import com.lihebin.manage.bean.MerchantConsumerUpdate;
import com.lihebin.manage.bean.OrderNew;
import com.lihebin.manage.bean.Result;
import com.lihebin.manage.service.OrderService;
import com.lihebin.manage.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lihebin on 2019/6/25.
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/pay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result pay(@Valid @RequestHeader("token") String token, @RequestBody OrderNew orderNew) {
        return ResultUtil.success(orderService.pay(token, orderNew));
    }
}
