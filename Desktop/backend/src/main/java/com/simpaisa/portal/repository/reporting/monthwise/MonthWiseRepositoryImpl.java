package com.simpaisa.portal.repository.reporting.monthwise;


import com.simpaisa.portal.entity.mysql.reporting.monthwise.MonthWiseRevenue;
import com.simpaisa.portal.entity.mysql.reporting.monthwise.MonthWiseRevenueMapper;
import com.simpaisa.portal.repository.interfaces.MonthWiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MonthWiseRepositoryImpl implements MonthWiseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String FETCH_MONTHWISE = " select sum(amount) as amount, MONTHNAME(chargedOn) as monthName from paymentlogs\n" +
            " where  merchantID = ? and   DATE(chargedOn)>=DATE(CURRENT_DATE - INTERVAL ? MONTH) \n" +
            " GROUP BY monthName ";

    private static final String FETCH_MONTHWISE_ALL = " select sum(amount) as amount, MONTHNAME(chargedOn) as monthName from paymentlogs\n" +
            " where    DATE(chargedOn)>=DATE(CURRENT_DATE - INTERVAL ? MONTH) \n" +
            " GROUP BY monthName ";

    @Override
    public List<MonthWiseRevenue> fetchMonthWiseRevenue(long merchantId, int numberOfMonths) {
        List<MonthWiseRevenue> monthWiseRevenues = null;
        try{
            RowMapper<MonthWiseRevenue> monthWiseRevenueRowMapper = new MonthWiseRevenueMapper();
            monthWiseRevenues = jdbcTemplate.query(FETCH_MONTHWISE, new Object[]{merchantId, numberOfMonths}, monthWiseRevenueRowMapper);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return monthWiseRevenues;
    }
    @Override
    public List<MonthWiseRevenue> fetchMonthWiseRevenueALL(int numberOfMonths) {
        List<MonthWiseRevenue> monthWiseRevenues = null;
        try{
            RowMapper<MonthWiseRevenue> monthWiseRevenueRowMapper = new MonthWiseRevenueMapper();
            monthWiseRevenues = jdbcTemplate.query(FETCH_MONTHWISE_ALL, new Object[]{numberOfMonths}, monthWiseRevenueRowMapper);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return monthWiseRevenues;
    }
}
