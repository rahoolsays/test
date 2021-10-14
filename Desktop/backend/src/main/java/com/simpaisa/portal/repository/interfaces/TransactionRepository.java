package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mysql.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    Page<Transaction> findByMerchantIdAndCreatedDateBetween(Long merchantId, Date startDate, Date endDate, Pageable pageable);
    Page<Transaction> findByMerchantIdAndStatusAndCreatedDateBetween(Long merchantId, int status, Date startDate, Date endDate, Pageable pageable);
    Transaction findByTransactionId(long id);
}
