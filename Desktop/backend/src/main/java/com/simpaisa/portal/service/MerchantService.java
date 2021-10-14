package com.simpaisa.portal.service;

import com.simpaisa.portal.entity.mysql.merchant.Merchant;
import com.simpaisa.portal.entity.mysql.merchant.MerchantIdAndName;
import com.simpaisa.portal.entity.mysql.product.Product;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.repository.interfaces.MerchantRepository;
import com.simpaisa.portal.utility.Utility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    public Map<String, Object> findByEmail(HashMap<String, String> data) {
        Map<String, Object> response = null;
        Merchant merchant = null;

        try{
            if(data.containsKey(Utility.EMAIL) && !data.get(Utility.EMAIL).trim().equals("")){

                if(data.containsKey(Utility.ENVIRONMENT) && Integer.valueOf(data.get(Utility.ENVIRONMENT)) == Utility.JDBC_LIVE){
                    merchant = merchantRepository.findByEmail(data.get(Utility.EMAIL),Utility.JDBC_LIVE);
                }else {
                    merchant = merchantRepository.findByEmail(data.get(Utility.EMAIL),Utility.JDBC_STAGING);
                }


                System.out.println("merchant == " + merchant);
                if(merchant!=null){
                    response = getResponse(Responses.SUCCESS);
                    response.put(Utility.MERCHANT_DETAIL, merchant);
                }else {
                    response = getResponse(Responses.NO_DATA);
                }
            }else {
                response = getResponse(Responses.INVALID_EMAIL);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            response = getResponse(Responses.SYSTEM_ERROR);
        }
        return response;
    }

    public Merchant findByEmail(String email){
        Merchant merchant = null;
        try{
            if(StringUtils.isNotEmpty(email)){
                merchant = merchantRepository.findByEmail(email, Utility.JDBC_STAGING);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return  merchant;
    }


    private Map<String, Object> getResponse(Responses responses){
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(Utility.STATUS, responses.getStatus());
        response.put(Utility.MESSAGE, responses.getMessage());
        return response;
    }

    public Map<String, Object> updatePostback(HashMap<String, String> data) {
        Map<String, Object> response = null;
        Merchant merchant = null;

        try {
        merchant = merchantRepository.findByEmail(data.get(Utility.EMAIL),Utility.JDBC_STAGING);
        if(merchant!=null){
            merchantRepository.updatePostBack(merchant.getId(), data.get(Utility.POSTBACK_URL));
           return getResponse(Responses.SUCCESS);
        }
        }catch (Exception ex){
            ex.printStackTrace();
            response = getResponse(Responses.SYSTEM_ERROR);
        }
        response = getResponse(Responses.SYSTEM_ERROR);
        return response;
    }


    public Map<String, Object> updateLogo(HashMap<String, String> data) {
        Map<String, Object> response = null;

        try {
            merchantRepository.updateLogo(data.get(Utility.MERCHANT_ID), data.get(Utility.LOGO_URL));
            return getResponse(Responses.SUCCESS);

        }catch (Exception ex){
            ex.printStackTrace();
            response = getResponse(Responses.SYSTEM_ERROR);
        }
        response = getResponse(Responses.SYSTEM_ERROR);
        return response;
    }


    public List<MerchantIdAndName> getAllMerchants(){
        List<MerchantIdAndName> merchantIdAndNames = null;
        try{
            merchantIdAndNames= merchantRepository.getAllMerchants();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return merchantIdAndNames;
    }

    public Map<String, Object> goLive(HashMap<String, String> data) {
        Map<String, Object> response = null;
        Merchant merchantLive = null;
        Merchant merchantStaging = null;
        try{
            //find if merchant with same email already exists in live
            merchantLive = merchantRepository.findByEmail(data.get(Utility.EMAIL),Utility.JDBC_LIVE);
            if(merchantLive == null){
                //fetch merchantInfo from staging for email and id
                merchantStaging = merchantRepository.findById(Long.valueOf(data.get(Utility.MERCHANT_ID)), data.get(Utility.EMAIL),Utility.JDBC_STAGING);
                if(merchantStaging!=null){
                    //save merchant in live
                    merchantLive = merchantStaging;

                    System.out.println("merchantLive == " + merchantLive.toString());
                    merchantRepository.insert(merchantLive, Utility.JDBC_LIVE);
                    response =  Utility.getResponse(Responses.SUCCESS);
                }
            }else{
                response =  Utility.getResponse(Responses.MERCHANT_ALREADY_EXISTS);
            }

        }catch (Exception ex){
            ex.printStackTrace();
            response = Utility.getResponse(Responses.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * This method is used to get
     * merchant info against the merchantId
     * @param merchantId
     * @return
     */
    public Merchant isMerchantValid(long merchantId) {
        Merchant merchant = null;
        try {
            merchant=merchantRepository.isMerchantValid(merchantId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return merchant;
    }

    public Merchant getByMerchantId(int merchantId) {
        Merchant merchant = null;
        try{

            merchant = merchantRepository.findById(merchantId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return merchant;
    }

    public Merchant getByMerchantId(Long merchantId) {
        Merchant merchant = null;
        try{

            merchant = merchantRepository.findById(merchantId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return merchant;
    }
}
