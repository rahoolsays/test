package com.simpaisa.portal.repository;

import com.simpaisa.portal.entity.mysql.NumberPrefix;
import com.simpaisa.portal.entity.mysql.merchant.OperatorMapperIdAndName;
import com.simpaisa.portal.entity.mysql.operator.Operator;
import com.simpaisa.portal.entity.mysql.operator.OperatorIdAndName;
import com.simpaisa.portal.repository.interfaces.OperatorRepository;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OperatorRepositoryImpl implements OperatorRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Autowired
    private JdbcTemplate jdbcTemplateLive;



    private final String FETCH_OPERATORS = "select operatorID,operatorName from operator ";
    private final String FETCH_OPERATORS_NOT_CONFIGURED = "select operatorID,operatorName from operator o1 LEFT JOIN product_configuration o2 ON o2.operatorID = o1.operatorID WHERE o2.productID o2.operatorID IS NULL ";

    @Override
    public List<OperatorIdAndName> getAllOperators() {

        List<OperatorIdAndName> operatorIdAndNames = null;
        try{
            RowMapper<OperatorIdAndName> rowMapper = new OperatorMapperIdAndName();
            operatorIdAndNames = jdbcTemplate.query(FETCH_OPERATORS, new Object[]{}, rowMapper);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return operatorIdAndNames;

    }

    @Override
    public List<OperatorIdAndName> getAllOperatorsNotConfigured(int ProductId) {

        List<OperatorIdAndName> operatorIdAndNames = null;
        try{
            RowMapper<OperatorIdAndName> rowMapper = new OperatorMapperIdAndName();
            operatorIdAndNames = jdbcTemplate.query("select operatorID,operatorName from operator where operatorID not in (SELECT a.operatorID FROM operator AS a LEFT JOIN product_configuration as b ON a.operatorID = b.operatorID WHERE b.productID="+ProductId+" )", new Object[]{}, rowMapper);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return operatorIdAndNames;

    }

    @Override
    public OperatorIdAndName findByOperatorId(int OperatorId) {

        OperatorIdAndName operatorIdAndNames = null;
        try{
            RowMapper<OperatorIdAndName> rowMapper = new OperatorMapperIdAndName();
            operatorIdAndNames = jdbcTemplate.queryForObject("select operatorID,operatorName from operator where operatorID="+OperatorId+"", new Object[]{}, rowMapper);
            System.out.println(operatorIdAndNames);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return operatorIdAndNames;

    }

    @Override
    public Operator findOperatorId(int OperatorId,short environment) {
        JdbcTemplate useJdbcTemplate;
        if(environment == Utility.JDBC_LIVE){
            useJdbcTemplate = jdbcTemplateLive;
        }else{
            useJdbcTemplate = jdbcTemplate;
        }
        Operator operator = null;
        try{
            operator = useJdbcTemplate.queryForObject("select * from operator  where operatorID="+OperatorId, new Object[]{}, BeanPropertyRowMapper.newInstance(Operator.class));
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return operator;

    }

    @Override
    public Page<Operator> listOperators(Pageable pageable,short environment) {
        Page<Operator> operators=null;

        JdbcTemplate useJdbcTemplate;
        if(environment == Utility.JDBC_LIVE){
            useJdbcTemplate = jdbcTemplateLive;
        }else{
            useJdbcTemplate = jdbcTemplate;
        }
        try{
            List<Operator> operatorsList = useJdbcTemplate.query("select * from operator  LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset(), new Object[]{}, BeanPropertyRowMapper.newInstance(Operator.class));
            int count = useJdbcTemplate.queryForObject("SELECT count(*) FROM operator", Integer.class);
            operators=new PageImpl<Operator>(operatorsList, pageable, count);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return operators;
    }

    @Override
    public void save(Operator operator, short environment) {
        JdbcTemplate useJdbcTemplate;
        if(environment == Utility.JDBC_LIVE){
            useJdbcTemplate = jdbcTemplateLive;
        }else{
            useJdbcTemplate = jdbcTemplate;
        }
        System.out.println(operator);
        useJdbcTemplate.update("update operator set active=?,contact=?,countryID=?,createdDate=?,description=?,operatorName=?,updatedDate=?,channelId=? where operatorID=?",operator.getActive(),operator.getContact(),operator.getCountryID(),operator.getCreatedDate(),operator.getDescription(),operator.getOperatorName(),operator.getUpdatedDate(),operator.getChannelId(), operator.getOperatorID());
    }

    @Override
    public Page<NumberPrefix> listPrefix(Pageable pageable, short environment) {
        Page<NumberPrefix> numberPrefixes=null;
        JdbcTemplate useJdbcTemplate;
        if(environment == Utility.JDBC_LIVE){
            useJdbcTemplate = jdbcTemplateLive;
        }else{
            useJdbcTemplate = jdbcTemplate;
        }
        try{
            List<NumberPrefix> numberPrefixesList = useJdbcTemplate.query("select * from number_prefix  LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset(), new Object[]{}, BeanPropertyRowMapper.newInstance(NumberPrefix.class));
            int count = useJdbcTemplate.queryForObject("SELECT count(*) FROM number_prefix", Integer.class);
            numberPrefixes=new PageImpl<NumberPrefix>(numberPrefixesList, pageable, count);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return numberPrefixes;
    }
}
