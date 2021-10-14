package com.simpaisa.portal.entity.mysql.balance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantBalance implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private long merchantId;
    private double total;
    private double available;
    private double on_hold;
    private Timestamp createdDate;




}
