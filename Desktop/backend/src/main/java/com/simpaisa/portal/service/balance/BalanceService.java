package com.simpaisa.portal.service.balance;

import com.simpaisa.portal.entity.mysql.balance.MerchantBalance;
import com.simpaisa.portal.repository.balance.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class BalanceService {

    @Autowired
    BalanceRepository balanceRepository;

    public MerchantBalance getMerchantBalance(long merchantId, Timestamp fromDate, Timestamp toDate){

        MerchantBalance merchantBalance = null;
        try {

            merchantBalance = balanceRepository.getMerchantBalance(merchantId, fromDate, toDate);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return merchantBalance;
    }
}
