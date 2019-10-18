package com.lihebin.manage.service;

import com.lihebin.manage.bean.Code;
import com.lihebin.manage.bean.OrderNew;
import com.lihebin.manage.bean.OrderRes;
import com.lihebin.manage.bean.UserMessage;
import com.lihebin.manage.dao.manage.MerchantConsumerWalletDao;
import com.lihebin.manage.dao.manage.OrderDao;
import com.lihebin.manage.dao.manage.TransactionDao;
import com.lihebin.manage.exception.BackendException;
import com.lihebin.manage.model.MerchantConsumer;
import com.lihebin.manage.model.MerchantConsumerWallet;
import com.lihebin.manage.model.Order;
import com.lihebin.manage.service.common.ConsumerDomainService;
import com.lihebin.manage.service.common.MerchantDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lihebin on 2019/6/25.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MerchantDomainService merchantDomainService;

    @Autowired
    private ConsumerDomainService consumerDomainService;

    @Autowired
    private MerchantConsumerWalletDao merchantConsumerWalletDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public OrderRes pay(String token, OrderNew orderNew) {
        UserMessage userMessage = merchantDomainService.getUserMessage(token);
        long merchantId = userMessage.getMerchantId();
        MerchantConsumer merchantConsumer = consumerDomainService.getMerchantConsumer(orderNew.getConsumerId());
        if (!merchantConsumer.getMerchantId().equals(merchantId)) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户会员不存在");
        }
        MerchantConsumerWallet merchantConsumerWalletOld = consumerDomainService.getMerchantConsumerWallet(orderNew.getConsumerId());
        long balanceOld = merchantConsumerWalletOld.getBalance();
        long reduceBalance = orderNew.getOriginalAmount() - orderNew.getDiscount();
        if (balanceOld < reduceBalance) {
            throw new BackendException(Code.CODE_NOT_EXIST, "商户会员钱包余额不足");
        }



        return null;
    }


    /**
     * 变更会员余额
     *
     * @param merchantConsumerWalletOld
     * @param balance
     * @param username
     */
    private void updateMerchantConsumerWalletHandle(MerchantConsumerWallet merchantConsumerWalletOld, long balance, String username) {
        merchantConsumerWalletOld.setBalance(balance);
        merchantConsumerWalletOld.setOperatorUpdate(username);
        merchantConsumerWalletDao.save(merchantConsumerWalletOld);
    }


//    private Order createOrder(OrderNew orderNew, long merchantId, String username) {
//        Order order = new Order();
//
//    }
}
