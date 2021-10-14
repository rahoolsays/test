package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mongo.revenue.RevenueShare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueSharesRepository extends MongoRepository<RevenueShare, String > {
    Page<RevenueShare> findAll(Pageable pageable);
}
