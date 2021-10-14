package com.simpaisa.portal.service.revenue;


import com.simpaisa.portal.entity.mongo.revenue.RevenueShare;
import com.simpaisa.portal.repository.interfaces.RevenueSharesRepository;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RevenueShareService {
    @Autowired
    private RevenueSharesRepository revenueSharesssRepository;


    public Page<RevenueShare> findAll(String orderBy, String direction, int pageNo, int size) {
        Page<RevenueShare> revenueShares = null;
        Pageable pageable = null;
        try{
            if(direction.equalsIgnoreCase(Utility.SQL_DESC)){
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, orderBy));
            }else{
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.ASC, orderBy));
            }

            revenueShares = revenueSharesssRepository.findAll(pageable);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return revenueShares;
    }


//    public void save(RevenueShare revenueShare){
//        revenueSharesssRepository.save(revenueShare);
//    }

    public void deleteById(String id){
        try{
            revenueSharesssRepository.deleteById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public RevenueShare findById(String id) {
        RevenueShare result = null;
        try{
            result = revenueSharesssRepository.findById(id).orElse(null);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public RevenueShare save(RevenueShare revenueShare) {
        RevenueShare result = null;
        try{
            if(revenueShare!=null){
                if(revenueShare.getId()!=null){
                    revenueShare.setUpdatedDate(new Date());
                }else {
                    revenueShare.setCreatedDate(new Date());
                }
            }
            result = revenueSharesssRepository.save(revenueShare);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  result;
    }
}
