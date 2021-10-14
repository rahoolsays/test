package com.simpaisa.portal.service.reporting;

import com.simpaisa.portal.entity.mysql.reporting.Recursion;
import com.simpaisa.portal.entity.mysql.reporting.RevenueTotal;
import com.simpaisa.portal.repository.interfaces.ReportingRepository;
import com.simpaisa.portal.repository.reporting.ReportingRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingService {
    @Autowired
    ReportingRepository reportingRepo;
    @Autowired
    ReportingRepositoryImpl reportingRepoImpl;
    public RevenueTotal findByMerchantId(int merchantId){
        RevenueTotal totalRevenue=null;
        try{
            totalRevenue=reportingRepoImpl.findByMerchantId(merchantId);
        }
        catch (Exception e){
        }
        return totalRevenue;
    }

    public RevenueTotal Revenue(){
        RevenueTotal totalRevenue=null;
        try{
            totalRevenue=reportingRepoImpl.Revenue();
        }
        catch (Exception e){
        }
        return totalRevenue;
    }

    public List<Recursion> newTrials(Long merchantId, String from_date, String to_date, String operatorID){
        List<Recursion> newSubscribersList=null;
        try{
            newSubscribersList=reportingRepo.newTrialsList(merchantId,from_date,to_date,operatorID);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return newSubscribersList;
    }
}
