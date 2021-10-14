package com.simpaisa.portal.repository.reporting;

import com.simpaisa.portal.entity.mysql.reporting.Recursion;
import com.simpaisa.portal.entity.mysql.reporting.RevenueTotal;
import com.simpaisa.portal.repository.interfaces.ReportingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ReportingRepositoryImpl implements ReportingRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static String FETCH_NEW_TRIALS = "select * from recursion where merchantID=? and (date(createdDate) between ? and ?) and operatorID=? and typeId=13";

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
    public RevenueTotal Revenue() {
        RevenueTotal totalRevenue = null;
        try{
//            RowMapper<Map<String,Object>> rowMapper = new BeanPropertyRowMapper();
            String sql="select sum(amount) as amount from paymentlogs";
            totalRevenue = jdbcTemplate.queryForObject(sql,  BeanPropertyRowMapper.newInstance(RevenueTotal.class));
//            totalRevenue = jdbcTemplate.queryForObject("select sum(amount) as amount from paymentlogs where merchantID="+merchantId+"", new Object[]{}, rowMapper);
//            System.out.println(totalRevenue);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return totalRevenue;
    }
    @Override
    public List<Recursion> newTrialsList(long merchantId, String from_date, String to_date, String operatorID) {
        List<Recursion> newTrialsList=null;
        try{
            if(operatorID.equals("all")){
                FETCH_NEW_TRIALS = "select * from recursion where merchantID=? and (date(createdDate) between ? and ?) and typeId=13";
                Object[] args= {merchantId, from_date,to_date};
                newTrialsList =jdbcTemplate.query(FETCH_NEW_TRIALS, args, new BeanPropertyRowMapper(Recursion.class));
            }
            else{
                FETCH_NEW_TRIALS = "select * from recursion where merchantID=? and (date(createdDate) between ? and ?) and operatorID=? and typeId=13";
                Object[] args= {merchantId, from_date,to_date,operatorID};
                newTrialsList =jdbcTemplate.query(FETCH_NEW_TRIALS, args, new BeanPropertyRowMapper(Recursion.class));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return newTrialsList;

    }

}
