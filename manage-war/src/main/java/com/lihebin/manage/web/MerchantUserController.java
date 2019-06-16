package com.lihebin.manage.web;

import com.lihebin.manage.bean.Login;
import com.lihebin.manage.bean.LoginRes;
import com.lihebin.manage.bean.Result;
import com.lihebin.manage.service.MerchantUserService;
import com.lihebin.manage.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lihebin on 2019/5/22.
 */
@RestController
@RequestMapping("/user")
public class MerchantUserController {

    @Autowired
    private MerchantUserService merchantUserService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result login(@Valid @RequestBody Login login) {
        return ResultUtil.success(merchantUserService.login(login));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result logout(@RequestParam(value = "token", required = true) String token) {
        merchantUserService.logout(token);
        return ResultUtil.success(null);
    }


    @RequestMapping(value = "/api/check", method = RequestMethod.GET)
    public Result checkToken() {
        return ResultUtil.success(null);
    }


}
