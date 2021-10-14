package com.simpaisa.portal.service.reporting.payments;

import com.simpaisa.portal.entity.mysql.reporting.payments.Payment;
import com.simpaisa.portal.repository.interfaces.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsService {
    @Autowired
    PaymentsRepository paymentsRepository;

    public List<Payment> successPayment(Long merchantId,String from_date,String to_date,String operatorID){
        List<Payment> successPayments=null;
        try{
            successPayments=paymentsRepository.successPayments(merchantId,from_date,to_date,operatorID);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return successPayments;
    }

}
