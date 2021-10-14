package com.simpaisa.portal.service.customer;

import static com.simpaisa.portal.utility.Utility.CUSTOMER_DOB;
import static com.simpaisa.portal.utility.Utility.CUSTOMER_NAME;
import static com.simpaisa.portal.utility.Utility.EMAIL;
import static com.simpaisa.portal.utility.Utility.REFERENCE;
import static com.simpaisa.portal.utility.Utility.SQL_DESC;
import static com.simpaisa.portal.utility.Utility.getResponse;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.simpaisa.portal.entity.mysql.admin.customer.AdminCustomer;
import com.simpaisa.portal.entity.mysql.customer.Customer;
import com.simpaisa.portal.entity.mysql.customer.CustomerAddress;
import com.simpaisa.portal.entity.mysql.customer.CustomerList;
import com.simpaisa.portal.entity.mysql.merchant.Merchant;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.repository.customer.AdminCustomerRepository;
import com.simpaisa.portal.service.MerchantService;
import com.simpaisa.portal.utility.Utility;

@Service
public class AdminCustomerService {

	private static final Logger logger = Logger.getLogger(AdminCustomerService.class);

	@Autowired
	AdminCustomerRepository adminCustomerRepository;
	@Autowired
	MerchantService merchantService;

	public LinkedHashMap<String, Object> insertCustomer(Map<String, Object> data) {

		LinkedHashMap<String, Object> response = null;

		try {
			if (data.containsKey(REFERENCE) && StringUtils.isNotEmpty(data.get(REFERENCE).toString())) {
				if (data.containsKey(CUSTOMER_NAME) && StringUtils.isNotEmpty(data.get(CUSTOMER_NAME).toString())) {
					if (data.containsKey(Utility.CUSTOMER_CONTACT)
							&& StringUtils.isNotEmpty(data.get(Utility.CUSTOMER_CONTACT).toString())) {
						if (data.containsKey(Utility.CUSTOMER_EMAIL)
								&& StringUtils.isNotEmpty(data.get(Utility.CUSTOMER_EMAIL).toString())) {
							if (data.containsKey(Utility.GENDER)
									&& Utility.isValidGender(data.get(Utility.GENDER).toString())) {
								if (data.containsKey(Utility.ACCOUNT)
										&& StringUtils.isNotEmpty(data.get(Utility.ACCOUNT).toString())) {
									if (data.containsKey(Utility.ACCOUNT_TYPE)
											&& Utility.isValidAccountType(data.get(Utility.ACCOUNT_TYPE).toString())) {
										if (data.containsKey(Utility.DESTINATION_BANK) && StringUtils
												.isNotEmpty(data.get(Utility.DESTINATION_BANK).toString())) {
											if (data.containsKey(CUSTOMER_DOB)
													&& StringUtils.isNotEmpty(data.get(CUSTOMER_DOB).toString())) {
												
												//if (data.containsKey(EMAIL)
												//		&& StringUtils.isNotEmpty(data.get(EMAIL).toString())) {
												//	Merchant merchant = merchantService
														//	.findByEmail(data.get(EMAIL).toString());
													if (data.containsKey(Utility.MERCHANT_ID)
															&& StringUtils.isNotEmpty(
																	data.get(Utility.MERCHANT_ID).toString())
															&& merchantService.isMerchantValid(Long.valueOf(data
																	.get(Utility.MERCHANT_ID).toString())) != null) {
														// if (merchant.getId() == merchantId) {

														// customerRepository.insert(getCustomer(data, merchantId));
														long id = 0l;
														id = adminCustomerRepository
																.insert(getCustomer(data, Long.valueOf(data.get(Utility.MERCHANT_ID).toString())));
														if (id > 0) {
															response = getResponse(Responses.SUCCESS, data);
														} else {
															response = getResponse(Responses.SYSTEM_ERROR, data);
														}
														/*
														 * } else { response = getResponse(Responses.INVALID_CALL,
														 * data); }
														 */
													} else {
														response = getResponse(Responses.INVALID_MERCHANT, data);
													}

												//} else {
													//response = getResponse(Responses.INVALID_CALL, data);
												//}
											} else {
												response = getResponse(Responses.INVALID_CUSTOMER_DOB, data);
											}
										} else {
											response = getResponse(Responses.INVALID_DESTINATION_BANK, data);
										}
									} else {
										response = getResponse(Responses.INVALID_ACCOUNT_TYPE, data);
									}
								} else {
									response = getResponse(Responses.INVALID_CUSTOMER_ACCOUNT, data);
								}

							} else {
								response = getResponse(Responses.INVALID_CUSTOMER_GENDER, data);
							}
						} else {
							response = getResponse(Responses.INVALID_CUSTOMER_EMAIL, data);
						}
					} else {
						response = getResponse(Responses.INVALID_CUSTOMER_CONTACT, data);
					}
				} else {
					response = getResponse(Responses.INVALID_CUSTOMERNAME, data);
				}
			} else {
				response = getResponse(Responses.INVALID_REF, data);
			}
		} catch (DuplicateKeyException e) {
			logger.error("Error in insertCustomer DuplicateKeyException :: ", e);
			response = getResponse(Responses.REFERENCE_ALREADY_EXISTS, data);
		} catch (Exception e) {
			logger.error("Error in insertCustomer :: ", e);
			response = getResponse(Responses.SYSTEM_ERROR, data);
		}
		return response;
	}

	private Customer getCustomer(Map<String, Object> request, Long merchantId) {
		Customer customer = new Customer();
		customer.setCustomerAddress(new CustomerAddress());
		customer.setReference(request.get(REFERENCE).toString());
		customer.setMerchantId(merchantId);
		customer.setCustomerName(request.get(CUSTOMER_NAME).toString());
		customer.setCustomerContact(request.get(Utility.CUSTOMER_CONTACT).toString());
		customer.setCustomerEmail(request.get(Utility.CUSTOMER_EMAIL).toString());
		customer.setCustomerGender(request.get(Utility.GENDER).toString().toUpperCase());
		customer.setCustomerAccount(request.get(Utility.ACCOUNT).toString());
		customer.setAccountType(request.get(Utility.ACCOUNT_TYPE).toString());
		customer.setDestinationBank(request.get(Utility.DESTINATION_BANK).toString());
		customer.setCustomerDob(request.get(CUSTOMER_DOB).toString());

		if (request.containsKey(Utility.ADDRESS)) {
			Map<String, Object> address = (Map<String, Object>) request.get(Utility.ADDRESS);
			CustomerAddress customerAddress = new CustomerAddress();
			if (address.containsKey(Utility.COUNTRY) && address.get(Utility.COUNTRY).toString().length() == 2) {
				customerAddress.setCountry(address.get(Utility.COUNTRY).toString());
			}
			if (address.containsKey(Utility.CITY)) {
				customerAddress.setCity(address.get(Utility.CITY).toString());
			}
			if (address.containsKey(Utility.STATE)) {
				customerAddress.setState(address.get(Utility.STATE).toString());
			}
			if (address.containsKey(Utility.STREET_ADDRESS)) {
				customerAddress.setStreetAddress(address.get(Utility.STREET_ADDRESS).toString());
			}
			if (address.containsKey(Utility.POSTAL_CODE)) {
				customerAddress.setPostalCode(address.get(Utility.POSTAL_CODE).toString());
			}
			if (address.containsKey(Utility.LANDMARK)) {
				customerAddress.setLandmark(address.get(Utility.LANDMARK).toString());
			}
			if (address.containsKey(Utility.FREEFORMADDRESS)) {
				customerAddress.setFreeformAddress(address.get(Utility.FREEFORMADDRESS).toString());
			}

			customer.setCustomerAddress(customerAddress);
		}
		if (request.containsKey(Utility.MARITAL_STATUS)) {
			customer.setCustomerMaritalStatus(request.get(Utility.MARITAL_STATUS).toString());
		}
		if (request.containsKey(Utility.CNIC)) {
			customer.setCustomerIdNumber(request.get(Utility.CNIC).toString());
		}
		if (request.containsKey(Utility.CNIC_EXPIRY)) {
			customer.setCustomerIdExpirationDate(request.get(Utility.CNIC_EXPIRY).toString());
		}
		if (request.containsKey(Utility.NTN)) {
			customer.setCustomerNtnNumber(request.get(Utility.NTN).toString());
		}
		if (request.containsKey(Utility.BRANCH_CODE)) {
			customer.setBranchCode(request.get(Utility.BRANCH_CODE).toString());
		}
		if (request.containsKey(Utility.STATUS)) {
			customer.setStatus(Integer.valueOf(request.get(Utility.STATUS).toString()));
		}

		customer.setCreatedDate(new Timestamp(System.currentTimeMillis()));

		return customer;
	}

	
	  public AdminCustomer getCustomerByRef(String reference, Long merchantId) {
		  AdminCustomer customer = null;
	        try {
	            customer = adminCustomerRepository.getCustomerByReference(reference, merchantId);

	        } catch (EmptyResultDataAccessException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            throw e;
	        }
	        return customer;
	    }
	  
	  
	  
	  
	    public LinkedHashMap<String, Object> getCustomerRequest(Long merchantId, Map<String, Object> data) {
	        LinkedHashMap<String, Object> response = null;
	        AdminCustomer customer = null;

	            try {
	                    if (data.containsKey(REFERENCE) && StringUtils.isNotEmpty(data.get(REFERENCE).toString())) {
	                        customer = getCustomerByRef(data.get(REFERENCE).toString(), merchantId);

	                        if (customer != null) {
	                            response = new LinkedHashMap<>();
	                            response.put("customer", customer);
	                        } else {
	                            response = getResponse(Responses.CUSTOMER_DOES_NOT_EXISTS);
	                        }

	                    } else {
	                        response = getResponse(Responses.INVALID_CALL);
	                    }

	            } catch (Exception e) {
	                logger.error("Error in getCustomerRequest :: ", e);
	                response = getResponse(Responses.SYSTEM_ERROR, data);
	            }
	            return response;
	    }

}
