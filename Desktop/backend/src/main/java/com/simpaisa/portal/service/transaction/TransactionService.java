package com.simpaisa.portal.service.transaction;

import com.simpaisa.portal.entity.mysql.transaction.Transaction;
import com.simpaisa.portal.repository.interfaces.TransactionRepository;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public Page<Transaction> getTransaction(Long merchantId, Date start, Date end, String status, String orderBy, String direction , int pageNo, int size){
        Pageable pageable = null;

        Page<Transaction> transactions = null;
        if(direction.equalsIgnoreCase(Utility.SQL_DESC)){
            pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, orderBy));
        }else {
            pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.ASC, orderBy));
        }

        try{
            if(status.equals("ALL")) {
                transactions = transactionRepository.findByMerchantIdAndCreatedDateBetween(merchantId,start,end, pageable);
            }else if(status.equals("SUCCESS")){
                transactions = transactionRepository.findByMerchantIdAndStatusAndCreatedDateBetween(merchantId, 1, start, end, pageable);
            }else if(status.equals("FAIL")){
                transactions = transactionRepository.findByMerchantIdAndStatusAndCreatedDateBetween(merchantId, 0, start, end, pageable);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  transactions;


    }

    public Transaction findById(long id) {
        Transaction transaction = null;
        try{
            transaction = transactionRepository.findByTransactionId(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return transaction;
    }
}
