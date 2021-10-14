package com.simpaisa.portal.entity.mysql.disbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disbursement implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long disbursementId;
    private Long merchantId;
    private String reference;
    private String customerReference;
    private Timestamp issueDate;
    private Timestamp disbDate;
    private  String currency;
    private Double amount;
    private Double disbursedAmount;
    private Double adjustmentsWithTax;
    private String path;
    private String state;
    private String narration;
    private String uuid;
    private String comments;

    public Disbursement(Long merchantId, String reference, String customerReference, Timestamp issueDate, String currency, Double amount, String narration, String uuid) {
        this.merchantId = merchantId;
        this.reference = reference;
        this.customerReference = customerReference;
        this.issueDate = issueDate;
        this.currency = currency;
        this.amount = amount;
        this.narration = narration;
        this.uuid = uuid;

    }
    // for adming 
    public Disbursement(Long merchantId, String reference, String customerReference, Timestamp issueDate, String currency, Double amount, String narration, String uuid, String state,String comments) {
        this.merchantId = merchantId;
        this.reference = reference;
        this.customerReference = customerReference;
        this.issueDate = issueDate;
        this.currency = currency;
        this.amount = amount;
        this.narration = narration;
        this.uuid = uuid;
        this.state=state;
        this.comments= comments;

    }
}
