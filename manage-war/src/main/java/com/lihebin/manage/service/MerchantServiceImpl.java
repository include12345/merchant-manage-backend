package com.lihebin.manage.service;

import com.lihebin.manage.bean.*;
import com.lihebin.manage.dao.manage.*;
import com.lihebin.manage.exception.BackendException;
import com.lihebin.manage.model.Merchant;
import com.lihebin.manage.model.MerchantConsumer;
import com.lihebin.manage.model.MerchantConsumerWallet;
import com.lihebin.manage.model.WalletAddTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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


    @Autowired
    private MerchantConsumerWalletDao merchantConsumerWalletDao;

    @Autowired
    private WalletAddTransactionDao walletAddTransactionDao;


    @Override
    public Page<MerchantConsumerRes> listMerchantConsumerPaging(String token, Optional<String> name, Optional<String> cellphone,  int pageNo, int pageSize) {
        UserMessage userMessage = merchantUserService.getUserMessage(token);
        Page<MerchantConsumer> merchantPage = merchantConsumerDao.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Path<Long> merchantIdPath = root.get("merchantId");
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

    @Override
    public void deleteMerchantConsumer(String token, long id) {
        checkMerchantConsumer(token, id);
        long walletId = checkMerchantConsumerWallet(id);
        if (walletId != 0L) {
            merchantConsumerWalletDao.delete(walletId);
        }
        merchantConsumerDao.delete(id);
    }



    @Override
    public MerchantConsumerWalletRes getMerchantConsumerWallet(String token, long consumerId) {
        checkMerchantConsumer(token, consumerId);
        MerchantConsumerWallet merchantConsumerWallet = merchantConsumerWalletDao.findByConsumerId(consumerId);
        MerchantConsumerWalletRes merchantConsumerWalletRes = new MerchantConsumerWalletRes();
        if (merchantConsumerWallet != null) {
            merchantConsumerWalletRes.setId(merchantConsumerWallet.getId());
            merchantConsumerWalletRes.setMerchantId(merchantConsumerWallet.getMerchantId());
            merchantConsumerWalletRes.setConsumerId(merchantConsumerWallet.getConsumerId());
            merchantConsumerWalletRes.setBalance(merchantConsumerWallet.getBalance());
            merchantConsumerWalletRes.setCtime(merchantConsumerWallet.getCtime());
            merchantConsumerWalletRes.setMtime(merchantConsumerWallet.getMtime());
        }
        return merchantConsumerWalletRes;
    }

    @Transactional
    @Override
    public MerchantConsumerRes addMerchantConsumer(String token, MerchantConsumerAdd merchantConsumerAdd) {
        long merchantId = merchantUserService.getUserMessage(token).getMerchantId();
        MerchantConsumer merchantConsumer = merchantConsumerDao.findByMerchantIdAndName(merchantId, merchantConsumerAdd.getName());
        if (null != merchantConsumer) {
            throw new BackendException(Code.CODE_EXIST, "商户会员名称[%s]已存在", merchantConsumerAdd.getName());
        }
        merchantConsumer = merchantConsumerDao.findByMerchantIdAndCellphone(merchantId, merchantConsumerAdd.getCellphone());
        if (null != merchantConsumer) {
            throw new BackendException(Code.CODE_EXIST, "商户会员手机号[%s]已存在", merchantConsumerAdd.getCellphone());
        }
        merchantConsumer = new MerchantConsumer();
        merchantConsumer.setMerchantId(merchantId);
        merchantConsumer.setName(merchantConsumerAdd.getName());
        merchantConsumer.setCellphone(merchantConsumerAdd.getCellphone());
        merchantConsumer.setEmail(merchantConsumer.getEmail());
        merchantConsumer.setWechat(merchantConsumer.getWechat());
        merchantConsumer = merchantConsumerDao.save(merchantConsumer);
        initMerchantConsumerWallet(merchantId, merchantConsumer.getId());
        MerchantConsumerRes merchantConsumerRes = new MerchantConsumerRes();
        merchantConsumerRes.setId(merchantConsumer.getId());
        return merchantConsumerRes;
    }



    @Override
    public MerchantConsumerRes updateMerchantConsumer(String token, MerchantConsumerUpdate merchantConsumerUpdate) {
        long merchantId = merchantUserService.getUserMessage(token).getMerchantId();
        MerchantConsumer merchantConsumerOld = merchantConsumerDao.findOne(merchantConsumerUpdate.getId());
        if (merchantConsumerOld == null || !merchantConsumerOld.getMerchantId().equals(merchantId)) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户会员不存在");
        }
        MerchantConsumer merchantConsumerCheck = merchantConsumerDao.findByMerchantIdAndName(merchantId, merchantConsumerUpdate.getName());
        if (null != merchantConsumerCheck) {
            throw new BackendException(Code.CODE_EXIST, "商户会员名称[%s]已存在", merchantConsumerUpdate.getName());
        }
        merchantConsumerCheck = merchantConsumerDao.findByMerchantIdAndCellphone(merchantId, merchantConsumerUpdate.getCellphone());
        if (null != merchantConsumerCheck) {
            throw new BackendException(Code.CODE_EXIST, "商户会员手机号[%s]已存在", merchantConsumerUpdate.getCellphone());
        }
        MerchantConsumer merchantConsumer = new MerchantConsumer();
        merchantConsumer.setId(merchantConsumerUpdate.getId());
        merchantConsumer.setName(merchantConsumerUpdate.getName());
        merchantConsumer.setCellphone(merchantConsumerUpdate.getCellphone());
        merchantConsumer.setEmail(merchantConsumerUpdate.getEmail());
        merchantConsumer.setWechat(merchantConsumerUpdate.getWechat());
        merchantConsumerDao.save(merchantConsumer);
        MerchantConsumerRes merchantConsumerRes = new MerchantConsumerRes();
        merchantConsumerRes.setId(merchantConsumerUpdate.getId());
        return merchantConsumerRes;
    }

    @Transactional
    @Override
    public MerchantConsumerWalletRes rechargeMerchantConsumerBalance(String token, ConsumerBalanceReCharge consumerBalanceReCharge) {
        long merchantId = merchantUserService.getUserMessage(token).getMerchantId();
        MerchantConsumer merchantConsumerOld = merchantConsumerDao.findOne(consumerBalanceReCharge.getConsumerId());
        if (merchantConsumerOld == null || !merchantConsumerOld.getMerchantId().equals(merchantId)) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户会员不存在");
        }
        long walletId;
        long balanceOld;
        if (consumerBalanceReCharge.getWalletId() == null) {
            walletId = initMerchantConsumerWallet(merchantId, consumerBalanceReCharge.getConsumerId());
            balanceOld = 0L;
        } else {
            MerchantConsumerWallet merchantConsumerWalletOld = merchantConsumerWalletDao.findOne(consumerBalanceReCharge.getWalletId());
            if (merchantConsumerWalletOld == null
                    || !merchantConsumerWalletOld.getConsumerId().equals(consumerBalanceReCharge.getConsumerId())) {
                throw new BackendException(Code.CODE_NOT_EXIST, "商户会员钱包不存在");
            }
            walletId = consumerBalanceReCharge.getWalletId();
            balanceOld = merchantConsumerWalletOld.getBalance();
        }
        long afterBalance = balanceOld + consumerBalanceReCharge.getBalance();
        MerchantConsumerWallet merchantConsumerWallet = new MerchantConsumerWallet();
        merchantConsumerWallet.setId(walletId);
        merchantConsumerWallet.setBalance(afterBalance);
        merchantConsumerWallet = merchantConsumerWalletDao.save(merchantConsumerWallet);
        WalletAddTransaction walletAddTransaction = new WalletAddTransaction();
        walletAddTransaction.setWalletId(walletId);
        walletAddTransaction.setBeforeBalance(balanceOld);
        walletAddTransaction.setAddAmount(consumerBalanceReCharge.getBalance());
        walletAddTransaction.setAfterBalance(afterBalance);
        walletAddTransactionDao.save(walletAddTransaction);
        MerchantConsumerWalletRes merchantConsumerWalletRes = new MerchantConsumerWalletRes();
        merchantConsumerWalletRes.setId(walletId);
        merchantConsumerWalletRes.setBalance(merchantConsumerWallet.getBalance());
        return merchantConsumerWalletRes;
    }


    /**
     * 校验商户会员余额是否为空
     *
     * @param consumerId
     */
    private long checkMerchantConsumerWallet(long consumerId) {
        MerchantConsumerWallet merchantConsumerWallet = merchantConsumerWalletDao.findByConsumerId(consumerId);
        if (merchantConsumerWallet == null) {
            return 0L;
        }
        if (merchantConsumerWallet.getBalance() != 0L) {
            throw new BackendException(Code.CODE_EXIST, "商户会员余额不为空[%d]", merchantConsumerWallet.getBalance());
        } else {
            return merchantConsumerWallet.getId();
        }
    }

    /**
     * 初始化会员钱包
     *
     * @param merchantId
     * @param consumerId
     */
    private long initMerchantConsumerWallet(long merchantId, long consumerId) {
        MerchantConsumerWallet merchantConsumerWallet = new MerchantConsumerWallet();
        merchantConsumerWallet.setMerchantId(merchantId);
        merchantConsumerWallet.setConsumerId(consumerId);
        merchantConsumerWallet.setBalance(0L);
        merchantConsumerWallet = merchantConsumerWalletDao.save(merchantConsumerWallet);
        return merchantConsumerWallet.getId();
    }

    /**
     * 校验token商户会员
     *
     * @param token
     * @param id
     * @return
     */
    private MerchantConsumer checkMerchantConsumer(String token, long id) {
        long merchantId = merchantUserService.getUserMessage(token).getMerchantId();
        MerchantConsumer merchantConsumer = merchantConsumerDao.findOne(id);
        if (merchantConsumer == null) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户不存在");
        }
        if (!merchantConsumer.getMerchantId().equals(merchantId)) {
            throw new BackendException(Code.CODE_PARAM_ERROR, "账户无权限操作会员");
        }
        return merchantConsumer;
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
        merchantConsumerRes.setMerchantId(merchantConsumer.getMerchantId());
        merchantConsumerRes.setConsumerCellphone(merchantConsumer.getCellphone());
        merchantConsumerRes.setConsumerEmail(merchantConsumer.getEmail());
        merchantConsumerRes.setConsumerName(merchantConsumer.getName());
        merchantConsumerRes.setConsumerWechat(merchantConsumer.getWechat());
        merchantConsumerRes.setCtime(merchantConsumer.getCtime());
        merchantConsumerRes.setMtime(merchantConsumer.getMtime());
        return merchantConsumerRes;
    }
}
