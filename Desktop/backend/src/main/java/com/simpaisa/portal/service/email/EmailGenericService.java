package com.simpaisa.portal.service.email;

import com.simpaisa.portal.entity.mongo.Role;
import com.simpaisa.portal.entity.mongo.User;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailGenericService {
//    public Map<String, Object> send(HashMap<String, String> data) {
//    }

    @Autowired
    com.simpaisa.portal.email.EmailService emailService;


    @Async("threadPoolTaskExecutor")
    public Map<String , Object> send(HashMap<String, String> data){

        Map<String, Object> response = null;
        Map<String, Object> map = new HashMap<>();
        try {
            if (data.containsKey(Utility.EMAIL) && data.get(Utility.EMAIL).trim().length() > 0) {
                if(data.containsKey(Utility.EMAIL_TYPE) && data.get(Utility.EMAIL_TYPE).trim().length()> 0) {
                    String email = data.get(Utility.EMAIL);

                    if(data.get(Utility.EMAIL_TYPE).equalsIgnoreCase(Utility.EMAIL_TYPE_KYC_REVIEW)) {
                        emailService.sendMailWithTemplate(map, Utility.SUBJECT_KYC_REVIEW, email, Utility.KYC_REVIEW_TEMPLATE);
                    }else if(data.get(Utility.EMAIL_TYPE).equalsIgnoreCase(Utility.EMAIL_TYPE_KYC_APPROVED)) {
                        emailService.sendMailWithTemplate(map, Utility.SUBJECT_KYC_REVIEW_APPROVED, email, Utility.KYC_REVIEW_APPROVED_TEMPLATE);
                    }else if(data.get(Utility.EMAIL_TYPE).equalsIgnoreCase(Utility.EMAIL_TYPE_REJECTED)) {

                        map.put(Utility.REJECTION_REASON, data.get(Utility.REJECTION_REASON));
                        emailService.sendMailWithTemplate(map, Utility.SUBJECT_KYC_REVIEW_REJECTED, email, Utility.KYC_REVIEW_REJECTED_TEMPLATE);
                    }else if(data.get(Utility.EMAIL_TYPE).equalsIgnoreCase(Utility.EMAIL_TYPE_PASSWORD_RESET)) {

                        map.put(Utility.PASSWORD_RESETURL, data.get(Utility.PASSWORD_RESETURL));
                        emailService.sendMailWithTemplate(map, Utility.SUBJECT_PASSWORD_RESET, email, Utility.PASSWORD_RESET_TEMPLATE);
                    }

                }else {
                    response = Utility.getResponse(Responses.INVALID_CALL);
                }
            } else {
                response = Utility.getResponse(Responses.INVALID_CALL);
            }
        }catch (AuthenticationException ex){
            //throw  new BadCredentialsException("Invalid email / password");
            response = Utility.getResponse(Responses.SYSTEM_ERROR);
            ex.printStackTrace();
        }

        return response;
    }

    public Map<String , Object> sendProduct(HashMap<String, String> data){

        Map<String, Object> response = null;
        Map<String, Object> map = new HashMap<>();

        map.put("productID", data.get("productID"));
        try {
            if (data.containsKey(Utility.EMAIL) && data.get(Utility.EMAIL).trim().length() > 0) {
                if(data.containsKey(Utility.EMAIL_TYPE) && data.get(Utility.EMAIL_TYPE).trim().length()> 0) {
                    String email = data.get(Utility.EMAIL);

                    if(data.get(Utility.EMAIL_TYPE).equalsIgnoreCase("PRODUCT_REVIEW")) {
                        emailService.sendMailWithTemplate(map, "Product Review", email, Utility.KYC_REVIEW_TEMPLATE);
                    }else if(data.get(Utility.EMAIL_TYPE).equalsIgnoreCase("PRODUCT_APPROVED")) {
                        emailService.sendMailWithTemplate(map, "Product Review Approved", email, "product_review_approved_template.ftl");
                    }else if(data.get(Utility.EMAIL_TYPE).equalsIgnoreCase("PRODUCT_REJECTED")) {
                        System.out.println(data.toString());
                        map.put(Utility.REJECTION_REASON, data.get(Utility.REJECTION_REASON));
                        emailService.sendMailWithTemplate(map, "Product Review Rejected", email, "product_review_rejected_template.ftl");

                    }

                }else {
                    response = Utility.getResponse(Responses.INVALID_CALL);
                }
            } else {
                response = Utility.getResponse(Responses.INVALID_CALL);
            }
        }catch (AuthenticationException ex){
            //throw  new BadCredentialsException("Invalid email / password");
            response = Utility.getResponse(Responses.SYSTEM_ERROR);
            ex.printStackTrace();
        }

        return response;
    }

}
