package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mongo.payout.Payout;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayoutRepository extends PagingAndSortingRepository<Payout, String> {
    public Payout findByEmail(String email);
}
