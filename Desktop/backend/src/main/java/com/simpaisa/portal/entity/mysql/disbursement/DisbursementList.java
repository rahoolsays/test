package com.simpaisa.portal.entity.mysql.disbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisbursementList {

    private static final long serialVersionUID = 1L;
    private String reference;
    private String customerReference;
    private String issueDate;
    private String disbDate;
    private  String currency;
    private Double amount;
    private Double deductedAmount;
    private Double disbursedAmount;
    private Double adjustmentsWithTax;
    private String path;
    private String state;
    private String narration;
    private String comments;
    private String account;
    private String accountType;
    private String destinationBank;

}
