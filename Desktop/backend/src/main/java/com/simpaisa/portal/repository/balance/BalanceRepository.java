package com.simpaisa.portal.repository.balance;

import com.simpaisa.portal.entity.mysql.balance.MerchantBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;

@Repository
@Transactional
public class BalanceRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

//    private final String SQL_GET_BALANCE = " SELECT id, merchantId, available, current, createdDate FROM dcbdatasource.merchant_balance where merchantId = ?  " +
//            " AND createdDate between ? and ? order by id desc limit 1";

    private final String SQL_GET_BALANCE = " SELECT id, merchantId, total, available, on_hold, createdDate FROM merchant_balance where merchantId = ? and status = 1 " +
            " order by id desc limit 1";


//    public MerchantBalance getMerchantBalance(long merchantId, Timestamp from_date, Timestamp to_date){
//        try{
//            return jdbcTemplate.queryForObject(SQL_GET_BALANCE, new BeanPropertyRowMapper<>(MerchantBalance.class),
//                    new Object[]{merchantId, from_date, to_date});
//        }
//        catch (Exception e){
//            throw e;
//        }
//    }

    public MerchantBalance getMerchantBalance(long merchantId, Timestamp from_date, Timestamp to_date){
        try{
            return jdbcTemplate.queryForObject(SQL_GET_BALANCE, new BeanPropertyRowMapper<>(MerchantBalance.class),
                    new Object[]{merchantId});
        }
        catch (Exception e){
            throw e;
        }
    }


}
