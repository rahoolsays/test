package com.simpaisa.portal.repository.disbursement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.simpaisa.portal.entity.mysql.disbursement.Disbursement;

@Transactional
@Repository
public class AdminDisbursementRepository {

	 @Autowired
	 JdbcTemplate  jdbcTemplate;
	 
	    private final String SQL_DISBURSEMENT_INSERT = "INSERT INTO disbursements (`merchantId`,`reference`,`customerReference`,`issueDate`,`currency`,`amount`,`narration`,`uuid`,`state`,comment) VALUES " +
	            "(?,?,?,?,?,?,?,?,?,?);";
	    
	public long insert(Disbursement disbursement){
	        long key = 0l;
	        try{
	            KeyHolder keyHolder = new GeneratedKeyHolder();
	            jdbcTemplate.update(
	                    new PreparedStatementCreator(){
	                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DISBURSEMENT_INSERT,new String[]{"disbursementId"});
	                            preparedStatement.setLong(1,disbursement.getMerchantId());
	                            preparedStatement.setString(2, disbursement.getReference());
	                            preparedStatement.setString(3, disbursement.getCustomerReference());
	                            preparedStatement.setTimestamp(4, disbursement.getIssueDate());
	                            preparedStatement.setString(5, disbursement.getCurrency());
	                            preparedStatement.setDouble(6, disbursement.getAmount());
	                            preparedStatement.setString(7,disbursement.getNarration());
	                            preparedStatement.setString(8, disbursement.getUuid());
	                            preparedStatement.setString(9, disbursement.getState());
	                            preparedStatement.setString(10, disbursement.getComments());

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
	
	
	
	
    public int update(Map<String, Object> data, Long merchantId, String reference)
    {
      
    	System.out.println("data in repo"+data +"merchantId  =="+merchantId+"ref == "+reference);
    	StringBuilder sb = new StringBuilder();
        sb.append("UPDATE disbursements SET ");
        for (Map.Entry entry : data.entrySet()){
            sb.append(entry.getKey() +" = '"+ entry.getValue().toString() +"' ,");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(" where merchantId = '"+ merchantId + "' ");
        sb.append(" AND reference = '"+ reference + "'");
        sb.append(";");
       // sb.append(" and state in ('in_review', 'publishing') ;");
        System.out.println(sb.toString());

        int  affectedRows = 0;

        try{

            affectedRows =  jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
                            return preparedStatement;
                        }
                    }
            );

        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }

        return affectedRows;
    }

}
