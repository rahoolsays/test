package com.simpaisa.portal.entity.mysql.reporting.monthwise;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MonthWiseRevenueMapper implements RowMapper<MonthWiseRevenue> {
    @Override
    public MonthWiseRevenue mapRow(ResultSet rs, int i) throws SQLException {
        MonthWiseRevenue monthWiseRevenue = null;
        try{
            monthWiseRevenue = new MonthWiseRevenue();
            monthWiseRevenue.setMonthName(rs.getString("monthName"));
            monthWiseRevenue.setAmount(rs.getDouble("amount"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return monthWiseRevenue;
    }
}
