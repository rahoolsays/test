package com.simpaisa.portal.service.disbursement;

import static com.simpaisa.portal.utility.Utility.AMOUNT;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.simpaisa.portal.entity.mysql.Merchant;
import com.simpaisa.portal.entity.mysql.customer.Customer;
import com.simpaisa.portal.entity.mysql.disbursement.Disbursement;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.repository.disbursement.AdminDisbursementRepository;
import com.simpaisa.portal.service.MerchantService;
import com.simpaisa.portal.service.customer.CustomerService;
import com.simpaisa.portal.utility.Utility;

@Service
public class AdminDisbursementService {

	@Autowired
    CustomerService customerService;
	
	@Autowired
	AdminDisbursementRepository adminDisbursementRepository;
	
	@Autowired
	MerchantService  merchantService;
	
	public LinkedHashMap<String, Object> initiate(Map<String, Object> data) {

		System.out.println("data here ");
        LinkedHashMap<String, Object> response = null;
        Customer customer = null;

        try {
            if(data.containsKey(Utility.MERCHANT_ID) && StringUtils.isNotEmpty(data.get(Utility.MERCHANT_ID).toString()) && merchantService.isMerchantValid(Long.valueOf(data.get(Utility.MERCHANT_ID).toString()))!=null) {
            if (data.containsKey(Utility.CUSTOMER_REF) && StringUtils.isNotEmpty(data.get(Utility.CUSTOMER_REF).toString())) {
                if (data.containsKey(Utility.AMOUNT) && NumberUtils.isParsable(data.get(AMOUNT).toString())) {
                    if (data.containsKey(Utility.REFERENCE) && StringUtils.isNotEmpty(data.get(Utility.REFERENCE).toString())) {
                        customer = customerService.getCustomerByRef(data.get(Utility.CUSTOMER_REF).toString(),Long.valueOf(data.get(Utility.MERCHANT_ID).toString()));
                        if (customer != null) {
                            long disbursementId = 0l;
                            String currency = "PKR";
                            if (data.containsKey(Utility.CURRENCY) && data.get(Utility.CURRENCY).toString().length() == 3) {
                                currency = data.get(Utility.CURRENCY).toString();
                            }
                            String uuid = UUID.nameUUIDFromBytes(data.toString().getBytes(StandardCharsets.UTF_8)).toString();

                            disbursementId = adminDisbursementRepository.insert(new Disbursement(Long.valueOf(data.get(Utility.MERCHANT_ID).toString()),
                                    data.get(Utility.REFERENCE).toString(),
                                    data.get(Utility.CUSTOMER_REF).toString(),
                                    new Timestamp(System.currentTimeMillis()),
                                    currency,
                                    Double.valueOf(data.get(Utility.AMOUNT).toString()),
                                    (data.containsKey(Utility.NARRATION) ? data.get(Utility.NARRATION).toString() : ""),
                                    uuid ,data.get(Utility.STATE).toString(),data.get("comment").toString()));

                            if (disbursementId > 0) {
                              // data.put(Utility.STATE, "in_review");
                            	 data.put(Utility.STATE,data.get(Utility.STATE));
                                response = Utility.getResponse(Responses.SUCCESS, data);
                            } else {
                                response = Utility.getResponse(Responses.SYSTEM_ERROR, data);
                            }

                        } else {
                            response = Utility.getResponse(Responses.CUSTOMER_DOES_NOT_EXISTS, data);
                        }
                    } else {
                        response = Utility.getResponse(Responses.INVALID_REF, data);
                    }

                } else {
                    response = Utility.getResponse(Responses.INVALID_AMOUNT_PRODUCT, data);

                }

            } else {
                response = Utility.getResponse(Responses.INVALID_CUSTOMER_REFERENCE, data);
            }
            
            }else {
            	response = Utility.getResponse(Responses.INVALID_MERCHANT, data);
            }


        } catch (DuplicateKeyException ex) {
           // logger.error("Error while initiate :: ", ex);
            response = Utility.getResponse(Responses.REFERENCE_ALREADY_EXISTS, data);
        } catch (Exception e) {
           // logger.error("Error while initiate :: ", e);
            response = Utility.getResponse(Responses.SYSTEM_ERROR, data);
        }

        return response;
    }
	
	
	
    public LinkedHashMap<String, Object> update(Map<String, Object> request) {
        LinkedHashMap<String, Object> response = null;
        try {
        	 if(request.containsKey(Utility.MERCHANT_ID) && StringUtils.isNotEmpty(request.get(Utility.MERCHANT_ID).toString()) && merchantService.isMerchantValid(Long.valueOf(request.get(Utility.MERCHANT_ID).toString()))!=null) {
        
        		 if (request.containsKey(Utility.CUSTOMER_REF)) {
                if (StringUtils.isEmpty(request.get(Utility.CUSTOMER_REF).toString())) {
                    return Utility.getResponse(Responses.INVALID_CUSTOMER_REFERENCE, request);
                } else {
                    Customer customer = null;
                    customer = customerService.getCustomerByRef(request.get(Utility.CUSTOMER_REF).toString(),Long.valueOf(request.get(Utility.MERCHANT_ID).toString()));

                    if (customer == null) {
                        return Utility.getResponse(Responses.CUSTOMER_DOES_NOT_EXISTS, request);
                    }
                }
            }
            int affectedRows = adminDisbursementRepository.update(request,Long.valueOf(request.get(Utility.MERCHANT_ID).toString()),request.get(Utility.REFERENCE).toString());

            if (affectedRows > 0) {
                response = Utility.getResponse(Responses.SUCCESS, request);
            } else {
                response = Utility.getResponse(Responses.INVALID_CALL, request);
            }

        	 }else {
             	response = Utility.getResponse(Responses.INVALID_MERCHANT, request);
             }
        	 } catch (DuplicateKeyException e) {
           // logger.error("Error while update :: ", e);
            response = Utility.getResponse(Responses.REFERENCE_ALREADY_EXISTS, request);
        } catch (Exception e) {
          //  logger.error("Error while update :: ", e);
            response = Utility.getResponse(Responses.SYSTEM_ERROR, request);
        }
        return response;
    }
}
