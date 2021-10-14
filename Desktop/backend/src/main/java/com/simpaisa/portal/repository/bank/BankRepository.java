package com.simpaisa.portal.repository.bank;

import com.simpaisa.portal.entity.mysql.bank.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BankRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SQL_GET_ALL = " select code, name from bank_details where status = 1";

    public List<Bank> getAll(){
        try{
            return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<>(Bank.class));
        }
        catch (Exception e){
            throw e;
        }
    }
}
