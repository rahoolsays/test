package com.simpaisa.portal.repository;

import com.simpaisa.portal.entity.mysql.merchant.*;
import com.simpaisa.portal.repository.interfaces.MerchantRepository;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.crypto.KeyGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MerchantRepositoryImpl implements MerchantRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplateLive;

    private final String FETCH_MERCHANT_BY_ID = "select merchantID,postbackUrl, threshold, allowWalletOtp, sync_call,logo from merchant_detail where email = ?";
    private final String FETCH_MERCHANTS = "select merchantID,firstName, lastName from merchant_detail ";

    private final String FETCH_MERCHANT_QUERY = "select merchantID,postbackUrl, threshold, allowWalletOtp, sync_call,logo from merchant_detail where merchantID = ?";

    private final String FETCH_MERCHANT_DISB ="select merchantID,postbackUrl, threshold, allowWalletOtp, sync_call, website, title, inst_disburse, max_amount_disb,logo from merchant_detail where merchantID = ?";

    private final String FETCH_MERCHANT_QUERY_ALLFIELDS = "select postbackUrl, threshold, allowWalletOtp, sync_call,email,firstName,lastName,website,title from merchant_detail where merchantID = ? and email=?";
    private final String INSERT = "insert into merchant_detail(email, firstName, lastName, website, postbackUrl, threshold, title) values (?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_POSTBACK = "update merchant_detail set postbackUrl= ? where merchantId = ?";

    private final String UPDATE_LOGO = "update merchant_detail set logo= ? where merchantId = ?";

    @Override
    public long insert(Merchant merchant, short environment){
        long id = 0l;
        JdbcTemplate useJdbcTemplate;
        if(environment == Utility.JDBC_LIVE){
            useJdbcTemplate = jdbcTemplateLive;
        }else{
            useJdbcTemplate = jdbcTemplate;
        }
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            useJdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                    PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{"merchantId"});
                    try {
                        ps.setString(1, merchant.getEmail());
                        ps.setString(2, merchant.getFirstName());
                        ps.setString(3, merchant.getLastName());
                        ps.setString(4, merchant.getWebsite());
                        ps.setString(5, merchant.getPostbackUrl());
                        ps.setInt(6, merchant.getThreshold());
                        ps.setString(7, merchant.getTitle());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    return ps;
                }
            }, keyHolder);

            id = keyHolder.getKey().longValue();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public Merchant findByEmail(String email, short environment) {
        Merchant merchant = null;
        JdbcTemplate useJdbcTemplate;
        if(environment == Utility.JDBC_LIVE){
            useJdbcTemplate = jdbcTemplateLive;
        }else{
            useJdbcTemplate = jdbcTemplate;
        }
        try{
            RowMapper<Merchant> rowMapper = new MerchantMapper();
            merchant = useJdbcTemplate.queryForObject(FETCH_MERCHANT_BY_ID, new Object[]{email}, rowMapper);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return merchant;
    }

    @Override
    public Merchant findById(long merchantId, String email, short environment) {
        Merchant merchant = null;
        JdbcTemplate useJdbcTemplate;
        if(environment == Utility.JDBC_LIVE){
            useJdbcTemplate = jdbcTemplateLive;
        }else{
            useJdbcTemplate = jdbcTemplate;
        }

        try {

            RowMapper<Merchant> rowMapper = new MerchantAllFieldsMapper();
            merchant = useJdbcTemplate.queryForObject(FETCH_MERCHANT_QUERY_ALLFIELDS, new Object[] { merchantId,email }, rowMapper);

        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
        return merchant;
    }

    @Override
    public Merchant findById(int id) {
        Merchant merchant=null;
        try{
            RowMapper<Merchant> rowMapper = new MerchantMapper2();
            merchant = jdbcTemplate.queryForObject("Select merchantID,firstName,lastName,email,website,postbackUrl,threshold,allowWalletOtp,sync_call from merchant_detail where merchantID="+id, new Object[]{}, rowMapper);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return merchant;
    }
    @Override
    public Merchant findById(Long id) {
        Merchant merchant=null;
        try{
            RowMapper<Merchant> rowMapper = new MerchantMapper2();
            merchant = jdbcTemplate.queryForObject("Select merchantID,firstName,lastName,email,website,postbackUrl,threshold,allowWalletOtp,sync_call from merchant_detail where merchantID="+id, new Object[]{}, rowMapper);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return merchant;
    }

    @Override
    public void updatePostBack(long id, String postback) {
        try {
            jdbcTemplate.update(UPDATE_POSTBACK, postback, id);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void updateLogo(String merchantId, String logoUrl){
        try {
            jdbcTemplate.update(UPDATE_LOGO, logoUrl, merchantId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public List<MerchantIdAndName> getAllMerchants() {
        List<MerchantIdAndName> merchantIdAndNames = null;
        try{
            RowMapper<MerchantIdAndName> rowMapper = new MerchantMapperIdAndName();
            merchantIdAndNames = jdbcTemplate.query(FETCH_MERCHANTS, new Object[]{}, rowMapper);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return merchantIdAndNames;
    }

    @Override
    public Merchant isMerchantValid(long merchantId) {
        Merchant merchant = null;
        List<Merchant> merchantDetailList = null;
        try {

            RowMapper<Merchant> rowMapper = new MerchantMapper();
            merchantDetailList = jdbcTemplate.query(FETCH_MERCHANT_DISB, new Object[] { merchantId }, rowMapper);
            if(merchantDetailList!=null){
                if(merchantDetailList.size()>0){
                    merchant = merchantDetailList.get(0);
                }
            }
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
        return merchant;
    }




}
