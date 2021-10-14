package com.simpaisa.portal.repository.customer;

import com.simpaisa.portal.entity.mysql.customer.Customer;
import com.simpaisa.portal.entity.mysql.customer.CustomerMapper;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

@Repository
@Transactional
public class CustomerRepository {

   /* @Autowired
    MongoTemplate mongoTemplate;*/

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SQL_INSERT = " INSERT INTO disbursement_customer (`reference`, `merchantId`, `name`, `contact`, `email`," +
            " `dob`, `gender`, `country`, `city`, `state`, `streetAddress`, `postalCode`, `landmark`, `freeFormAddress`," +
            " `maritialStatus`, `idNumber`, `idExpirationDate`, `ntnNumber`, `account`, `accountType`, `destinationBank`, `branchCode`, `createdDate`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); ";

    private  final String SQL_SELECT_BY_REF = "SELECT reference,merchantId, name, contact, email, dob," +
            " gender, country, city, state, streetAddress, postalCode, landmark, freeFormAddress, maritialStatus, idNumber, idExpirationDate, " +
            "ntnNumber, account, accountType, destinationBank, branchCode, createdDate, updatedDate FROM disbursement_customer WHERE merchantId = ? AND reference = ? ; ";

    private final String SQL_DELETE = " UPDATE disbursement_customer SET active = 2 where merchantId=? AND reference=?";

    private final String SQL_IS_VALID = " SELECT count(*) from disbursement_customer  WHERE merchantId = ? AND reference = ? AND active = 1 ";

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
    /*public Customer insert(Customer customer){
        try {
            Customer customer1 = null;
            customer1 =  mongoTemplate.save(customer);
            System.out.println("CUSTOMER ID:: " + customer1.get_id());
            return customer1;
        }
        catch (Exception e)
        {
            throw e;
        }
    }*/

/*    public Customer update(Customer customer){

        //return mongoTemplate.save(customer);
        return null;
    }*/

    public int update(Map<String, Object> data, Long merchantId, String reference)
    {
        StringBuilder sb = new StringBuilder();
        if(data.containsKey("createdDate") && data.containsKey("updatedDate") ){
            data.remove("createdDate");
            data.remove("updatedDate");
        }

        sb.append("UPDATE disbursement_customer SET ");
        for (Map.Entry entry : data.entrySet()){
            if (!entry.getKey().toString().equalsIgnoreCase(Utility.CUSTOMER_ADDRESS)
            && !entry.getKey().toString().equalsIgnoreCase("createdDate"))
            sb.append(getColumnName(entry.getKey().toString()) +" = '"+ entry.getValue().toString() +"' ,");
            else {
            	
            	    if(!entry.getKey().toString().equalsIgnoreCase("createdDate")){
            	    Map<String, Object> address = (Map<String, Object>) entry.getValue();
            	    for (Map.Entry addressEntry : address.entrySet()) {
            	        if(addressEntry.getValue() != null) {
            	            sb.append(addressEntry.getKey().toString() + " = '" + addressEntry.getValue().toString() + "', ");
            	        }
            	    }
            	    }
            	
            	/*
                if(!entry.getKey().toString().equalsIgnoreCase("createdDate")){
                Map<String, Object> address = (Map<String, Object>) entry.getValue();
                for (Map.Entry addressEntry : address.entrySet()) {
                    sb.append(addressEntry.getKey().toString() + " = '" + addressEntry.getValue().toString() + "', ");
                }
                }
                */
            }
        }
       // sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(" updatedDate = '"+ new Timestamp(System.currentTimeMillis()) + "' ");
        sb.append(" where merchantId = '"+ merchantId + "' ");
        sb.append(" AND reference = '"+ reference + "'");
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

    public int updateActive(Long merchantId, String reference){
        int affectedRows = 0;
        try{
            affectedRows =
            jdbcTemplate.update(
                    new PreparedStatementCreator(){
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE,new String[]{"ID"});
                            preparedStatement.setLong(1,merchantId);
                            preparedStatement.setString(2,reference);

                            System.out.println(preparedStatement.toString());


                            return preparedStatement;
                        }

                    }
            );



        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return affectedRows;
    }
    public String getColumnName(String key){
        String name = null;

        switch (key) {
            case "reference":
                name = "reference";
                break;
            case "customerName":
                name = "name";
                break;
            case "customerContact":
                name = "contact";
                break;
            case "customerEmail":
                name = "email";
                break;
            case "customerDob":
                name = "dob";
                break;
            case "customerGender":
                name = "gender";
                break;
            case "customerMaritalStatus":
                name = "maritialStatus";
                break;
            case "customerIdNumber":
                name = "idNumber";
                break;
            case "customerIdExpirationDate":
                name = "idExpirationDate";
                break;
            case "customerNtnNumber":
                name = "ntnNumber";
                break;
            case "customerAccount":
                name = "account";
                break;
            case "accountType":
                name = "accountType";
                break;
            case "destinationBank":
                name = "destinationBank";
                break;
            case "branchCode":
                name = "branchCode";
                break;
            case "merchantId":
                name = "merchantId";
                break;
            case "status":
                name = "active";
                break;
        }

        return name;

    }

    public Customer getCustomerByReference(String reference, Long merchantId){
        Customer customer = null;
        try {
            /*Query query = new Query();
            query.addCriteria(Criteria.where(Utility.REFERENCE).is(reference)
                    .and(Utility.MERCHANT_ID).is(merchantId));
            customer = mongoTemplate.findOne(query, Customer.class);*/

            RowMapper<Customer> rowMapper = new CustomerMapper();

            customer = jdbcTemplate.queryForObject(SQL_SELECT_BY_REF, rowMapper, new Object[]{merchantId, reference});


        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        return customer;
    }

    public int isValid(Long merchantId, String reference) {
        int result = 0;
        try {

            result = jdbcTemplate.queryForObject(SQL_IS_VALID, new Object[]{merchantId, reference}, Integer.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
