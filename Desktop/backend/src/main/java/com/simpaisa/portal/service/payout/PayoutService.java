package com.simpaisa.portal.service.payout;

import com.simpaisa.portal.entity.mongo.payout.Payout;
import com.simpaisa.portal.repository.interfaces.PayoutRepository;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class PayoutService {
    @Autowired
    private PayoutRepository payoutRepository;

    public Payout save(Payout product) {
        Payout result = null;
        try{
            if(product!=null){
                if(product.getId()!=null ){
                    product.setUpdatedDate(new Date());
                }else {
                    product.setCreatedDate(new Date());
                }
            }
            result = payoutRepository.save(product);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  result;
    }

    public Payout findByEmail(HashMap<String, String> data) {
        Payout result = null;
        try{
            if(data.containsKey(Utility.EMAIL)){
                result = payoutRepository.findByEmail(data.get(Utility.EMAIL));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
