package com.simpaisa.portal.entity.mysql.customer;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerAddress(new CustomerAddress());

        try{
            customer.setReference(resultSet.getString("reference"));
            customer.setMerchantId(resultSet.getLong("merchantId"));
            customer.setCustomerName(resultSet.getString("name"));
            customer.setCustomerContact(resultSet.getString("contact"));
            customer.setCustomerEmail(resultSet.getString("email"));
            customer.setCustomerDob(resultSet.getString("dob"));
            customer.setCustomerGender(resultSet.getString("gender"));
            customer.getCustomerAddress().setCountry(resultSet.getString("country"));
            customer.getCustomerAddress().setCity(resultSet.getString("city"));
            customer.getCustomerAddress().setState(resultSet.getString("state"));
            customer.getCustomerAddress().setStreetAddress(resultSet.getString("streetAddress"));
            customer.getCustomerAddress().setPostalCode(resultSet.getString("postalCode"));
            customer.getCustomerAddress().setLandmark(resultSet.getString("landmark"));
            customer.getCustomerAddress().setFreeformAddress(resultSet.getString("freeFormAddress"));
            customer.setCustomerMaritalStatus(resultSet.getString("maritialStatus"));
            customer.setCustomerIdNumber(resultSet.getString("idNumber"));
            customer.setCustomerIdExpirationDate(resultSet.getString("idExpirationDate"));
            customer.setCustomerNtnNumber(resultSet.getString("ntnNumber"));
            customer.setCustomerAccount(resultSet.getString("account"));
            customer.setAccountType(resultSet.getString("accountType"));
            customer.setDestinationBank(resultSet.getString("destinationBank"));
            customer.setBranchCode(resultSet.getString("branchCode"));
            customer.setCreatedDate(resultSet.getTimestamp("createdDate"));
            customer.setUpdatedDate(resultSet.getTimestamp("updatedDate"));


        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return customer;
    }
}
