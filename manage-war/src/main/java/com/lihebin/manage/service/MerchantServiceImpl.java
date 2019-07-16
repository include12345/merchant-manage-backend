package com.lihebin.manage.service;

import com.lihebin.manage.bean.*;
import com.lihebin.manage.dao.manage.*;
import com.lihebin.manage.exception.BackendException;
import com.lihebin.manage.model.Merchant;
import com.lihebin.manage.model.MerchantConsumer;
import com.lihebin.manage.model.MerchantConsumerWallet;
import com.lihebin.manage.model.WalletAddTransaction;
import com.lihebin.manage.service.common.ConsumerDomainService;
import com.lihebin.manage.service.common.MerchantDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by lihebin on 2019/4/29.
 */
@Service
public class MerchantServiceImpl implements MerchantService {


    @Autowired
    private ConsumerDomainService consumerDomainService;

    @Autowired
    private MerchantDomainService merchantDomainService;

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
    public Page<MerchantConsumerRes> listMerchantConsumerPaging(String token, Optional<Date> ctimeStart, Optional<Date> ctimeEnd, Optional<String> name, Optional<String> cellphone, int pageNo, int pageSize) {
        UserMessage userMessage = merchantDomainService.getUserMessage(token);
        Sort sort = new Sort(Sort.Direction.DESC,"ctime");
        Page<MerchantConsumer> merchantPage = merchantConsumerDao.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Path<Long> merchantIdPath = root.get("merchantId");
            Path<String> namePath = root.get("name");
            Path<String> cellphonePath = root.get("cellphone");
            Path<Date> timePath = root.get("ctime");
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.equal(merchantIdPath, userMessage.getMerchantId()));
            name.ifPresent(s -> predicateList.add(criteriaBuilder.like(namePath, "%" + s + "%")));
            cellphone.ifPresent(s -> predicateList.add(criteriaBuilder.equal(cellphonePath, s)));
            if (ctimeStart.isPresent() && ctimeEnd.isPresent()) {
                predicateList.add(criteriaBuilder.between(timePath, ctimeStart.get(), ctimeEnd.get()));
            }
            Predicate[] p = new Predicate[predicateList.size()];
            return criteriaBuilder.and(predicateList.toArray(p));
        }, new PageRequest(pageNo, pageSize, sort));
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
        Merchant merchantAdd = new Merchant();
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

    @Transactional
    @Override
    public void deleteMerchantConsumer(String token, long id) {
        checkMerchantConsumer(token, id);
        long walletId = checkMerchantConsumerWallet(id);
        if (walletId != 0L) {
            merchantConsumerWalletDao.deleteById(walletId);
        }
        merchantConsumerDao.deleteById(id);
        consumerDomainService.removeMerchantConsumerCache(id);
    }



    @Override
    public MerchantConsumerRes getMerchantConsumer(String token, long consumerId) {
        MerchantConsumer merchantConsumer = checkMerchantConsumer(token, consumerId);
        MerchantConsumerWallet merchantConsumerWallet = merchantConsumerWalletDao.findByConsumerId(consumerId);
        MerchantConsumerRes merchantConsumerRes = new MerchantConsumerRes();
        merchantConsumerRes.setId(merchantConsumer.getId());
        merchantConsumerRes.setMerchantId(merchantConsumer.getMerchantId());
        merchantConsumerRes.setConsumerName(merchantConsumer.getName());
        merchantConsumerRes.setConsumerCellphone(merchantConsumer.getCellphone());
        merchantConsumerRes.setConsumerEmail(merchantConsumer.getEmail());
        merchantConsumerRes.setConsumerWechat(merchantConsumer.getWechat());
        merchantConsumerRes.setCtime(merchantConsumer.getCtime());
        merchantConsumerRes.setMtime(merchantConsumer.getMtime());
        if (merchantConsumerWallet != null) {
            merchantConsumerRes.setWalletId(merchantConsumerWallet.getId());
            merchantConsumerRes.setBalance(merchantConsumerWallet.getBalance());
            merchantConsumerRes.setWalletCtime(merchantConsumerWallet.getCtime());
            merchantConsumerRes.setWalletMtime(merchantConsumerWallet.getMtime());
        }
        return merchantConsumerRes;
    }

    @Transactional
    @Override
    public MerchantConsumerRes addMerchantConsumer(String token, MerchantConsumerAdd merchantConsumerAdd) {
        UserMessage userMessage = merchantDomainService.getUserMessage(token);
        long merchantId = userMessage.getMerchantId();
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
        merchantConsumer.setEmail(merchantConsumerAdd.getEmail());
        merchantConsumer.setWechat(merchantConsumerAdd.getWechat());
        merchantConsumer.setOperatorCreate(userMessage.getUsername());
        merchantConsumer.setOperatorUpdate(userMessage.getUsername());
        merchantConsumer = merchantConsumerDao.save(merchantConsumer);
        initMerchantConsumerWallet(merchantId, userMessage.getUsername(), merchantConsumer.getId());
        MerchantConsumerRes merchantConsumerRes = new MerchantConsumerRes();
        merchantConsumerRes.setId(merchantConsumer.getId());
        return merchantConsumerRes;
    }



    @Override
    public MerchantConsumerRes updateMerchantConsumer(String token, MerchantConsumerUpdate merchantConsumerUpdate) {
        UserMessage userMessage = merchantDomainService.getUserMessage(token);
        long merchantId = userMessage.getMerchantId();
        MerchantConsumer merchantConsumerOld = merchantConsumerDao.findOne(merchantConsumerUpdate.getId());
        if (merchantConsumerOld == null || !merchantConsumerOld.getMerchantId().equals(merchantId)) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户会员不存在");
        }
        if (!merchantConsumerOld.getName().equals(merchantConsumerUpdate.getName())) {
            MerchantConsumer merchantConsumerCheck = merchantConsumerDao.findByMerchantIdAndName(merchantId, merchantConsumerUpdate.getName());
            if (null != merchantConsumerCheck) {
                throw new BackendException(Code.CODE_EXIST, "商户会员名称[%s]已存在", merchantConsumerUpdate.getName());
            }
        }
        if (!merchantConsumerOld.getCellphone().equals(merchantConsumerUpdate.getCellphone())) {
            MerchantConsumer merchantConsumerCheck = merchantConsumerDao.findByMerchantIdAndCellphone(merchantId, merchantConsumerUpdate.getCellphone());
            if (null != merchantConsumerCheck) {
                throw new BackendException(Code.CODE_EXIST, "商户会员手机号[%s]已存在", merchantConsumerUpdate.getCellphone());
            }
        }
        merchantConsumerOld.setId(merchantConsumerUpdate.getId());
        merchantConsumerOld.setName(merchantConsumerUpdate.getName());
        merchantConsumerOld.setCellphone(merchantConsumerUpdate.getCellphone());
        merchantConsumerOld.setEmail(merchantConsumerUpdate.getEmail());
        merchantConsumerOld.setWechat(merchantConsumerUpdate.getWechat());
        merchantConsumerOld.setOperatorUpdate(userMessage.getUsername());
        merchantConsumerDao.save(merchantConsumerOld);
        consumerDomainService.removeMerchantConsumerCache(merchantConsumerUpdate.getId());
        MerchantConsumerRes merchantConsumerRes = new MerchantConsumerRes();
        merchantConsumerRes.setId(merchantConsumerUpdate.getId());
        return merchantConsumerRes;
    }

    @Transactional
    @Override
    public MerchantConsumerWalletRes rechargeMerchantConsumerBalance(String token, ConsumerBalanceReCharge consumerBalanceReCharge) {
        UserMessage userMessage = merchantDomainService.getUserMessage(token);
        long merchantId = userMessage.getMerchantId();
        MerchantConsumerWallet merchantConsumerWalletOld = getMerchantConsumerWalletOld(merchantId, consumerBalanceReCharge);
        long balanceOld = merchantConsumerWalletOld.getBalance();
        long afterBalance = balanceOld + consumerBalanceReCharge.getAmount();
        merchantConsumerWalletOld.setBalance(afterBalance);
        merchantConsumerWalletOld.setOperatorUpdate(userMessage.getUsername());
        merchantConsumerWalletOld = merchantConsumerWalletDao.save(merchantConsumerWalletOld);
        WalletAddTransaction walletAddTransaction = new WalletAddTransaction();
        walletAddTransaction.setConsumerId(merchantConsumerWalletOld.getConsumerId());
        walletAddTransaction.setWalletId(merchantConsumerWalletOld.getId());
        walletAddTransaction.setBeforeBalance(balanceOld);
        walletAddTransaction.setAddAmount(consumerBalanceReCharge.getAmount());
        walletAddTransaction.setAfterBalance(afterBalance);
        walletAddTransaction.setOperatorCreate(userMessage.getUsername());
        walletAddTransaction.setOperatorUpdate(userMessage.getUsername());
        walletAddTransaction.setRemark(consumerBalanceReCharge.getRemark());
        walletAddTransactionDao.save(walletAddTransaction);
        MerchantConsumerWalletRes merchantConsumerWalletRes = new MerchantConsumerWalletRes();
        merchantConsumerWalletRes.setId(merchantConsumerWalletOld.getId());
        merchantConsumerWalletRes.setBalance(merchantConsumerWalletOld.getBalance());
        return merchantConsumerWalletRes;
    }

    /**
     * 获取老的会员余额
     *
     * @param merchantId
     * @param consumerBalanceReCharge
     * @return
     */
    private MerchantConsumerWallet getMerchantConsumerWalletOld(long merchantId, ConsumerBalanceReCharge consumerBalanceReCharge) {
        MerchantConsumer merchantConsumerOld = merchantConsumerDao.findOne(consumerBalanceReCharge.getConsumerId());
        if (merchantConsumerOld == null || !merchantConsumerOld.getMerchantId().equals(merchantId)) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户会员不存在");
        }
        MerchantConsumerWallet merchantConsumerWalletOld = merchantConsumerWalletDao.findByConsumerId(consumerBalanceReCharge.getConsumerId());
        if (merchantConsumerWalletOld == null
                || !merchantConsumerWalletOld.getConsumerId().equals(consumerBalanceReCharge.getConsumerId())) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户会员钱包不存在");
        }
        return merchantConsumerWalletOld;
    }

    @Transactional
    @Override
    public MerchantConsumerWalletRes reduceMerchantConsumerBalance(String token, ConsumerBalanceReCharge consumerBalanceReCharge) {
        UserMessage userMessage = merchantDomainService.getUserMessage(token);
        long merchantId = userMessage.getMerchantId();
        MerchantConsumerWallet merchantConsumerWalletOld = getMerchantConsumerWalletOld(merchantId, consumerBalanceReCharge);
        long balanceOld = merchantConsumerWalletOld.getBalance();
        if (balanceOld < consumerBalanceReCharge.getAmount()) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户会员钱包余额不足");
        }
        long afterBalance = balanceOld - consumerBalanceReCharge.getAmount();
        merchantConsumerWalletOld.setBalance(afterBalance);
        merchantConsumerWalletOld.setOperatorUpdate(userMessage.getUsername());
        merchantConsumerWalletOld = merchantConsumerWalletDao.save(merchantConsumerWalletOld);

        MerchantConsumerWalletRes merchantConsumerWalletRes = new MerchantConsumerWalletRes();
        merchantConsumerWalletRes.setId(merchantConsumerWalletOld.getId());
        merchantConsumerWalletRes.setBalance(merchantConsumerWalletOld.getBalance());
        return merchantConsumerWalletRes;
    }

    @Override
    public Page<WalletTransactionRes> listWalletTransactionPaging(long consumerId, Optional<Date> ctimeStart, Optional<Date> ctimeEnd, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC,"ctime");
        Page<WalletAddTransaction> walletAddTransactionPage = walletAddTransactionDao.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Path<Long> consumerIdPath = root.get("consumerId");
            Path<Date> timePath = root.get("ctime");
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.equal(consumerIdPath, consumerId));
            if (ctimeStart.isPresent() && ctimeEnd.isPresent()) {
                predicateList.add(criteriaBuilder.between(timePath, ctimeStart.get(), ctimeEnd.get()));
            }
            Predicate[] p = new Predicate[predicateList.size()];
            return criteriaBuilder.and(predicateList.toArray(p));
        }, new PageRequest(pageNo, pageSize, sort));
        return new PageImpl<>(
                walletAddTransactionPage.getContent()
                        .stream()
                        .map(this::buildWalletTransactionRes)
                        .collect(Collectors.toList()),
                walletAddTransactionPage.previousPageable(), walletAddTransactionPage.getTotalElements());
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
    private MerchantConsumerWallet initMerchantConsumerWallet(long merchantId, String username, long consumerId) {
        MerchantConsumerWallet merchantConsumerWallet = new MerchantConsumerWallet();
        merchantConsumerWallet.setMerchantId(merchantId);
        merchantConsumerWallet.setConsumerId(consumerId);
        merchantConsumerWallet.setBalance(0L);
        merchantConsumerWallet.setOperatorCreate(username);
        merchantConsumerWallet.setOperatorUpdate(username);
        return merchantConsumerWalletDao.save(merchantConsumerWallet);
    }

    /**
     * 校验token商户会员
     *
     * @param token
     * @param id
     * @return
     */
    private MerchantConsumer checkMerchantConsumer(String token, long id) {
        long merchantId = merchantDomainService.getUserMessage(token).getMerchantId();
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

    /**
     * build MerchantConsumerRes
     *
     * @param walletAddTransaction
     * @return
     */
    private WalletTransactionRes buildWalletTransactionRes(WalletAddTransaction walletAddTransaction) {
        WalletTransactionRes walletTransactionRes = new WalletTransactionRes();
        walletTransactionRes.setConsumerId(walletAddTransaction.getConsumerId());
        walletTransactionRes.setWalletId(walletAddTransaction.getWalletId());
        walletTransactionRes.setAddAmount(walletAddTransaction.getAddAmount());
        walletTransactionRes.setBeforeBalance(walletAddTransaction.getBeforeBalance());
        walletTransactionRes.setAfterBalance(walletAddTransaction.getAfterBalance());
        walletTransactionRes.setOperatorCreate(walletAddTransaction.getOperatorCreate());
        walletTransactionRes.setOperatorUpdate(walletAddTransaction.getOperatorUpdate());
        walletTransactionRes.setRemark(walletAddTransaction.getRemark());
        walletTransactionRes.setCtime(walletAddTransaction.getCtime());
        walletTransactionRes.setMtime(walletAddTransaction.getMtime());
        return walletTransactionRes;
    }
}
