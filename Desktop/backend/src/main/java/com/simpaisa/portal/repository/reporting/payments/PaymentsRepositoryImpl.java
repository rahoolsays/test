package com.simpaisa.portal.repository.reporting.payments;

import com.simpaisa.portal.entity.mysql.reporting.payments.Payment;
import com.simpaisa.portal.entity.mysql.reporting.payments.PaymentMapper;
import com.simpaisa.portal.repository.interfaces.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentsRepositoryImpl implements PaymentsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String FETCH_SUCCESS_PAYMENTS = "select * from paymentlogs where merchantID=? and (date(chargedOn) between ? and ?) and operatorID=?";

    @Override
    public List<Payment> successPayments(long merchantId, String from_date, String to_date,String operatorID) {
        List<Payment> successPayments=null;
        try{
            RowMapper<Payment> paymentRowMapper=new PaymentMapper();
            if(operatorID.equals("all")){
                FETCH_SUCCESS_PAYMENTS = "select * from paymentlogs where merchantID=? and (date(chargedOn) between ? and ?)";
                successPayments = jdbcTemplate.query(FETCH_SUCCESS_PAYMENTS, new Object[]{merchantId, from_date,to_date}, paymentRowMapper);
            }
            else{
                FETCH_SUCCESS_PAYMENTS = "select * from paymentlogs where merchantID=? and (date(chargedOn) between ? and ?) and operatorID=?";
                successPayments = jdbcTemplate.query(FETCH_SUCCESS_PAYMENTS, new Object[]{merchantId, from_date,to_date,operatorID}, paymentRowMapper);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return successPayments;
    }
}
