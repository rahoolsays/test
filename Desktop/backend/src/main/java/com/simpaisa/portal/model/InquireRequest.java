package com.simpaisa.portal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString @Getter @Setter
public class InquireRequest {

	
	public String merchantId;
	public String userKey;
}
