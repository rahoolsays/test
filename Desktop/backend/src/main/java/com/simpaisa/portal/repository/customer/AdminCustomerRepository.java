package com.simpaisa.portal.repository.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.simpaisa.portal.entity.mysql.admin.customer.AdminCustomer;
import com.simpaisa.portal.entity.mysql.admin.customer.AdminCustomerMapper;
import com.simpaisa.portal.entity.mysql.customer.Customer;
import com.simpaisa.portal.entity.mysql.customer.CustomerMapper;
@Repository
@Transactional
public class AdminCustomerRepository {

	  @Autowired
	    JdbcTemplate jdbcTemplate;

	    private static  final String SQL_INSERT = " INSERT INTO disbursement_customer (`reference`, `merchantId`, `name`, `contact`, `email`," +
	            " `dob`, `gender`, `country`, `city`, `state`, `streetAddress`, `postalCode`, `landmark`, `freeFormAddress`," +
	            " `maritialStatus`, `idNumber`, `idExpirationDate`, `ntnNumber`, `account`, `accountType`, `destinationBank`, `branchCode`, `createdDate`,active) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); ";

	    private static   final String SQL_SELECT_BY_REF = "SELECT reference,merchantId, name, contact, email, dob," +
	            " gender, country, city, state, streetAddress, postalCode, landmark, freeFormAddress, maritialStatus, idNumber, idExpirationDate, " +
	            "ntnNumber, account, accountType, destinationBank, branchCode, createdDate, updatedDate,active FROM disbursement_customer WHERE merchantId = ? AND reference = ? ; ";

	    public long insert(Customer customer){
	        long key = 0l;
	        try{
	            KeyHolder keyHolder = new GeneratedKeyHolder();
	            jdbcTemplate.update(
	                    new PreparedStatementCreator(){
	                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT,new String[]{"ID"});
	                            preparedStatement.setString(1,customer.getReference());
	                            preparedStatement.setLong(2,customer.getMerchantId());
	                            preparedStatement.setString(3,customer.getCustomerName());
	                            preparedStatement.setString(4, customer.getCustomerContact());
	                            preparedStatement.setString(5, customer.getCustomerEmail());
	                            preparedStatement.setString(6, customer.getCustomerDob());
	                            preparedStatement.setString(7, customer.getCustomerGender());
	                            preparedStatement.setString(8, customer.getCustomerAddress().getCountry());
	                            preparedStatement.setString(9, customer.getCustomerAddress().getCity());
	                            preparedStatement.setString(10, customer.getCustomerAddress().getState());
	                            preparedStatement.setString(11, customer.getCustomerAddress().getStreetAddress());
	                            preparedStatement.setString(12, customer.getCustomerAddress().getPostalCode());
	                            preparedStatement.setString(13, customer.getCustomerAddress().getLandmark());
	                            preparedStatement.setString(14, customer.getCustomerAddress().getFreeformAddress());
	                            preparedStatement.setString(15, customer.getCustomerMaritalStatus());
	                            preparedStatement.setString(16, customer.getCustomerIdNumber());
	                            preparedStatement.setString(17, customer.getCustomerIdExpirationDate());
	                            preparedStatement.setString(18, customer.getCustomerNtnNumber());
	                            preparedStatement.setString(19, customer.getCustomerAccount());
	                            preparedStatement.setString(20, customer.getAccountType());
	                            preparedStatement.setString(21, customer.getDestinationBank());
	                            preparedStatement.setString(22, customer.getBranchCode());
	                            preparedStatement.setTimestamp(23, new Timestamp(System.currentTimeMillis()));
	                            preparedStatement.setInt(24, customer.getStatus());
	                            System.out.println(preparedStatement.toString());


	                            return preparedStatement;
	                        }

	                    },keyHolder
	            );

	            key = keyHolder.getKey().longValue();

	        }catch (Exception e){
	            e.printStackTrace();
	            throw e;
	        }
	        return key;
	    }
	    
	    public AdminCustomer getCustomerByReference(String reference, Long merchantId){
	    	AdminCustomer customer = null;
	        try {
	            /*Query query = new Query();
	            query.addCriteria(Criteria.where(Utility.REFERENCE).is(reference)
	                    .and(Utility.MERCHANT_ID).is(merchantId));
	            customer = mongoTemplate.findOne(query, Customer.class);*/

	            RowMapper<AdminCustomer> rowMapper = new AdminCustomerMapper();

	            customer = jdbcTemplate.queryForObject(SQL_SELECT_BY_REF, rowMapper, new Object[]{merchantId, reference});


	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            throw e;
	        }

	        return customer;
	    }
}
