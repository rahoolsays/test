package com.simpaisa.portal.repository;

import com.simpaisa.portal.entity.mongo.kyc.KYC;
import com.simpaisa.portal.entity.mysql.product.Product;
import com.simpaisa.portal.repository.interfaces.ProductRepo;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ProductRepositoryImpl implements ProductRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplateLive;

    private final MongoTemplate mongoTemplate;

    private final String FETCH_OPERATORS = "select operatorID,operatorName from operator ";
    private final String INSERT = "insert into product(active, amount, createdDate, description, failureUrl, productName, successUrl,merchantID,responseUrl,status,category) values (?, ?, ?, ?, ?, ?, ?,?,?,?,?)";

    @Autowired
    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }



    @Override
    public long insert(Product product, short environment){
        long id = 0l;
        JdbcTemplate useJdbcTemplate;
        if(environment == Utility.JDBC_LIVE){
            useJdbcTemplate = jdbcTemplateLive;
        }else{
            useJdbcTemplate = jdbcTemplate;
        }
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            useJdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                    PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{"merchantId"});
                    try {
                        ps.setBoolean(1,true);
                        ps.setDouble(2, product.getAmount());
                        ps.setDate(3, convertTimestamp((Timestamp) product.getCreatedDate()));
                        ps.setString(4, product.getDescription());
                        ps.setString(5, product.getFailureUrl());
                        ps.setString(6, product.getProductName());
                        ps.setString(7, product.getSuccessUrl());
                        ps.setLong(8, product.getMerchantId());
                        ps.setString(9, product.getResponseUrl());
                        ps.setInt(10, product.getStatus());
                        ps.setLong(11,product.getCategory());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    return ps;
                }
            }, keyHolder);

            id = keyHolder.getKey().longValue();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public Product findById(long id, short environment) {
        Product product = null;
        JdbcTemplate useJdbcTemplate;
        if(environment == Utility.JDBC_LIVE){
            useJdbcTemplate = jdbcTemplateLive;
        }else{
            useJdbcTemplate = jdbcTemplate;
        }

        try {
            String sql="select active, amount, createdDate, description, failureUrl, productName, successUrl,merchantID,responseUrl,status,category from product where productID = ?";
            Object[] args= { id };
            product = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Product.class));
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
        return product;
    }

    @Override
    public com.simpaisa.portal.entity.mongo.Product findByIdMongo(short environment) {
        com.simpaisa.portal.entity.mongo.Product product = null;
        JdbcTemplate useJdbcTemplate;
        if(environment == Utility.JDBC_LIVE){
            useJdbcTemplate = jdbcTemplateLive;
            System.out.println("Done");
        }else{
            useJdbcTemplate = jdbcTemplate;
        }
        try {
            String sql="select productID,active, amount, createdDate, description, failureUrl, productName, successUrl,merchantID,responseUrl,status,category from product where productID = (SELECT MAX(productID) FROM product)";
            product = useJdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(com.simpaisa.portal.entity.mongo.Product.class));
            System.out.println("Product moving to mongo "+product);
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
        return product;
    }

    @Override
    public com.simpaisa.portal.entity.mongo.Product save(com.simpaisa.portal.entity.mongo.Product product) {
        com.simpaisa.portal.entity.mongo.Product productResult = null;
        try{
            productResult = mongoTemplate.save(product);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return product;
    }

    static final java.sql.Date convertTimestamp(final java.sql.Timestamp timestamp)
    {
        java.sql.Date returnValue;

        if (timestamp != null)
        {
            returnValue = new java.sql.Date(timestamp.getTime());
        }
        else
        {
            returnValue = null; // an exception might be better here.
        }

        return returnValue;
    }
}
