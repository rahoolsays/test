package com.simpaisa.portal.service.reporting;

import com.simpaisa.portal.entity.mysql.reporting.Recursion;
import com.simpaisa.portal.repository.interfaces.NewSubscribersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewSubscribersService {
    @Autowired
    NewSubscribersRepository newSubscribersRepository;

    public List<Recursion> newSubscribers(Long merchantId,String from_date,String to_date,String operatorID){
        List<Recursion> newSubscribersList=null;
        try{
            newSubscribersList=newSubscribersRepository.newSubscribersList(merchantId,from_date,to_date,operatorID);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return newSubscribersList;
    }

}
