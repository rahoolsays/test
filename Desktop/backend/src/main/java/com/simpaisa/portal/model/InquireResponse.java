package com.simpaisa.portal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString @Getter @Setter
public class InquireResponse {
	
	public String status;
	public String message;
	public String amount;

}
