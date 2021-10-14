package com.simpaisa.portal.entity.mysql.reporting.payments;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Payment {
    private int paymentID;
    private double amount;
    private String chargedOn;
    private int merchantID;
    private String mobileNo;
    private int operatorID;
    private int recursionID;
    private String chargedOnStr;
    private int processed;
    private int firstCharge;
    private String acr;
}
