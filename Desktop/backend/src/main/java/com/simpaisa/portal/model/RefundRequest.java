package com.simpaisa.portal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString @Getter @Setter
public class RefundRequest {
	
	public String mobileNo;
	public String transactionId;
	public String referenceNumber;
	public String status;
	public String createdDate;
	public String cnic;

}
