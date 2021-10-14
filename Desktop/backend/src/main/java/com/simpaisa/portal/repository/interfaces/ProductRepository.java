package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mysql.operator.OperatorIdAndName;
import com.simpaisa.portal.entity.mysql.product.Product;
import com.simpaisa.portal.entity.mysql.product.ProductView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findByMerchantId(long id, Pageable pageable);
    List<ProductView> findByMerchantId(long id);
    Page<Product> findByOnLive(int onLive, Pageable pageable);

}
