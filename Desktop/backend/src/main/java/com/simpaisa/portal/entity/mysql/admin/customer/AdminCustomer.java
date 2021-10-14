package com.simpaisa.portal.entity.mysql.admin.customer;

import java.sql.Timestamp;

import com.simpaisa.portal.entity.mysql.customer.Customer;
import com.simpaisa.portal.entity.mysql.customer.CustomerAddress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCustomer {
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
