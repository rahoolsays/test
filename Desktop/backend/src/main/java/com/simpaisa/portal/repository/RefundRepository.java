package com.simpaisa.portal.repository;

import com.simpaisa.portal.model.RefundRequest;
import com.simpaisa.portal.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RefundRepository {


	@Autowired
	JdbcTemplate jdbcTemplate;

	public Transaction get(String transactionId,String userKey) {
		Transaction item=null;
		try {
			String sql=null;
//			if(transactionId!=null) {
//				sql="Select transactionID, ACR, amount, codeOTP, createdDate, mobileNo, status, transactionType, updatedDate, userKey, merchantID, operatorID, productID, transactionStatusID, processed, action, accountId, integrationId, productReference, acknowledge, refund, currency, merchantUrl, paymentToken from transaction where transactionId=?";
//				Object[] args= {transactionId};
//				item =jdbctemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Transaction.class));
//			}
//			else {
//				sql="Select transactionID, ACR, amount, codeOTP, createdDate, mobileNo, status, transactionType, updatedDate, userKey, merchantID, operatorID, productID, transactionStatusID, processed, action, accountId, integrationId, productReference, acknowledge, refund, currency, merchantUrl, paymentToken from transaction where userKey=? limit 1";
//				Object[] args= {userKey};
//				item =jdbctemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Transaction.class));
//			}
			sql="Select transactionID, ACR, amount, codeOTP, createdDate, mobileNo, status, transactionType, updatedDate, userKey, merchantID, operatorID, productID, transactionStatusID, processed, action, accountId, integrationId, productReference, acknowledge, refund, currency, merchantUrl, paymentToken from transaction where transactionId=? and userKey=?";
			Object[] args= {transactionId,userKey};
			item =jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Transaction.class));
		}
		catch(Exception e) {}
		return item;
	}

	public Transaction getTransaction(String transactionId,String userKey,String merchantId) {
		Transaction item=null;
		try {
			String sql=null;
//			if(transactionId!=null) {
//				sql="Select transactionID, ACR, amount, codeOTP, createdDate, mobileNo, status, transactionType, updatedDate, userKey, merchantID, operatorID, productID, transactionStatusID, processed, action, accountId, integrationId, productReference, acknowledge, refund, currency, merchantUrl, paymentToken from transaction where transactionId=?";
//				Object[] args= {transactionId};
//				item =jdbctemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Transaction.class));
//			}
//			else {
//				sql="Select transactionID, ACR, amount, codeOTP, createdDate, mobileNo, status, transactionType, updatedDate, userKey, merchantID, operatorID, productID, transactionStatusID, processed, action, accountId, integrationId, productReference, acknowledge, refund, currency, merchantUrl, paymentToken from transaction where userKey=? limit 1";
//				Object[] args= {userKey};
//				item =jdbctemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Transaction.class));
//			}
			sql="Select transactionID, ACR, amount, codeOTP, createdDate, mobileNo, status, transactionType, updatedDate, userKey, merchantID, operatorID, productID, transactionStatusID, processed, action, accountId, integrationId, productReference, acknowledge, refund, currency, merchantUrl, paymentToken from transaction where transactionId=? and userKey=? and merchantID=?";
			Object[] args= {transactionId,userKey,merchantId};
			item =jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Transaction.class));
		}
		catch(Exception e) {}
		return item;
	}

	public void addRequest(RefundRequest refundReq) {
		SimpleJdbcInsert refund=new SimpleJdbcInsert(jdbcTemplate);
		if(refundReq.getCnic()==null){
			refund.withTableName("refund_request").usingColumns("mobileNo","transactionId","referenceNumber","status","createdDate");
		}else{
			refund.withTableName("refund_request").usingColumns("mobileNo","transactionId","referenceNumber","status","createdDate","cnic");
		}
		BeanPropertySqlParameterSource param=new BeanPropertySqlParameterSource(refundReq);
		refund.execute(param);

	}

	public RefundRequest getRefund(String transactionId) {
		RefundRequest item=null;
		try {
			String sql="Select * from refund_request where transactionId=?";
			Object[] args= {transactionId};
			item =jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(RefundRequest.class));
		}
		catch(Exception e) {}
		return item;
	}

	public List<RefundRequest> getRequests(){
		List<RefundRequest> refundRequests=null;
		try {
			String sql="Select * from refund_request where status=0";
			refundRequests = jdbcTemplate.query(sql, new BeanPropertyRowMapper(RefundRequest.class));
		}
		catch(Exception e) {}
		return refundRequests;

	}



	public Transaction getTransactionRecord(String transactionId) {
		Transaction item=null;
		try {
			String sql="Select * from transaction where transactionId=?";
			Object[] args= {transactionId};
			item =jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Transaction.class));
		}
		catch(Exception e) {}
		return item;
	}

	public void updateStatusToRefunded(String transactionId) {

		try {
			String sql="update refund_request set status=3 where transactionId=?";
			Object[] args= {transactionId};
			jdbcTemplate.update(sql, args);
		}
		catch(Exception e) {}

	}

	public void updateStatusToRefundFailed(String transactionId) {


		try {
			String sql="update refund_request set status=4 where transactionId=?";
			Object[] args= {transactionId};
			jdbcTemplate.update(sql, args);
		}
		catch(Exception e) {}
	}

	public void updateStatusToApiFailure(String transactionId) {


		try {
			String sql="update refund_request set status=5 where transactionId=?";
			Object[] args= {transactionId};
			jdbcTemplate.update(sql, args);
		}
		catch(Exception e) {}
	}


}
