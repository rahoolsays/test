package com.simpaisa.portal.repository.reporting;

import com.simpaisa.portal.entity.mysql.reporting.RevenueTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class ReportingRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;


    public RevenueTotal findByMerchantId(int merchantId) {

        RevenueTotal totalRevenue = null;
        try{
//            RowMapper<Map<String,Object>> rowMapper = new BeanPropertyRowMapper();
            String sql="select sum(amount) as amount from paymentlogs where merchantID=?";
            Object[] args= {merchantId};
            totalRevenue = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(RevenueTotal.class));
//            totalRevenue = jdbcTemplate.queryForObject("select sum(amount) as amount from paymentlogs where merchantID="+merchantId+"", new Object[]{}, rowMapper);
//            System.out.println(totalRevenue);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return totalRevenue;

    }





}
