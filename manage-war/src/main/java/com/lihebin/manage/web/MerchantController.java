package com.lihebin.manage.web;

import com.lihebin.manage.bean.*;
import com.lihebin.manage.service.MerchantService;
import com.lihebin.manage.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by lihebin on 2019/5/22.
 */
@RestController
@RequestMapping("/api/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "/listMerchantCustomerPaging", method = RequestMethod.GET)
    public Result listMerchantCustomerPaging(@RequestHeader("token") String token,
                                             @RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "cellphone", required = false) String cellphone,
                                             @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                             @RequestParam(value = "pageSize", defaultValue = "30") int pageSize
    ) {
        return ResultUtil.success(merchantService.listMerchantCustomerPaging(token, Optional.ofNullable(name), Optional.ofNullable(cellphone), pageNo, pageSize));
    }

//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public void logout(@RequestParam(value = "token", required = true) String token) {
//        merchantUserService.logout(token);
//    }

    @RequestMapping(value = "/addMerchant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result addMerchant(@Valid @RequestBody MerchantNew merchant) {
        return ResultUtil.success(merchantService.addMerchant(merchant));
    }

    @RequestMapping(value = "/updateMerchant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result updateMerchant(@Valid @RequestBody MerchantUpdate merchant) {
        return ResultUtil.success(merchantService.updateMerchant(merchant));
    }




}
