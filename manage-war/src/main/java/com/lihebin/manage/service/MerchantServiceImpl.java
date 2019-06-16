package com.lihebin.manage.service;

import com.lihebin.manage.bean.*;
import com.lihebin.manage.dao.manage.MerchantConsumerDao;
import com.lihebin.manage.dao.manage.MerchantDao;
import com.lihebin.manage.dao.manage.SimpleSnGeneratorDao;
import com.lihebin.manage.exception.BackendException;
import com.lihebin.manage.model.Merchant;
import com.lihebin.manage.model.MerchantConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * Created by lihebin on 2019/4/29.
 */
@Service
public class MerchantServiceImpl implements MerchantService {


    @Autowired
    private MerchantUserService merchantUserService;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private MerchantConsumerDao merchantConsumerDao;

    @Autowired
    private SimpleSnGeneratorDao simpleSnGeneratorDao;


    @Override
    public Page<MerchantConsumerRes> listMerchantCustomerPaging(String token, Optional<String> name, Optional<String> cellphone,  int pageNo, int pageSize) {
        UserMessage userMessage = merchantUserService.getUserMessage(token);
        Page<MerchantConsumer> merchantPage = merchantConsumerDao.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Path<Long> merchantIdPath = root.get("merchant_id");
            Path<String> namePath = root.get("name");
            Path<String> cellphonePath = root.get("cellphone");
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.equal(merchantIdPath, userMessage.getMerchantId()));
            name.ifPresent(s -> predicateList.add(criteriaBuilder.like(namePath, "%" + s + "%")));
            cellphone.ifPresent(s -> predicateList.add(criteriaBuilder.equal(cellphonePath, s)));
            Predicate[] p = new Predicate[predicateList.size()];
            return criteriaBuilder.and(predicateList.toArray(p));
        }, new PageRequest(pageNo, pageSize));
        return new PageImpl<>(
                merchantPage.getContent()
                        .stream()
                        .map(this::buildMerchantConsumerRes)
                        .collect(Collectors.toList()),
                merchantPage.previousPageable(), merchantPage.getTotalElements());
    }


    /**
     * build MerchantConsumerRes
     *
     * @param merchantConsumer
     * @return
     */
    private MerchantConsumerRes buildMerchantConsumerRes(MerchantConsumer merchantConsumer) {
        MerchantConsumerRes merchantConsumerRes = new MerchantConsumerRes();
        merchantConsumerRes.setId(merchantConsumer.getId());
        merchantConsumerRes.setMerchantId(merchantConsumer.getMerchant_id());
        merchantConsumerRes.setConsumerCellphone(merchantConsumer.getCellphone());
        merchantConsumerRes.setConsumerEmail(merchantConsumer.getEmail());
        merchantConsumerRes.setConsumerName(merchantConsumer.getName());
        merchantConsumerRes.setConsumerSn(merchantConsumer.getSn());
        merchantConsumerRes.setConsumerWechat(merchantConsumer.getWechat());
        merchantConsumerRes.setCtime(merchantConsumer.getCtime());
        merchantConsumerRes.setMtime(merchantConsumer.getMtime());
        return merchantConsumerRes;
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
        merchantAdd = merchantDao.save(merchantAdd);
        MerchantRes merchantRes = new MerchantRes();
        merchantRes.setId(merchantAdd.getId());
        return merchantRes;
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
        merchantQuery = merchantDao.save(merchantQuery);
        MerchantRes merchantRes = new MerchantRes();
        merchantRes.setId(merchantQuery.getId());
        return merchantRes;
    }
}
