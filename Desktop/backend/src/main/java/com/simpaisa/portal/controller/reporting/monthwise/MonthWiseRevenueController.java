package com.simpaisa.portal.controller.reporting.monthwise;

import com.simpaisa.portal.entity.mysql.reporting.RevenueTotal;
import com.simpaisa.portal.entity.mysql.reporting.monthwise.MonthWiseRevenue;
import com.simpaisa.portal.service.reporting.ReportingService;
import com.simpaisa.portal.service.reporting.monthwise.MonthWiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reporting/v1")
@CrossOrigin(origins = "*")
public class MonthWiseRevenueController {

    @Autowired
    private MonthWiseService monthWiseService;
    @Autowired
    ReportingService reportingService;

    @GetMapping(value = "monthWise", produces = "application/json")
    public List<MonthWiseRevenue> fetchMonthWise(@RequestParam("merchantId") long merchantId, @RequestParam("numberOfMonth") int numberOfMonth){
        List<MonthWiseRevenue> monthWiseRevenues = null;
        try{
            monthWiseRevenues = monthWiseService.fetchMonthWiseRevenue(merchantId, numberOfMonth);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return monthWiseRevenues;
    }

    @GetMapping(value = "monthWiseALL", produces = "application/json")
    public List<MonthWiseRevenue> fetchMonthWise(@RequestParam("numberOfMonth") int numberOfMonth){
        List<MonthWiseRevenue> monthWiseRevenues = null;
        try{
            monthWiseRevenues = monthWiseService.fetchMonthWiseRevenueALL(numberOfMonth);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return monthWiseRevenues;
    }


    @GetMapping("/total")
    public ResponseEntity findByMerchantId(@RequestParam("id") int merchantID ){
        RevenueTotal response = null;
        try{
            response=reportingService.findByMerchantId(merchantID);
            return new ResponseEntity<RevenueTotal>(response, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<RevenueTotal>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/totalRevenue")
    public ResponseEntity Revenue(){
        RevenueTotal response = null;
        try{
            response=reportingService.Revenue();
            return new ResponseEntity<RevenueTotal>(response, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<RevenueTotal>(response, HttpStatus.OK);
        }
    }
}
