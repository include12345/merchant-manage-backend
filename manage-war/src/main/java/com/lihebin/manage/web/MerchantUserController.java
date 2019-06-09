package com.lihebin.manage.web;

import com.lihebin.manage.bean.Login;
import com.lihebin.manage.bean.LoginRes;
import com.lihebin.manage.service.MerchantUserService;
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
    public LoginRes login(@Valid @RequestBody Login login) {
        return merchantUserService.login(login);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(@RequestParam(value = "token", required = true) String token) {
        merchantUserService.logout(token);
    }



}
