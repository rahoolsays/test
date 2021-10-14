package com.simpaisa.portal.controller.customer;

import com.fasterxml.jackson.core.JsonParser;
import com.simpaisa.portal.entity.mysql.customer.CustomerList;
import com.simpaisa.portal.service.customer.CustomerService;
import com.simpaisa.portal.utility.Utility;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("disbursements/{merchantId}/register-customer")
public class CustomerContoller {

    @Autowired
    CustomerService customerService;

    Logger logger = Logger.getLogger(CustomerContoller.class);

    @PostMapping(value = "/get", produces = "application/json")
    public ResponseEntity<?> getCustomer(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {
        LinkedHashMap<String, Object> response = null;
        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            logger.info("REMOTE ADDRESS :: " + ipAddress + " GET CUSTOMER RQUEST :: " + data.toString());
            response = customerService.getCustomerRequest(merchantId, data);

            logger.info("GET CUSTOMER RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> insertCustomer(Authentication authentication, @PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {
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
            data.put(Utility.EMAIL,authentication.getName());
            response = customerService.insertCustomer(data, merchantId);
            logger.info("REGISTER CUSTOMER RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>( response, HttpStatus.OK);
    }

    @PostMapping(value = "/list",  produces = "application/json")
    public Page<CustomerList> getCustomerList(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data){
      
    	System.out.println("list values here "+merchantId+"data "+data.toString());
    	String orderBy = data.get(Utility.ORDER_BY).toString();
        String direction = data.get(Utility.DIRECTION).toString();
        int pageNo = Integer.valueOf(data.get(Utility.PAGE_NO).toString()) ;
        int size = Integer.valueOf(data.get(Utility.LIMIT).toString());


        Page<CustomerList> list = customerService.getCustomerList(merchantId, orderBy, direction, pageNo, size);
        System.out.println("response here "+list);
        return list;

    }

    @PostMapping(value = "/delete", produces = "application/json" )
    public ResponseEntity<?> deleteCustomer(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {

        LinkedHashMap<String, Object> response = null;

        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            logger.info("REMOTE ADDRESS :: " + ipAddress + " DELETE CUSTOMER RQUEST :: " + data.toString());
            response = customerService.delete(merchantId,data);
            logger.info("UPDATE CUSTOMER RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>( response, HttpStatus.OK);
    }

    @PostMapping(value = "/isValid", produces = "application/json" )
    public ResponseEntity<?> isValid(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {

        LinkedHashMap<String, Object> response = null;

        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            logger.info("REMOTE ADDRESS :: " + ipAddress + " DELETE CUSTOMER RQUEST :: " + data.toString());
            response = customerService.isValid(merchantId,data);
            logger.info("UPDATE CUSTOMER RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>( response, HttpStatus.OK);
    }



    @PutMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCustomer(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {
        LinkedHashMap<String, Object> response = null;

        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            logger.info("REMOTE ADDRESS :: " + ipAddress + " UPDATE CUSTOMER RQUEST :: " + data.toString());
            response = customerService.updateCustomer(data, merchantId);
            logger.info("UPDATE CUSTOMER RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>( response, HttpStatus.OK);
    }
}
