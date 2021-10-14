package com.simpaisa.portal.controller.disbursement;

import com.simpaisa.portal.entity.mysql.disbursement.DisbursementList;
import com.simpaisa.portal.service.disbursement.DisbursementService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("disbursements/{merchantId}/")

public class DisbursementController {

    @Autowired
    DisbursementService disbursementService;

    private static final Logger logger = Logger.getLogger(DisbursementController.class);

    @PostMapping(value = "initiate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> initiateDisbursement(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {
        LinkedHashMap<String, Object> response = null;

        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            logger.info("REMOTE ADDRESS :: " + ipAddress + " INITIATE RQUEST :: " + data.toString());
            response = disbursementService.initiate(data, merchantId);
            logger.info("INITIATE RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Page<DisbursementList> getAllDisbursements(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {
        Page<DisbursementList> disbursementLists = null;
        try {
            disbursementLists = disbursementService.getAll(data, merchantId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return disbursementLists;
    }

    @GetMapping(value = "/{uuid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getDisbursementDetail(@PathVariable("merchantId") Long merchantId, @PathVariable("uuid") String uuid, HttpServletRequest httpSevletRequest) {
        LinkedHashMap<String, Object> response = null;
        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            logger.info("REMOTE ADDRESS :: " + ipAddress + " getDisbursementDetail RQUEST :: " + merchantId + " - " + uuid);
            response = disbursementService.getDetail(merchantId, uuid);

            logger.info("getDisbursementDetail RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response, HttpStatus.OK);
    }

    @PostMapping(value = "update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateDisbursement(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {
        LinkedHashMap<String, Object> response = null;

        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            logger.info("REMOTE ADDRESS :: " + ipAddress + " UPDATE RQUEST :: " + data.toString());
            response = disbursementService.update(data, merchantId);
            logger.info("UPDATE RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response, HttpStatus.OK);
    }
    @PostMapping(value = "cancel", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> cancelDisbursement(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest) {
        LinkedHashMap<String, Object> response = null;

        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            logger.info("REMOTE ADDRESS :: " + ipAddress + " CANCEL RQUEST :: " + data.toString());
            response = disbursementService.cancelDisbursement( merchantId,data);
            logger.info("CANCEL RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response, HttpStatus.OK);
    }

    @PostMapping(value = "bulk", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> bulkDisbursement(@PathVariable("merchantId") Long merchantId, @RequestBody List<Map<String, Object>> data, HttpServletRequest httpSevletRequest) {
        LinkedHashMap<String, Object> response = null;

        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            //logger.info("REMOTE ADDRESS :: " + ipAddress + " BULK RQUEST :: " + data.toString());
            //logger.info(data);
            response = disbursementService.bulkInitiate( data, merchantId);
            //logger.info("CANCEL RESPONSE :: " + response.toString());

        } catch (Exception exception) {
           // exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response, HttpStatus.OK);
    }

    @PostMapping(value = "summary", consumes = "application/json", produces = "application/json")
    public ResponseEntity disbursementSummary(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest)
    {
        LinkedHashMap<String, Object> response = null;

        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            //logger.info("REMOTE ADDRESS :: " + ipAddress + " BULK RQUEST :: " + data.toString());
            //logger.info(data);
            response = disbursementService.getDisbursementSummary(merchantId, data);
            //logger.info("CANCEL RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            // exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response, HttpStatus.OK);
    }

    @PostMapping(value = "settlement", consumes = "application/json", produces = "application/json")
    public ResponseEntity getSettlement(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest)
    {
        Map<String, Object> response = null;

        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            //logger.info("REMOTE ADDRESS :: " + ipAddress + " BULK RQUEST :: " + data.toString());
            //logger.info(data);
            response = disbursementService.getSettlements(merchantId, data);
            //logger.info("CANCEL RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            // exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response, HttpStatus.OK);
    }

    @PostMapping(value = "profile", consumes = "application/json", produces = "application/json")
    public ResponseEntity getProfile(@PathVariable("merchantId") Long merchantId, @RequestBody Map<String, Object> data, HttpServletRequest httpSevletRequest)
    {
        Map<String, Object> response = null;

        try {

            String ipAddress = httpSevletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = httpSevletRequest.getRemoteAddr();
            }
            //logger.info("REMOTE ADDRESS :: " + ipAddress + " BULK RQUEST :: " + data.toString());
            //logger.info(data);
            response = disbursementService.getProfile( data);
            //logger.info("CANCEL RESPONSE :: " + response.toString());

        } catch (Exception exception) {
            // exception.printStackTrace();
        }
        return new ResponseEntity<LinkedHashMap<String, Object>>((LinkedHashMap<String, Object>) response, HttpStatus.OK);
    }

}
