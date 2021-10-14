package com.simpaisa.portal.utility;

import com.simpaisa.portal.model.InquireRequest;
import com.simpaisa.portal.model.InquireResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ReffundUtility {

    public final static String WALLET_INQUIRE="https://seagm.simpaisa.com/inquire/v2/wallets";

	 public static InquireResponse wallet_response(InquireRequest request) {

	    	InquireResponse res=new InquireResponse();
			try {
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<InquireRequest> entity = new HttpEntity<InquireRequest>(request, headers);
			 res = restTemplate.postForObject(WALLET_INQUIRE, entity, InquireResponse.class);
			}catch(Exception e) {
				
				e.printStackTrace();
			}
			System.out.println(res);
			return res ;
		}
}
