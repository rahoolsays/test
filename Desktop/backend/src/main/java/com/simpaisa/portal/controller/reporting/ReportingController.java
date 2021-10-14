package com.simpaisa.portal.controller.reporting;

import com.simpaisa.portal.entity.mysql.reporting.Recursion;
import com.simpaisa.portal.entity.mysql.reporting.RevenueTotal;
import com.simpaisa.portal.service.reporting.NewSubscribersService;
import com.simpaisa.portal.service.reporting.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reporting2")
@CrossOrigin(origins = "*")
public class ReportingController {
    @Autowired
    ReportingService reportingService;
    @Autowired
    NewSubscribersService newSubscribersService;

    @GetMapping(value = "newSubscribers", produces = "application/json")
    public List<Recursion> getNewSubscribers(@RequestParam("merchantId") long merchantId,@RequestParam("from") String from,@RequestParam("to") String to,@RequestParam("operatorID") String operatorID){
        List<Recursion> response = null;
        try{
            response=newSubscribersService.newSubscribers(merchantId,from,to,operatorID);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }


    @GetMapping(value = "newTrials", produces = "application/json")
    public List<Recursion> getNewTrials(@RequestParam("merchantId") long merchantId,@RequestParam("from") String from,@RequestParam("to") String to,@RequestParam("operatorID") String operatorID){
        List<Recursion> response = null;
        try{
            response=reportingService.newTrials(merchantId,from,to,operatorID);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

}


