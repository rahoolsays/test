package com.simpaisa.portal.entity.mysql.disbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalDisbursement implements Serializable  {

    private static final long serialVersionUID = 1L;

    private int count;
    private Double amount;
   // private Double disburseAmount;
   // private Double deductedAmount;


}
