package com.simpaisa.portal.entity.mysql.disbursement;

import com.simpaisa.portal.utility.Utility;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisbursementListMapper implements RowMapper<DisbursementList> {
    @Override
    public DisbursementList mapRow(ResultSet resultSet, int i) throws SQLException {
        DisbursementList disbursement = null;
        try{
            disbursement = new DisbursementList();
//            disbursement.setMerchantId(resultSet.getLong("merchantId"));
            disbursement.setReference(resultSet.getString("reference"));
            disbursement.setCustomerReference(resultSet.getString("customerReference"));
            disbursement.setIssueDate(resultSet.getString("issueDate"));
            disbursement.setDisbDate(resultSet.getString("disbDate"));
            disbursement.setCurrency(resultSet.getString("currency"));
            disbursement.setAmount(resultSet.getDouble("amount"));
            disbursement.setDisbursedAmount(resultSet.getDouble("disbamount"));
            disbursement.setDeductedAmount(resultSet.getDouble("deducted_amount"));

           // disbursement.setAdjustmentsWithTax(resultSet.getDouble("adjust_tax"));
            disbursement.setState(resultSet.getString("state"));
           disbursement.setNarration(resultSet.getString("narration"));
           disbursement.setComments(resultSet.getString("comment"));
            /*disbursement.setUuid(resultSet.getString("uuid"));*/
            /*disbursement.setPath(resultSet.getString("path"));*/
            disbursement.setPath(Utility.BASE_URL + Utility.MERCHANTS_URL + resultSet.getLong("merchantId") + Utility.DISBURSEMENTS + resultSet.getString("uuid"));
            disbursement.setAccount(resultSet.getString("account"));
            disbursement.setAccountType(resultSet.getString("accountType"));
            disbursement.setDestinationBank(resultSet.getString("destinationBank"));

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return disbursement;
    }
}

