package com.simpaisa.portal.controller.disbursement;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simpaisa.portal.service.disbursement.AdminDisbursementService;

@RestController
@RequestMapping("admin/disbursements/")
//@CrossOrigin(origins = "*")
public class AdminDisbursementController {
	private static final Logger logger = Logger.getLogger(AdminDisbursementController.class);

	@Autowired
	AdminDisbursementService adminDisbursementService;

	@PostMapping(value = "initiate", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> initiateDisbursement(@RequestBody Map<String, Object> data,
			HttpServletRequest httpSevletRequest) {
		LinkedHashMap<String, Object> response = null;
         System.out.println("data here "+data);
		try {

			String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
			if (ipAddress == null) {
				ipAddress = httpSevletRequest.getRemoteAddr();
			}
			logger.info("REMOTE ADDRESS :: " + ipAddress + " INITIATE RQUEST :: " + data.toString());
			response = adminDisbursementService.initiate(data);
			logger.info("INITIATE RESPONSE :: " + response!=null ?response.toString():"");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response,
				HttpStatus.OK);
	}
	
    @PostMapping(value = "update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateDisbursement(@RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {
        LinkedHashMap<String, Object> response = null;

        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            logger.info("REMOTE ADDRESS :: " + ipAddress + " UPDATE RQUEST :: " + data.toString());
            response = adminDisbursementService.update(data);
            logger.info("UPDATE RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response, HttpStatus.OK);
    }

}
