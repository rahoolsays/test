package com.simpaisa.portal.entity.mysql.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    private String reference;
    private Long merchantId;
    private String customerName;
    private String customerContact;
    private String customerEmail;
    private String customerDob;
    private CustomerAddress customerAddress;
    private String customerGender;
    private String customerMaritalStatus;
    private String customerIdNumber;
    private String customerIdExpirationDate;
    private String customerNtnNumber;
    private String customerAccount;
    private String accountType;
    private String destinationBank;
    private String branchCode;
/*    private Date createdDate;
    private Date updatedDate;*/
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private int status;
}
