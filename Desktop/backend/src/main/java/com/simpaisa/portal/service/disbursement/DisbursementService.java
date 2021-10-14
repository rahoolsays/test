package com.simpaisa.portal.service.disbursement;

import com.simpaisa.portal.entity.mysql.balance.MerchantBalance;
import com.simpaisa.portal.entity.mysql.customer.Customer;
import com.simpaisa.portal.entity.mysql.disbursement.*;
import com.simpaisa.portal.entity.mysql.merchant.Merchant;
import com.simpaisa.portal.entity.mysql.settlements.Settlement;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.repository.disbursement.DisbursementRepository;
import com.simpaisa.portal.service.MerchantService;
import com.simpaisa.portal.service.balance.BalanceService;
import com.simpaisa.portal.service.customer.CustomerService;
import com.simpaisa.portal.utility.Utility;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.simpaisa.portal.utility.Utility.*;

@Service
public class DisbursementService {

    @Autowired
    DisbursementRepository disbursementRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    BalanceService balanceService;

    @Autowired
    MerchantService merchantService;

    private static final Logger logger = Logger.getLogger(DisbursementService.class);

    public LinkedHashMap<String, Object> bulkInitiate(List<Map<String, Object>> data, long merchantId){
        LinkedHashMap<String, Object> response = new LinkedHashMap<>();

        try {

            for (Map<String, Object> disbursemnt : data) {
                LinkedHashMap<String, Object> initiateResponse = initiate(disbursemnt, merchantId);

                if (!response.containsKey(disbursemnt.get(REFERENCE).toString())) {
                    response.put(disbursemnt.get(REFERENCE).toString(), initiateResponse.get(MESSAGE));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  response;
    }

    public LinkedHashMap<String, Object> initiate(Map<String, Object> data, long merchantId) {

        LinkedHashMap<String, Object> response = null;
        Customer customer = null;

        try {

            if (data.containsKey(Utility.CUSTOMER_REF) && StringUtils.isNotEmpty(data.get(Utility.CUSTOMER_REF).toString())) {
                if (data.containsKey(Utility.AMOUNT) && NumberUtils.isParsable(data.get(AMOUNT).toString())) {
                    if (data.containsKey(Utility.REFERENCE) && StringUtils.isNotEmpty(data.get(Utility.REFERENCE).toString())) {
                        customer = customerService.getCustomerByRef(data.get(Utility.CUSTOMER_REF).toString(), merchantId);
                        if (customer != null) {
                            long disbursementId = 0l;
                            String currency = "PKR";
                            if (data.containsKey(Utility.CURRENCY) && data.get(Utility.CURRENCY).toString().length() == 3) {
                                currency = data.get(Utility.CURRENCY).toString();
                            }
                            String uuid = UUID.nameUUIDFromBytes(data.toString().getBytes(StandardCharsets.UTF_8)).toString();

                            disbursementId = disbursementRepository.insert(new Disbursement(merchantId,
                                    data.get(Utility.REFERENCE).toString(),
                                    data.get(Utility.CUSTOMER_REF).toString(),
                                    new Timestamp(System.currentTimeMillis()),
                                    currency,
                                    Double.valueOf(data.get(Utility.AMOUNT).toString()),
                                    (data.containsKey(Utility.NARRATION) ? data.get(Utility.NARRATION).toString() : ""),
                                    uuid));
                            if (disbursementId > 0) {
                                data.put(Utility.STATE, "in_review");
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


        } catch (DuplicateKeyException ex) {
           // logger.error("Error while initiate :: ", ex);
            response = Utility.getResponse(Responses.REFERENCE_ALREADY_EXISTS, data);
        } catch (Exception e) {
           // logger.error("Error while initiate :: ", e);
            response = Utility.getResponse(Responses.SYSTEM_ERROR, data);
        }

        return response;
    }


    public LinkedHashMap<String, Object> update(Map<String, Object> request, Long merchantId) {
        LinkedHashMap<String, Object> response = null;
        try {
            if (request.containsKey(Utility.CUSTOMER_REF)) {
                if (StringUtils.isEmpty(request.get(Utility.CUSTOMER_REF).toString())) {
                    return Utility.getResponse(Responses.INVALID_CUSTOMER_REFERENCE, request);
                } else {
                    Customer customer = null;
                    customer = customerService.getCustomerByRef(request.get(Utility.CUSTOMER_REF).toString(), merchantId);

                    if (customer == null) {
                        return Utility.getResponse(Responses.CUSTOMER_DOES_NOT_EXISTS, request);
                    }
                }
            }
            int affectedRows = disbursementRepository.update(request, merchantId, request.get(Utility.REFERENCE).toString());

            if (affectedRows > 0) {
                response = Utility.getResponse(Responses.SUCCESS, request);
            } else {
                response = Utility.getResponse(Responses.INVALID_CALL, request);
            }

        } catch (DuplicateKeyException e) {
            logger.error("Error while update :: ", e);
            response = Utility.getResponse(Responses.REFERENCE_ALREADY_EXISTS, request);
        } catch (Exception e) {
            logger.error("Error while update :: ", e);
            response = Utility.getResponse(Responses.SYSTEM_ERROR, request);
        }
        return response;
    }

    public Page<DisbursementList> getAll(Map<String, Object> request, Long merchantId) {
        Page<DisbursementList> response = null;


            try {
                    if (request.containsKey(FROM_DATE) && StringUtils.isNotEmpty(request.get(FROM_DATE).toString())) {
                        if (request.containsKey(TO_DATE) && StringUtils.isNotEmpty(request.get(TO_DATE).toString())) {

                            int limit = 25;
                            int pageNo = 0;
                            String orderBy = "disbursementId";
                            String state = null;
                            String search = null;
                            Pageable pageable = null;
                            Timestamp from_date = formatDateStrToTimeStamp(request.get(FROM_DATE).toString(), DATE_FORMAT);
                            Timestamp temp = formatDateStrToTimeStamp(request.get(TO_DATE).toString(), DATE_FORMAT);

                            Timestamp to_date = new Timestamp(temp.getTime() + TimeUnit.HOURS.toMillis(23) +
                                    TimeUnit.MINUTES.toMillis(59) + TimeUnit.SECONDS.toMillis(59) + TimeUnit.MILLISECONDS.toMillis(999));


                            if (request.containsKey(STATE) && StringUtils.isNotEmpty(request.get(STATE).toString())) {
                                state = request.get(STATE).toString();
                            }

                            if (request.containsKey(LIMIT) && StringUtils.isNumeric(request.get(LIMIT).toString())) {
                                limit = Integer.valueOf(request.get(LIMIT).toString());

                            }
                            if (request.containsKey(Utility.PAGE_NO) && StringUtils.isNumeric(request.get(PAGE_NO).toString())) {
                                pageNo = Integer.valueOf(request.get(PAGE_NO).toString());
                            }
                            if (request.containsKey(Utility.ORDER_BY) && StringUtils.isNotEmpty(request.get(ORDER_BY).toString())) {
                                orderBy = request.get(ORDER_BY).toString();
                            }
                            if (request.containsKey(DIRECTION) && request.get(DIRECTION).toString().equalsIgnoreCase("desc"))
                            {
                                pageable = PageRequest.of(pageNo,limit, Sort.by(Sort.Direction.DESC,orderBy));
                            }
                            else {
                                pageable = PageRequest.of(pageNo,limit, Sort.by(Sort.Direction.ASC,orderBy));
                            }

                            if(request.containsKey(SEARCH) && StringUtils.isNotEmpty(request.get(SEARCH).toString()))
                            {
                                search = request.get(SEARCH).toString();
                            }



                            response = disbursementRepository.fetchAll(merchantId, state, search, from_date, to_date, pageable);

                        }

                    }

            } catch (Exception e) {
                logger.error("Error while get all :: ", e);

            }

        return response;

    }

    public LinkedHashMap<String, Object> getDetail(Long merchantId, String uuid) {
        LinkedHashMap<String, Object> response = null;
        Customer customer = null;
        DisbursementDetail disbursementDetail = null;

        try {
            disbursementDetail = disbursementRepository.fetchDetail(merchantId, uuid);
            if (disbursementDetail != null) {
                customer = customerService.getCustomerByRef(disbursementDetail.getCustomerReference(), merchantId);
                if (customer != null) {
                    disbursementDetail.setCustomerName(customer.getCustomerName());
                    disbursementDetail.setCustomerAccount(customer.getCustomerAccount());
                }

                //disbursementDetail.setSettlements(disbursementRepository.getSettlementDetails(merchantId, disbursementDetail.getReference()));

                response = new LinkedHashMap<>();
                response.put("disbursement", disbursementDetail);

            } else {
                response = getResponse(Responses.INVALID_CALL);
            }
        } catch (Exception e) {
            logger.error("Error while get detail :: ", e);
            response = getResponse(Responses.SYSTEM_ERROR);
        }

        return response;
    }

    public LinkedHashMap<String, Object> cancelDisbursement(long merchantId, Map<String, Object> request){

        LinkedHashMap<String, Object> response = null;
        int rows = 0;
        try{
            if(request.containsKey(REFERENCE) && StringUtils.isNotEmpty(request.get(REFERENCE).toString())){
                rows = disbursementRepository.cancelDisbursement(merchantId, request.get(REFERENCE).toString());
                if(rows > 0){
                    response = getResponse(Responses.SUCCESS);
                }
                else {
                    response = getResponse(Responses.INVALID_REF);
                }
            }
            else{
                response = getResponse(Responses.INVALID_CALL);
            }


        }
        catch (Exception e){
            response = getResponse(Responses.SYSTEM_ERROR);
        }

        return response;
    }

    public LinkedHashMap<String, Object> getDisbursementSummary(long merchantId, Map<String, Object> data){
        LinkedHashMap<String, Object> response = null;
        DisbursementCount disbursementCount = null;
        List<DisbSettlementSummary> reversalFees = null;
        MerchantBalance merchantBalance = null;
        Merchant merchantDetail = null;
        Map summary = null;
        Timestamp fromDate = formatDateStrToTimeStamp(data.get(FROM_DATE).toString(), DATE_FORMAT);
        Timestamp temp = formatDateStrToTimeStamp(data.get(TO_DATE).toString(), DATE_FORMAT);
        Timestamp toDate = new Timestamp(temp.getTime() + TimeUnit.HOURS.toMillis(23) +
                TimeUnit.MINUTES.toMillis(59) + TimeUnit.SECONDS.toMillis(59) + TimeUnit.MILLISECONDS.toMillis(999));


        System.out.println("toDATe ::::" + toDate);

        try {

            response = new LinkedHashMap<>();
            //Get Merchant Details

            merchantDetail = merchantService.isMerchantValid(merchantId);
            if(merchantDetail != null){
                response.put("merchantDetails", merchantDetail);
            }



            //Get balance
            merchantBalance = balanceService.getMerchantBalance(merchantId, fromDate, toDate);
            if(merchantBalance != null){
                response.put("merchantBalance", merchantBalance);
            }

            //Get disbursement count
            disbursementCount = disbursementRepository.fetchDisbursementCount(merchantId, fromDate, toDate);
            if(disbursementCount != null){
                response.put("disbursementCount", disbursementCount);
            }

            //Get Settlement Summary
            summary = new LinkedHashMap();
            List<DisbSettlementSummary> customer = disbursementRepository.fetchSettlementSummary(merchantId, fromDate, toDate, APPLY_ON_CUSTOMER);
            List<DisbSettlementSummary> merchant = disbursementRepository.fetchSettlementSummary(merchantId, fromDate, toDate, APPLY_ON_MERCHANT);
            summary.put("customer", customer);
            summary.put("merchant", merchant);
            if(summary != null){
                response.put("summary", summary);
            }

            //Reversal Fees
            reversalFees = disbursementRepository.fetchReversalFee(merchantId, fromDate, toDate);
            if(reversalFees != null){
                response.put("ReversalFeeDetails", reversalFees);
            }

            //Get total Successful disbursed Amount
            List<TotalDisbursement> totalAmount = disbursementRepository.fetchTotalDisbursedAmount(merchantId, fromDate, toDate);
            response.put("totalSuccessfulDisbursement", totalAmount);


            //total reversal disbursement instructions
            List<TotalDisbursement> total = disbursementRepository.fetchtotalreversaldisbursement(merchantId, fromDate, toDate);
            if(total != null)
            {
                response.put("totalReversalDisbursement", total );
            }



        }
        catch (Exception e){
            response = getResponse(Responses.SYSTEM_ERROR);
            e.printStackTrace();
        }

        return response;


    }

    public Map<String, Object> getSettlements(long merchantId, Map<String, Object> data){
        Map<String, Object> response = new LinkedHashMap<>();

        try{
            List<DisbSettlementDetail> customerDetail = disbursementRepository.getSettlementDetails(merchantId, data.get(REFERENCE).toString(),
                    APPLY_ON_CUSTOMER);

            if(customerDetail.size() > 0)
            response.put(APPLY_ON_CUSTOMER, customerDetail );

            List<DisbSettlementDetail> merchant = disbursementRepository.getSettlementDetails(merchantId, data.get(REFERENCE).toString(),
                    APPLY_ON_MERCHANT);

            if(merchant.size() > 0)
            response.put(APPLY_ON_MERCHANT,merchant );
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }

    public Map<String, Object> getProfile(Map<String, Object> data){
        Map<String, Object> response = new LinkedHashMap<>();
        Long merchantId = Long.valueOf(data.get(MERCHANT_ID).toString());

        try{
            Merchant merchant = null;
            merchant = merchantService.isMerchantValid(merchantId);
            if(merchant != null){
                response.put(MERCHANT_ID, merchant.getId());
                response.put(TITLE, merchant.getTitle());
                response.put("website", merchant.getWebsite());
                response.put("instantDisbursement", merchant.getInstantDisburse());
                response.put("maxAmountLimit", merchant.getMaxAmountLimit());
            }

            MerchantBalance balance = null;
            balance = balanceService.getMerchantBalance(merchantId, null, null);

            if(balance != null){
                response.put("total", balance.getTotal());
                response.put("available", balance.getAvailable());
                response.put("onhold", balance.getOn_hold());
            }

            List<Settlement> customerSettlements = disbursementRepository.getSettlementFields(merchantId, APPLY_ON_CUSTOMER);
            if(customerSettlements != null){
                response.put("customerFields", customerSettlements);
            }

            List<Settlement> merchantSettlements = disbursementRepository.getSettlementFields(merchantId, APPLY_ON_MERCHANT);
            if(merchantSettlements != null){
                response.put("merchantFields", merchantSettlements);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }
}
