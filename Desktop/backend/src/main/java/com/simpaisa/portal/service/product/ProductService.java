package com.simpaisa.portal.service.product;

import com.simpaisa.portal.entity.mongo.kyc.KYC;
import com.simpaisa.portal.entity.mysql.merchant.Merchant;
import com.simpaisa.portal.entity.mysql.product.Product;
import com.simpaisa.portal.entity.mysql.product.ProductView;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.repository.interfaces.ProductRepo;
import com.simpaisa.portal.repository.interfaces.ProductRepository;
import com.simpaisa.portal.service.KycService;
import com.simpaisa.portal.service.MerchantService;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    MerchantService merchantService;

    @Autowired
    KycService kycService;

    public Product save(Product product) {
        Product result = null;
        try{
            if(product!=null){
                if(product.getProductID()!=null && product.getProductID()>0l){
                    product.setUpdatedDate(new Date());
                }else {
                    product.setCreatedDate(new Date());
                }
            }
            result = productRepository.save(product);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  result;
    }

    public Product findById(long id) {
        Product product = null;
        try{
            product = productRepository.findById(id).orElse(null);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return product;
    }


    public void deleteById(Long id){
        try{
            productRepository.deleteById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Page<Product> findByMerchantId(Long merchantId, String orderBy, String direction, int pageNo, int size) {
        Page<Product> products = null;
        Pageable pageable = null;
        try{
            if(direction.equalsIgnoreCase(Utility.SQL_DESC)){
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, orderBy));
            }else{
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.ASC, orderBy));
            }
            products = productRepository.findByMerchantId(merchantId, pageable);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return products;
    }

    public Page<Product> findByOnLive(int onLive, String orderBy, String direction, int pageNo, int size) {
        Page<Product> products = null;
        Pageable pageable = null;
        try{
            if(direction.equalsIgnoreCase(Utility.SQL_DESC)){
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, orderBy));
            }else{
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.ASC, orderBy));
            }
            products = productRepository.findByOnLive(onLive,pageable);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return products;
    }

    public Page<Product> findAll(String orderBy, String direction, int pageNo, int size) {
        Page<Product> products = null;
        Pageable pageable = null;
        try{
            if(direction.equalsIgnoreCase(Utility.SQL_DESC)){
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, orderBy));
            }else{
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.ASC, orderBy));
            }
            products = productRepository.findAll(pageable);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return products;
    }

    public List<ProductView> getProductIds(Long merchantId){
        List<ProductView> productViews = null;
        try{
            productViews = productRepository.findByMerchantId(merchantId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return productViews;
    }

    public List<Product> getAllProducts(){
        List<Product> productViews = null;
        try{
            productViews= (List<Product>) productRepository.findAll();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return productViews;
    }


    public Map<String, Object> goLive(HashMap<String, String> data) {
        Map<String, Object> response = null;
        Product productLive = null;
        Product productStaging = null;
        try{
            //find if merchant with same email already exists in live
//            merchantLive = merchantRepository.findByEmail(data.get(Utility.EMAIL),Utility.JDBC_LIVE);
//            if(merchantLive == null){
                //fetch merchantInfo from staging for email and id
                productStaging = productRepo.findById(Long.valueOf(data.get("productID")),Utility.JDBC_STAGING);
                if(productStaging!=null){
                    //save merchant in live
                    productLive = productStaging;

                    Merchant merchant=merchantService.getByMerchantId(productLive.getMerchantId());
                    HashMap<String,String> forEmail=new LinkedHashMap<>();
                    forEmail.put("email",merchant.getEmail());
                    KYC kyc=kycService.findByEmail(forEmail);
                    productLive.setMerchantId(kyc.getMerchantId());

                    System.out.println("productLive == " + productLive.toString());
                    productRepo.insert(productLive, Utility.JDBC_LIVE);
                    com.simpaisa.portal.entity.mongo.Product productMongo =productRepo.findByIdMongo(Utility.JDBC_LIVE);
                    productRepo.save(productMongo);
                    response =  Utility.getResponse(Responses.SUCCESS);
                }
//            }else{
//                response =  Utility.getResponse(Responses.MERCHANT_ALREADY_EXISTS);
//            }
        }catch (Exception ex){
            ex.printStackTrace();
            response = Utility.getResponse(Responses.SYSTEM_ERROR);
        }
        return response;
    }




}
