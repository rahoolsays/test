package com.simpaisa.portal.entity.mysql.reporting.payments;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMapper implements RowMapper<Payment> {

    @Override
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Payment payment=null;
        try{
            payment=new Payment();
            payment.setPaymentID(rs.getInt("paymentID"));
            payment.setAmount(rs.getDouble("amount"));
            payment.setAcr(rs.getString("acr"));
            payment.setChargedOn(rs.getString("chargedOn"));
            payment.setMerchantID(rs.getInt("merchantID"));
            payment.setMobileNo(rs.getString("mobileNo"));
            payment.setOperatorID(rs.getInt("operatorID"));
            payment.setChargedOnStr(rs.getString("chargedOnStr"));
            payment.setFirstCharge(rs.getInt("firstCharge"));
            payment.setProcessed(rs.getInt("processed"));
            payment.setRecursionID(rs.getInt("recursionID"));

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return payment;
    }
}
