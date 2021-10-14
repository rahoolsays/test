package com.simpaisa.portal.controller.reporting.payments;

import com.simpaisa.portal.entity.mysql.reporting.payments.Payment;
import com.simpaisa.portal.service.reporting.payments.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reporting/v2")
@CrossOrigin(origins = "*")
public class PaymentsController {

    @Autowired
    PaymentsService paymentsService;

    @GetMapping(value = "payments", produces = "application/json")
    public List<Payment> successfulPayments(@RequestParam("merchantId") long merchantId,@RequestParam("from") String from,@RequestParam("to") String to,@RequestParam("operatorID") String operatorID){
        List<Payment> allPayments=null;
        try{
            allPayments=paymentsService.successPayment(merchantId,from,to,operatorID);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return allPayments;

    }


}
