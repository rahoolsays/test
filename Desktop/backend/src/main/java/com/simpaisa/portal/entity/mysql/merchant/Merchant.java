package com.simpaisa.portal.entity.mysql.merchant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Merchant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String postbackUrl;
	private int threshold;
	private boolean allowWalletOtp;
	private boolean isSync;

	private String email;
	private String firstName;
	private String lastName;
	private String website;
	private String logo;

	private String title;

	private Boolean instantDisburse;
	private Double maxAmountLimit;
	
}
