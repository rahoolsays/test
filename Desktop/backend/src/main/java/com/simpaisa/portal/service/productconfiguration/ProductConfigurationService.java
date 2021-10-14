package com.simpaisa.portal.service.productconfiguration;

import com.simpaisa.portal.entity.mysql.productconfiguration.ProductConfiguration;
import com.simpaisa.portal.repository.interfaces.ProductConfigurationRepository;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductConfigurationService {
    @Autowired
    private ProductConfigurationRepository psRepository;

    public Page<ProductConfiguration> findByProductId(Long productId, String orderBy, String direction, int pageNo, int size) {
        Page<ProductConfiguration> productConfigurations = null;
        Pageable pageable = null;
        try{
            if(direction.equalsIgnoreCase(Utility.SQL_DESC)){
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, orderBy));
            }else{
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.ASC, orderBy));
            }
            productConfigurations = psRepository.findByProductId(productId, pageable);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return productConfigurations;
    }


    public ProductConfiguration save(ProductConfiguration productConfig) {
        ProductConfiguration result = null;
        System.out.println(productConfig);
        try{
            if(productConfig!=null){
                if(productConfig.getId()!=null && productConfig.getId()>0l){
                    productConfig.setUpdatedDate(new Date());
                }else {
                    productConfig.setCreatedDate(new Date());
                }
            }
            result = psRepository.save(productConfig);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  result;
    }

    public ProductConfiguration findById(long id) {
        ProductConfiguration productConfig = null;
        try{
            productConfig = psRepository.findById(id).orElse(null);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return productConfig;
    }


    public void deleteById(Long id){
        try{
            psRepository.deleteById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
