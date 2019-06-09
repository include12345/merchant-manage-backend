package com.lihebin.manage.web;

import com.lihebin.manage.bean.MerchantNew;
import com.lihebin.manage.bean.MerchantConsumerRes;
import com.lihebin.manage.bean.MerchantRes;
import com.lihebin.manage.bean.MerchantUpdate;
import com.lihebin.manage.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lihebin on 2019/5/22.
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "/listMerchantCustomerPaging", method = RequestMethod.GET)
    public Page<MerchantConsumerRes> listMerchantCustomerPaging(@RequestHeader("token") String token,
                                                                @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                                @RequestParam(value = "pageSize", defaultValue = "30") int pageSize
    ) {
        return merchantService.listMerchantCustomerPaging(token, pageNo, pageSize);
    }

//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public void logout(@RequestParam(value = "token", required = true) String token) {
//        merchantUserService.logout(token);
//    }

    @RequestMapping(value = "/addMerchant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MerchantRes addMerchant(@Valid @RequestBody MerchantNew merchant) {
        return merchantService.addMerchant(merchant);
    }

    @RequestMapping(value = "/updateMerchant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MerchantRes updateMerchant(@Valid @RequestBody MerchantUpdate merchant) {
        return merchantService.updateMerchant(merchant);
    }




}
