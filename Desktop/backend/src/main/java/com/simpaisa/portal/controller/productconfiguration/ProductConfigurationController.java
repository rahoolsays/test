package com.simpaisa.portal.controller.productconfiguration;

import com.simpaisa.portal.entity.mysql.productconfiguration.ProductConfiguration;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.repository.interfaces.ProductConfigurationRepository;
import com.simpaisa.portal.service.product.ProductService;
import com.simpaisa.portal.service.productconfiguration.ProductConfigurationService;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("productconfiguration")
@CrossOrigin(origins = "*")
public class ProductConfigurationController {

    @Autowired
    private ProductConfigurationService productConfigurationService;


    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ProductConfiguration productConfig){
        ProductConfiguration result = null;

        try{

            result = productConfigurationService.save(productConfig);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<ProductConfiguration>(result, HttpStatus.OK);
    }

    @PostMapping("/findByProductId")
    public Page<ProductConfiguration> findByMerchantId(@RequestParam("productId") Long productId, @RequestParam("orderBy") String orderBy, @RequestParam("direction") String direction,
                                                       @RequestParam("pageNo") int pageNo, @RequestParam("size") int size){
        Page<ProductConfiguration> productConfigurations = null;
        try{
            productConfigurations = productConfigurationService.findByProductId(productId,orderBy,direction, pageNo, size);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return productConfigurations;
    }

    @GetMapping("")
    public ResponseEntity<?> findById(@RequestParam("id") long id){
        ProductConfiguration productConfig = null;
        try{
            productConfig = productConfigurationService.findById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<ProductConfiguration>(productConfig, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteById(@RequestParam("id") Long id ){
        try{

            productConfigurationService.deleteById(id);

            return new ResponseEntity<LinkedHashMap<String, Object>>(Utility.getResponse(Responses.SUCCESS), HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<LinkedHashMap<String, Object>>(Utility.getResponse(Responses.SYSTEM_ERROR), HttpStatus.OK);
        }
    }
}
