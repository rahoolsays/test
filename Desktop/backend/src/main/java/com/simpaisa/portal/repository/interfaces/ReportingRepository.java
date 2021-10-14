package com.simpaisa.portal.repository.interfaces;
import com.simpaisa.portal.entity.mysql.reporting.Recursion;
import java.util.List;
public interface ReportingRepository {
    public List<Recursion> newTrialsList(long merchantId, String from_date, String to_date, String operatorID);
}