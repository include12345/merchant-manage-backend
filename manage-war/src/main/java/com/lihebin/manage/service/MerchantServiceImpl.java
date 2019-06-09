package com.lihebin.manage.service;

import com.lihebin.manage.bean.*;
import com.lihebin.manage.dao.manage.MerchantDao;
import com.lihebin.manage.dao.manage.SimpleSnGeneratorDao;
import com.lihebin.manage.exception.BackendException;
import com.lihebin.manage.model.Merchant;
import com.lihebin.manage.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


/**
 * Created by lihebin on 2019/4/29.
 */
@Service
public class MerchantServiceImpl implements MerchantService{


    @Autowired
    private MerchantUserService merchantUserService;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private SimpleSnGeneratorDao simpleSnGeneratorDao;


    @Override
    public Page<MerchantConsumerRes> listMerchantCustomerPaging(String token, int pageNo, int pageSize) {
        UserMessage userMessage = merchantUserService.getUserMessage(token);
        Page<Merchant> merchantPage = merchantDao.findAllByIdOrderByMerchantConsumerSetCtimeDesc(userMessage.getMerchantId(), new PageRequest(pageNo, pageSize));
        return null;
    }

    @Override
    public MerchantRes addMerchant(MerchantNew merchant) {
        Merchant merchantQuery = merchantDao.findByName(merchant.getName());
        if (merchantQuery != null) {
            throw new BackendException(Code.CODE_EXIST, "商户名称已存在");
        }
        String merchantSn = simpleSnGeneratorDao.nextMerchantSn();
        Merchant merchantAdd = new Merchant();
        merchantAdd.setSn(merchantSn);
        merchantAdd.setCellphone(merchant.getCellphone());
        merchantAdd.setName(merchant.getName());
        merchantAdd.setDeleted(false);
        merchantDao.save(merchantAdd);
        return null;
    }

    @Override
    public MerchantRes updateMerchant(MerchantUpdate merchant) {
        Merchant merchantQuery = merchantDao.findOne(merchant.getId());
        if (merchantQuery == null) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户id不存在");
        }
        merchantQuery.setId(merchant.getId());
        if (!merchantQuery.getName().equals(merchant.getName())) {
            Merchant merchantName = merchantDao.findByName(merchant.getName());
            if (merchantName != null) {
                throw new BackendException(Code.CODE_EXIST, "商户名称已存在");
            }
            merchantQuery.setName(merchant.getName());
        }
        if (!merchantQuery.getCellphone().equals(merchant.getCellphone())) {
            merchantQuery.setCellphone(merchant.getCellphone());
        }
        if (merchant.getDeleted() != null && !merchantQuery.getDeleted().equals(merchant.getDeleted())) {
            merchantQuery.setDeleted(merchant.getDeleted());
        }
        merchantDao.save(merchantQuery);
        return null;
    }
}
