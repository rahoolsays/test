package com.simpaisa.portal.entity.mysql.disbursement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisbursementDetail implements Serializable {

        private static final long serialVersionUID = 1L;
        private String customerName;
        private String customerAccount;

        @JsonIgnore
        private String customerReference;

        private Timestamp issueDate;
        private Timestamp disbDate;
        private  String currency;
        private Double amount;
        private Double disbursedAmount;
        private Double adjustmentsWithTax;
        private String reference;
        private String state;
        private String comments;
        private Map<String, String> settlements;

    }
