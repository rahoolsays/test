package com.simpaisa.portal.entity.mysql.customer;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerListMapper implements RowMapper<CustomerList> {
    @Override
    public CustomerList mapRow(ResultSet resultSet, int i) throws SQLException {
        CustomerList customerList= new CustomerList();

        try {
            customerList.setReference(resultSet.getString("reference"));
            customerList.setCustomerName(resultSet.getString("name"));
            customerList.setCustomerAccount(resultSet.getString("account"));
            customerList.setAccountType(resultSet.getString("accountType"));
            customerList.setDestinationBank(resultSet.getString("destinationBank"));

        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return customerList;
    }
}
