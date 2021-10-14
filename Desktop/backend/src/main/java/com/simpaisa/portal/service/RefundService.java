package com.simpaisa.portal.service;

import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.model.RefundRequest;
import com.simpaisa.portal.model.Transaction;
import com.simpaisa.portal.repository.RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RefundService {
	
	@Autowired
	RefundRepository repo;

	public Map<String, Object> getResponse(Responses responses, Map<String, String> data) {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		if(data.containsKey("transactionId"))
		{
			response.put("transactionId", data.get("transactionId"));
		}
		if(data.containsKey("userKey"))
		{
			response.put("userKey", data.get("userKey"));
		}
		if(data.containsKey("referenceNumber"))
		{
			response.put("referenceNumber", data.get("referenceNumber"));
		}
		if(data.containsKey("status"))
		{
			response.put("refundStatus", data.get("status"));
		}
		response.put("STATUS", responses.getStatus());
		response.put("MESSAGE", responses.getMessage());

		return response;
	}

	public Transaction CheckTransaction(String transactionId,String userKey,String merchantId) {



		Transaction transaction=null;
//		if(transactionId!=null) {
//			transaction=repo.get(transactionId,null);
//		}
//		else {
//			transaction=repo.get(null,userKey);
//		}
//
		if(merchantId==null) {

			transaction=repo.get(transactionId,userKey);
		}
		else {

			transaction=repo.getTransaction(transactionId,userKey,merchantId);
		}




		return transaction;

	}

	public Transaction getTransaction(String transactionId) {



		Transaction transaction=null;
		transaction=repo.getTransactionRecord(transactionId);
		return transaction;

	}

	public RefundRequest addRequest(RefundRequest refundReq) {

		Boolean status;
		RefundRequest req;

		req=repo.getRefund(refundReq.getTransactionId());

		if(req!=null) {
			System.out.println("Request Already Exist");
			status=false;

		}
		else {
			repo.addRequest(refundReq);
			System.out.println("Request Added");
			status=true;
		}

		return req;
	}

	public List<RefundRequest> getRefundRequests(){
		List<RefundRequest> refundRequests=new ArrayList<RefundRequest>();
		refundRequests=repo.getRequests();
		return refundRequests;
	}


	public void updatePostbackToRefunded(String transactionId) {
		repo.updateStatusToRefunded(transactionId);
	}


	public void updatePostbackToRefundFailed(String transactionId) {
		repo.updateStatusToRefundFailed(transactionId);
	}

	public void updatePostbackToApiFailure(String transactionId) {
		repo.updateStatusToApiFailure(transactionId);
	}

}
