package com.simpaisa.portal.repository.customer;

import com.simpaisa.portal.entity.mysql.customer.CustomerList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerListRepository extends PagingAndSortingRepository<CustomerList, String> {
    Page<CustomerList> findByMerchantIdAndActiveNot(Long merchantId,Integer active, Pageable pageable);
}
