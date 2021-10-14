package com.simpaisa.portal.entity.mysql.disbursement;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisbursementDetailMapper implements RowMapper<DisbursementDetail> {
    @Override
    public DisbursementDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        DisbursementDetail disbursementDetail = null;

        try{
            disbursementDetail = new DisbursementDetail();
            disbursementDetail.setIssueDate(resultSet.getTimestamp("issueDate"));
            disbursementDetail.setDisbDate(resultSet.getTimestamp("disbDate"));
            disbursementDetail.setCurrency(resultSet.getString("currency"));
            disbursementDetail.setAmount(resultSet.getDouble("amount"));
            disbursementDetail.setDisbursedAmount(resultSet.getDouble("disbamount"));
            disbursementDetail.setAdjustmentsWithTax(resultSet.getDouble("adjust_tax"));
            disbursementDetail.setReference(resultSet.getString("reference"));
            disbursementDetail.setState(resultSet.getString("state"));
            disbursementDetail.setComments(resultSet.getString("comment"));
            disbursementDetail.setCustomerReference(resultSet.getString("customerReference"));

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return disbursementDetail;
    }
}
