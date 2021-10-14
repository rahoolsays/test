package com.simpaisa.portal.repository.reporting;

import com.simpaisa.portal.entity.mysql.reporting.Recursion;
import com.simpaisa.portal.repository.interfaces.NewSubscribersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NewSubscribersRepositoryImpl implements NewSubscribersRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String FETCH_NEW_SUBSCRIBERS = "select * from recursion where merchantID=? and (date(createdDate) between ? and ?) and operatorID=?";

    @Override
    public List<Recursion> newSubscribersList(long merchantId, String from_date, String to_date, String operatorID) {
        List<Recursion> newSubscribersList=null;
        try{
            if(operatorID.equals("all")){
                FETCH_NEW_SUBSCRIBERS = "select * from recursion where merchantID=? and (date(createdDate) between ? and ?)";
                System.out.println("FETCH_NEW_SUBSCRIBERS :: "+FETCH_NEW_SUBSCRIBERS);
                Object[] args= {merchantId, from_date,to_date};
                System.out.println(from_date+"         "+to_date);
                newSubscribersList =jdbcTemplate.query(FETCH_NEW_SUBSCRIBERS, args, new BeanPropertyRowMapper(Recursion.class));
            }
            else{
                FETCH_NEW_SUBSCRIBERS = "select * from recursion where merchantID=? and (date(createdDate) between ? and ?) and operatorID=?";
                Object[] args= {merchantId, from_date,to_date,operatorID};
                newSubscribersList =jdbcTemplate.query(FETCH_NEW_SUBSCRIBERS, args, new BeanPropertyRowMapper(Recursion.class));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return newSubscribersList;

    }
}
