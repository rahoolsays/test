package com.simpaisa.portal.repository.disbursement;


import com.simpaisa.portal.entity.mysql.disbursement.*;
import com.simpaisa.portal.entity.mysql.settlements.Settlement;
import com.simpaisa.portal.utility.Utility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class DisbursementRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SQL_DISBURSEMENT_INSERT = "INSERT INTO disbursements (`merchantId`,`reference`,`customerReference`,`issueDate`,`currency`,`amount`,`state`,`narration`,`uuid`) VALUES " +
            "(?,?,?,?,?,?,'in_review',?,?);";

    private final String SQL_DISBURSEMENT_SELECT = "SELECT merchantId, reference, customerReference, issueDate, disbDate, currency, amount, disbamount, adjust_tax, state, comment, uuid FROM dcbdatasource.disbursements " +
            " where merchantId =  ? AND issueDate between ? AND ? ORDER BY reference limit ?;";

    private final String SQL_DISBURSEMENT_DETAIL_SELECT = "SELECT  reference, customerReference, issueDate, disbDate, currency, amount, disbamount, adjust_tax, state, comment FROM dcbdatasource.disbursements " +
            " where merchantId =  ? AND uuid = ?   limit 1;";

    private final String SQL_DISBURSEMENT_SELECT_WITH_STATE = "SELECT merchantId, reference, customerReference, issueDate, disbDate, currency, amount, disbamount, adjust_tax, state, uuid FROM dcbdatasource.disbursements " +
            " where merchantId =  ? AND state = ? AND issueDate between ? AND ? ORDER BY reference limit ?;";

    private final String SQL_CANCEL_DISBURSEMENT = " Update disbursements " +
            " Set state = 'cancelled' " +
            " where merchantId = ? and reference  = ? " +
            " and state in ('in_review', 'publishing') ";

    private final String SQL_COUNT_SUM_DISB = " SELECT count(*) as count, sum(amount) as totalAmount from dcbdatasource.disbursements where " +
            " merchantId = ? and issueDate between ? AND ?  ";

    private final String SQL_DISB_SETTLEMENT_SUMMARY = " select  dsf.merchantid, msf.displayName , sum(d.amount) as amount, msf.value, msf.percentile, sum(dsf.value) as total from dcbdatasource.disbursement_settlement_fields as dsf " +
            " LEFT join dcbdatasource.merchant_settlement_fields as msf on dsf.merchantId = msf.merchantId and dsf.merchantSetId = msf.Id " +
            " LEFT join dcbdatasource.disbursements as d on d.merchantId = dsf.merchantId and dsf.reference = d.reference " +
            " where dsf.merchantId = ? and d.state = 'disbursed' and d.issueDate between ? and ? " +
            " AND msf.applyOn = ? and msf.reversal = 0 " +
            " group by dsf.merchantSetId ";

    private final String SQL_TOTAL_DISB_AMOUNT = "select count(*) as count , sum(amount) as amount from dcbdatasource.disbursements where merchantId = ? and issueDate between ? and ? \n" +
            "and state = 'disbursed' ";

    private final String SQL_GET_SETTLEMENT_DETAILS = "SELECT msf.displayName, msf.value, percentile, dsf.value as amount from dcbdatasource.merchant_settlement_fields AS msf JOIN " +
            " dcbdatasource.disbursement_settlement_fields AS dsf on msf.merchantId = dsf.merchantId " +
            " and msf.id = dsf.merchantSetId where dsf.merchantId = ? and dsf.reference = ? and applyOn = ? ;";

    private final String SQL_GET_SETTLEMENT_FIELDS = " SELECT displayName, percentile, value, applyOn from merchant_settlement_fields where merchantId = ? and applyOn = ? and status = 1 and apply = 1 order by orderSeq asc ";

    private final String SQL_GET_REVERSAL_FEE = "select  dsf.merchantid, msf.displayName , sum(d.amount) as amount, msf.value, msf.percentile, sum(dsf.value) as total from dcbdatasource.disbursement_settlement_fields as dsf \n" +
            " LEFT join dcbdatasource.merchant_settlement_fields as msf on dsf.merchantId = msf.merchantId and dsf.merchantSetId = msf.Id \n" +
            "LEFT join dcbdatasource.disbursements as d on d.merchantId = dsf.merchantId and dsf.reference = d.reference \n" +
            "where dsf.merchantId = ? and d.state = 'reversed' and d.issueDate between ? and ? \n" +
            " AND msf.reversal = 1 \n" +
            " group by dsf.merchantSetId";

    private final String SQL_total_reversal_disbursement_instructions = "select count(*) as count , sum(amount) as amount from dcbdatasource.disbursements where merchantId = ? and issueDate between ? and ? \n" +
            "and state = 'reversed';";


    public List<DisbSettlementDetail> getSettlementDetails(long merchantId, String reference, String applyOn){

        try{
            return jdbcTemplate.query(SQL_GET_SETTLEMENT_DETAILS, new BeanPropertyRowMapper<>(DisbSettlementDetail.class),
                    new Object[]{merchantId, reference, applyOn});

        }
        catch (Exception e){
            throw e;
        }
    }

    public DisbursementDetail fetchDetail(long merchantId, String uuid){
        try {
            RowMapper<DisbursementDetail> rowMapper = new DisbursementDetailMapper();
            return jdbcTemplate.queryForObject(SQL_DISBURSEMENT_DETAIL_SELECT, rowMapper, new Object[]{merchantId, uuid});
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public Page<DisbursementList> fetchAll(Long merchantId, String state, String search, Timestamp start_date, Timestamp end_date, Pageable pageable)
    {
        try {
            RowMapper<DisbursementList> rowMapper = new DisbursementListMapper();
            Sort.Order order = pageable.getSort().toList().get(0);

            StringBuffer sb =new StringBuffer();
            sb.append(" SELECT merchantId, disbursementId, reference, customerReference, DATE(issueDate) AS issueDate, DATE(disbDate) AS disbDate, currency, amount, disbamount, deducted_amount, state, uuid, narration, comment, account, accountType, destinationBank " )
              .append(" FROM disbursements WHERE merchantId = '"+ merchantId+"'");
            if(!state.equalsIgnoreCase("all")){
                sb.append(" AND state = '"+state+"'");
            }
            if(search != null || StringUtils.isNotEmpty(search)){
                sb.append(" AND ( reference = '"+search+"' || account = '"+search+"') ");
            }
            sb.append(" AND issueDate between '"+start_date+"' AND '"+end_date);
            sb.append("' ORDER BY "+ order.getProperty() + " " + order.getDirection().name() + " LIMIT " + pageable.getPageSize() + "  OFFSET " + pageable.getOffset());

            String sql = sb.toString();
            System.out.println(sql);
            List<DisbursementList> disbursementLists = jdbcTemplate.query(sql,rowMapper);

            return new PageImpl<DisbursementList>(disbursementLists,pageable,count(merchantId, state, search, start_date, end_date));
        }
        catch (Exception e)
        {
            throw e;
        }

    }

    public int count(Long merchantId, String state,String search, Timestamp start_date, Timestamp end_date) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT count(*) FROM disbursements WHERE merchantId = '"+ merchantId+"' ");
        if(!state.equalsIgnoreCase("all"))
        {
            sb.append(" AND state = '"+state+"' ");
        }
        if(search != null || StringUtils.isNotEmpty(search)){
            sb.append(" AND ( reference = '"+search+"' || account = '"+search+"') ");
        }
        sb.append( " AND issueDate between '"+start_date+"' AND '"+end_date + "'");
         String sql = sb.toString();
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

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
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE disbursements SET ");
        for (Map.Entry entry : data.entrySet()){
            sb.append(entry.getKey() +" = '"+ entry.getValue().toString() +"' ,");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(" where merchantId = '"+ merchantId + "' ");
        sb.append(" AND reference = '"+ reference + "'");
        sb.append(" and state in ('in_review', 'publishing') ;");
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

    public int cancelDisbursement(Long merchantId, String reference){
        int affectedRows = 0;
        try{
            affectedRows =
                    jdbcTemplate.update(
                            new PreparedStatementCreator(){
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_CANCEL_DISBURSEMENT,new String[]{"disbursementId"});
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

    public DisbursementCount fetchDisbursementCount(long merchantId, Timestamp fromDate, Timestamp toDate){
        DisbursementCount disbursementCount = null;

        try{
            disbursementCount = jdbcTemplate.queryForObject(SQL_COUNT_SUM_DISB, new BeanPropertyRowMapper<>(DisbursementCount.class),
                    new Object[]{merchantId, fromDate, toDate});
             // if(disbursementCount.get() != null & StringUtils.isNotEmpty(amountTotal))
            //total = Utility.formatAmount(Double.valueOf(amountTotal));

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return disbursementCount;
    }

    public List<DisbSettlementSummary> fetchSettlementSummary(long merchantId, Timestamp fromDate, Timestamp toDate, String applyOn){
        List<DisbSettlementSummary> disbSettlementSummary = null;
        try{
            disbSettlementSummary = jdbcTemplate.query(SQL_DISB_SETTLEMENT_SUMMARY, new BeanPropertyRowMapper<>(DisbSettlementSummary.class),
                    new Object[]{merchantId, fromDate, toDate, applyOn});
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return disbSettlementSummary;
    }

    public List<TotalDisbursement> fetchTotalDisbursedAmount(long merchantId, Timestamp fromDate, Timestamp toDate){
        List<TotalDisbursement> total = null;
        try {
            total = jdbcTemplate.query(SQL_TOTAL_DISB_AMOUNT, new BeanPropertyRowMapper<>(TotalDisbursement.class),
                    new Object[]{merchantId, fromDate, toDate});

        //   if(amountTotal != null & StringUtils.isNotEmpty(amountTotal))
         //  total = Utility.formatAmount(Double.valueOf(amountTotal));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }

    public List<Settlement> getSettlementFields(long merchantId, String applyOn){
        List<Settlement> list = null;

        try{
            list = jdbcTemplate.query(SQL_GET_SETTLEMENT_FIELDS, new BeanPropertyRowMapper<>(Settlement.class), new Object[]{merchantId, applyOn});
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  list;
    }

    public List<DisbSettlementSummary> fetchReversalFee(long merchantId, Timestamp fromDate, Timestamp toDate){
        List<DisbSettlementSummary> disbSettlementSummary = null;
        try{
            disbSettlementSummary = jdbcTemplate.query(SQL_GET_REVERSAL_FEE, new BeanPropertyRowMapper<>(DisbSettlementSummary.class),
                    new Object[]{merchantId, fromDate, toDate});
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return disbSettlementSummary;
    }

    public List<TotalDisbursement> fetchtotalreversaldisbursement(long merchantId, Timestamp fromDate, Timestamp toDate){
        List<TotalDisbursement> totalDisbursements = null;

       try {
           totalDisbursements = jdbcTemplate.query(SQL_total_reversal_disbursement_instructions, new BeanPropertyRowMapper<>(TotalDisbursement.class),
                    new Object[]{merchantId, fromDate, toDate});

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return totalDisbursements;
    }
}
