package com.simpaisa.portal.controller;

import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.model.InquireRequest;
import com.simpaisa.portal.model.InquireResponse;
import com.simpaisa.portal.model.RefundRequest;
import com.simpaisa.portal.model.Transaction;
import com.simpaisa.portal.service.EasypaisaRefundService;
import com.simpaisa.portal.service.JazzCashRestService;
import com.simpaisa.portal.service.RefundService;
import com.simpaisa.portal.utility.JazzcashResponse;
import com.simpaisa.portal.utility.ReffundUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/")
public class RefundController {
	
	@Autowired
	RefundService mainService;
	
	@Autowired
	EasypaisaRefundService easypaisaService;
	
	@Autowired
	ReffundUtility refundUtility;

	@Autowired
	JazzCashRestService jazzCashRestService;

//	@PostMapping("/test")
//	public ResponseEntity<Map<String, Object>> test(@RequestBody Map<String, String> data){
//
//		Map<String, Object> response = null;
//
//		response = mainService.getResponse(Responses.SUCCESS, data);
//
//		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
//
//	}

	
	@PostMapping("refund")
	public ResponseEntity<Map<String, Object>> refund(@RequestBody Map<String, String> data){

		System.out.println(data);

		Map<String, Object> response = null;
		Transaction transaction=null;
		Date date = new Date();

		if(data.containsKey("transactionId") && data.containsKey("userKey") && data.containsKey("merchantId")) {

			transaction=mainService.CheckTransaction(data.get("transactionId"),data.get("userKey"),data.get("merchantId"));

		}
//		else if(data.containsKey("userKey")) {
//			transaction=mainService.CheckTransaction(null,data.get("userKey"));
//		}
		else if(data.containsKey("transactionId") && data.containsKey("userKey")) {

			transaction = mainService.CheckTransaction(data.get("transactionId"), data.get("userKey"), null);

		}
		else {
			response = mainService.getResponse(Responses.MISSING_PARAMETER, data);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}


		if(transaction!=null) {
			System.out.println("Transaction not null");

			//Generating random referenceNumber
			Random rnd = new Random();
			int referenceNumber = 100000 + rnd.nextInt(90000000);
			data.put("referenceNumber", Integer.toString(referenceNumber));


			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			RefundRequest refundRequest=new RefundRequest();
			refundRequest.setMobileNo(transaction.getMobileNo());
			refundRequest.setReferenceNumber( Integer.toString(referenceNumber));
			refundRequest.setStatus("0");
			refundRequest.setTransactionId(transaction.getTransactionID());
			refundRequest.setCreatedDate(formatter.format(date).toString());
			if(data.containsKey("cnic")){
				refundRequest.setCnic(data.get("cnic"));
			}else{
				refundRequest.setCnic(null);
			}

			System.out.println(refundRequest);

			RefundRequest refReq=mainService.addRequest(refundRequest);

			if(refReq==null) {
				response = mainService.getResponse(Responses.SUCCESS, data);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
			else {
				data.put("status", refReq.getStatus());
				data.replace("referenceNumber", refReq.getReferenceNumber());
				response = mainService.getResponse(Responses.REQUEST_ALREADY_EXIST, data);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
		}

		else {
			System.out.println("Transaction is null");
			response = mainService.getResponse(Responses.INVALID_REFUND_REQUEST, data);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

	}
//	public ResponseEntity<Map<String, Object>> refund(@RequestBody Map<String, String> data){
//
//		System.out.println(data);
//
//		Map<String, Object> response = null;
//		Transaction transaction=null;
//		Date date = new Date();
//
//		if(data.containsKey("transactionId") && data.containsKey("userKey") && data.containsKey("merchantId")) {
//			transaction=mainService.CheckTransaction(data.get("transactionId"),data.get("userKey"),data.get("merchantId"));
//		}
////		else if(data.containsKey("userKey")) {
////			transaction=mainService.CheckTransaction(null,data.get("userKey"));
////		}
//		else if(data.containsKey("transactionId") && data.containsKey("userKey")) {
//			transaction=mainService.CheckTransaction(data.get("transactionId"),data.get("userKey"),null);
//		}
//		else {
//			response = mainService.getResponse(Responses.MISSING_PARAMETER, data);
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
//		}
//
//
//		if(transaction!=null) {
//
//			//Generating random referenceNumber
//			Random rnd = new Random();
//			int referenceNumber = 100000 + rnd.nextInt(90000000);
//			data.put("referenceNumber", Integer.toString(referenceNumber));
//
//
//			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//
//			RefundRequest refundRequest=new RefundRequest();
//			refundRequest.setMobileNo(transaction.getMobileNo());
//			refundRequest.setReferenceNumber( Integer.toString(referenceNumber));
//			refundRequest.setStatus("0");
//			refundRequest.setTransactionId(transaction.getTransactionID());
//			refundRequest.setCreatedDate(formatter.format(date).toString());
//
//			System.out.println(refundRequest);
//
//			RefundRequest refReq=mainService.addRequest(refundRequest);
//
//			if(refReq==null) {
//				response = mainService.getResponse(Responses.SUCCESS, data);
//				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
//			}
//			else {
//				data.put("status", refReq.getStatus());
//				data.replace("referenceNumber", refReq.getReferenceNumber());
//				response = mainService.getResponse(Responses.REQUEST_ALREADY_EXIST, data);
//				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
//			}
//		}
//
//		else {
//			response = mainService.getResponse(Responses.INVALID_REFUND_REQUEST, data);
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
//		}
//
//	}

	
	@GetMapping("/test")
	public List<RefundRequest> test(){
		
		List<RefundRequest> refundRequests=new ArrayList<RefundRequest>();
		
		refundRequests=mainService.getRefundRequests();
		
		if(refundRequests!=null) {
			for (int i = 0; i < refundRequests.size(); i++) {

				String transactionId=refundRequests.get(i).getTransactionId();
				
				Transaction transaction=new Transaction();
				transaction=mainService.getTransaction(transactionId);
				String transactionStatus=transaction.getStatus();
				String operatorId=transaction.getOperatorID();
				String amount=transaction.getAmount();
				String date=transaction.getCreatedDate();
				String merchantID=transaction.getMerchantID();
				String userKey=transaction.getUserKey();
				
				if(transactionStatus.equals("1")) {
					if(operatorId.equals("100007")) {
						String[] dateArray=date.split(" ");
						date=dateArray[0];
						String[] dateSplit=date.split("-");
						date=dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0];
//						System.out.println("easypaisa = " + easypaisaService.refundAmount(transactionId,amount,date, "","44441"));
						HashMap<String, String> res=easypaisaService.refundAmount(transactionId,amount,date, "","44441");
//						System.out.println(res.get("responseCode"));
						if(res.get("responseCode").equals("000")) {
							mainService.updatePostbackToRefunded(transactionId);
						}
						else {
							mainService.updatePostbackToRefundFailed(transactionId);
						}
					}

					if(operatorId.equals("100008")) {
						JazzcashResponse jazzcashResponse = jazzCashRestService.refundApi("T68291179", "200");
						if(jazzcashResponse.getResponseCode().equals("000")) {
							mainService.updatePostbackToRefunded(transactionId);
						}
						else {
							mainService.updatePostbackToRefundFailed(transactionId);
						}
					}
				}
				else if(transactionStatus.equals("0")) {
					if(operatorId.equals("100007")) {
						
						InquireRequest inquireRequest=new InquireRequest();
						inquireRequest.setMerchantId(merchantID);
						inquireRequest.setUserKey(userKey);
						InquireResponse inquireResponse= refundUtility.wallet_response(inquireRequest);
						if(inquireResponse.getStatus().equals("0000")) {
							
							String[] dateArray=date.split(" ");
							date=dateArray[0];
							String[] dateSplit=date.split("-");
							date=dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0];
							HashMap<String, String> res=easypaisaService.refundAmount(transactionId,amount,date, "","44441");
							if(res.get("responseCode").equals("000")) {
								mainService.updatePostbackToRefunded(transactionId);
								
							}
							else {
								mainService.updatePostbackToRefundFailed(transactionId);
							}
						}
					}

				}
				
				
			}
		}
		
		return refundRequests;
				
	}

	

}
