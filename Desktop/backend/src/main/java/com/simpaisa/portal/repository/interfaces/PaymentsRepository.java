package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mysql.reporting.payments.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PaymentsRepository {
    public List<Payment> successPayments(long merchantId,String from_date, String to_date,String operatorID);
}
