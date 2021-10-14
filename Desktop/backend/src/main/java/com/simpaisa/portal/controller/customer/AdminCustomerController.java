package com.simpaisa.portal.controller.customer;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simpaisa.portal.entity.mysql.customer.CustomerList;
import com.simpaisa.portal.service.customer.AdminCustomerService;
import com.simpaisa.portal.service.customer.CustomerService;
import com.simpaisa.portal.utility.Utility;

@RestController
@RequestMapping("admin/disbursements/register-customer")
public class AdminCustomerController {
	
	Logger logger = Logger.getLogger(AdminCustomerController.class);

	 
	 @Autowired
	 AdminCustomerService adminCustomerService;
	 @Autowired
	 CustomerService customerService;
	 
	   @PostMapping(value = "/get/{merchantId}", produces = "application/json")
	    public ResponseEntity<?> getCustomer(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {
	        LinkedHashMap<String, Object> response = null;
	        try {

	            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
	            if (ipAddress == null) {
	                ipAddress = httpSevletRequest.getRemoteAddr();
	            }
	            logger.info("REMOTE ADDRESS :: " + ipAddress + " GET CUSTOMER RQUEST :: " + data.toString());
	            response = adminCustomerService.getCustomerRequest(merchantId, data);

	            logger.info("GET CUSTOMER RESPONSE :: " + response.toString());

	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response, HttpStatus.OK);
	    }
	   
	 @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	    public ResponseEntity<?> insertCustomer( @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {
	        LinkedHashMap<String, Object> response = null;
	       /* System.out.println("TOKEN :::::: " + token);
	        String[] chunks = token.substring(7).split("\\.");
	        Base64.Decoder decoder = Base64.getDecoder();
	        String header = new String(decoder.decode(chunks[0]));
	        String payload = new String(decoder.decode(chunks[1]));
	        System.out.println(header);
	        System.out.println(payload);
	        String id  = payload.substring(payload.indexOf('"'+"merchantId" + '"' + ":"));
	        System.out.println(id);*/

	        try {

	            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
	            if (ipAddress == null) {
	                ipAddress = httpSevletRequest.getRemoteAddr();
	            }
	            logger.info("REMOTE ADDRESS :: " + ipAddress + " REGISTER CUSTOMER RQUEST :: " + data.toString());
	         //   data.put(Utility.EMAIL,authentication.getName());
	            response = adminCustomerService.insertCustomer(data);
	            logger.info("REGISTER CUSTOMER RESPONSE :: " + response.toString());

	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return new ResponseEntity<LinkedHashMap<String, Object>>( response, HttpStatus.OK);
	    }
	 @PostMapping(value = "/list/{merchantId}",  produces = "application/json")
	    public Page<CustomerList> getCustomerList(@PathVariable("merchantId") Long merchantId,@RequestBody Map<String, Object> data){
			
		    System.out.println("ADMIN list values here "+merchantId+"data "+data.toString());
	    	String orderBy = data.get(Utility.ORDER_BY).toString();
	        String direction = data.get(Utility.DIRECTION).toString();
	        int pageNo = Integer.valueOf(data.get(Utility.PAGE_NO).toString()) ;
	        int size = Integer.valueOf(data.get(Utility.LIMIT).toString());
	        Page<CustomerList> list = customerService.getCustomerList(merchantId, orderBy, direction, pageNo, size);
	        return list;

	    }
}
