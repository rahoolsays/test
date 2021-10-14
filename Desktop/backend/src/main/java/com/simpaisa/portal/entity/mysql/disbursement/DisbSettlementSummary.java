package com.simpaisa.portal.entity.mysql.disbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisbSettlementSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    private String merchantId;
    private String displayName;
    private double amount;
    private double value;
    private int percentile;
    private double total;
}