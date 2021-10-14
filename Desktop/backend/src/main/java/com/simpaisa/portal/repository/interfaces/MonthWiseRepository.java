package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mysql.reporting.monthwise.MonthWiseRevenue;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface MonthWiseRepository {
    public List<MonthWiseRevenue> fetchMonthWiseRevenue(long merchantId, int numberOfMonths);
    public List<MonthWiseRevenue> fetchMonthWiseRevenueALL(int numberOfMonths);
}
