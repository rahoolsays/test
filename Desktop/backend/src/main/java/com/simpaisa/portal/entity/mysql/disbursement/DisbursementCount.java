package com.simpaisa.portal.entity.mysql.disbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DisbursementCount implements Serializable {

    private static final long serialVersionUID = 1L;
    private String state;
    private int count;
    private Double totalAmount;
}
