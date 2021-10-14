package com.simpaisa.portal.repository;

import com.simpaisa.portal.entity.mongo.User;
import com.simpaisa.portal.repository.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User findByEmail(String email) {
        User result = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(email));

            result =  mongoTemplate.findOne(query, User.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  result;
    }

    @Override
    public User findByToken(String token) {
        User result = null;
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("resetToken").is(token));
            result = mongoTemplate.findOne(query, User.class);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return  result;
    }


//    @Override
//    public User findById(String id) {
//        User result = null;
//        try{
//            Query query = new Query();
//            query.addCriteria(Criteria.where("_id").is(id));
//            result = mongoTemplate.findOne(query, User.class);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return result;
//    }


    @Override
    public User save(User user) {
        try {
            mongoTemplate.save(user);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public long countByBusinessName(String businessName) {

        Query query = new Query();
        query.addCriteria(Criteria.where("businessName").is(businessName));
        return mongoTemplate.count(query, User.class);
       // return 0;
    }

    @Override
    public long countByWebsite(String website) {
        Query query = new Query();
        query.addCriteria(Criteria.where("website").is(website));
        return mongoTemplate.count(query, User.class);

    }
}
