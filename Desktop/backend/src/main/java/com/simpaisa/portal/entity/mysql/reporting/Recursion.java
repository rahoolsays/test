package com.simpaisa.portal.entity.mysql.reporting;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Recursion {
    private Integer recursionID;
    private double amount;
    private String createdDate;
    private Integer merchantID;
    private String mobileNo;
    private Integer operatorID;
    private Integer status;
    private String userKey;
    private Integer productID;
    private Integer transactionID;
    private String renewalDate;
    private Integer retryCount;
    private Integer typeId;
    private Integer reminderStatus;
    private String acr;
    private String subType;
    private String token;
    private String consentId;
}
