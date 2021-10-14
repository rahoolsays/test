package com.simpaisa.portal.entity.mysql.disbursement;

import com.simpaisa.portal.utility.Utility;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisbursementMapper implements RowMapper<Disbursement> {
    @Override
    public Disbursement mapRow(ResultSet resultSet, int i) throws SQLException {
        Disbursement disbursement = null;
        try{
            disbursement = new Disbursement();
//            disbursement.setMerchantId(resultSet.getLong("merchantId"));
            disbursement.setReference(resultSet.getString("reference"));
            disbursement.setCustomerReference(resultSet.getString("customerReference"));
            disbursement.setIssueDate(resultSet.getTimestamp("issueDate"));
            disbursement.setDisbDate(resultSet.getTimestamp("disbDate"));
            disbursement.setCurrency(resultSet.getString("currency"));
            disbursement.setAmount(resultSet.getDouble("amount"));
            disbursement.setDisbursedAmount(resultSet.getDouble("disbamount"));
            disbursement.setAdjustmentsWithTax(resultSet.getDouble("adjust_tax"));
            disbursement.setState(resultSet.getString("state"));
//            disbursement.setNarration(resultSet.getString("narration"));
            /*disbursement.setUuid(resultSet.getString("uuid"));*/
            /*disbursement.setPath(resultSet.getString("path"));*/
            disbursement.setPath(Utility.BASE_URL + Utility.MERCHANTS_URL + resultSet.getLong("merchantId") + Utility.DISBURSEMENTS + resultSet.getString("uuid"));

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return disbursement;
    }
}
