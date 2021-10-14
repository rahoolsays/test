package com.simpaisa.portal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString @Getter @Setter
public class Transaction {
	public String transactionID; 
	public String ACR;
	public String amount;
	public String codeOTP;
	public String createdDate;
	public String mobileNo;
	public String status;
	public String transactionType;
	public String updatedDate;
	public String userKey;
	public String merchantID;
	public String operatorID;
	public String productID;
	public String transactionStatusID;
	public String processed;
	public String action;
	public String accountId;
	public String integrationId;
	public String productReference;
	public String acknowledge;
	public String refund;
	public String currency;
	public String merchantUrl;
	public String paymentToken;
}
