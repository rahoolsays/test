package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mysql.productconfiguration.ProductConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductConfigurationRepository extends PagingAndSortingRepository<ProductConfiguration, Long> {
    Page<ProductConfiguration> findByProductId(Long productId, Pageable pageable);
}
