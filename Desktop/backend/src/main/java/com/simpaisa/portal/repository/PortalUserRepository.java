package com.simpaisa.portal.repository;

import com.mongodb.client.result.DeleteResult;
import com.simpaisa.portal.entity.mongo.PortalRole;
import com.simpaisa.portal.entity.mongo.PortalUser;
import com.simpaisa.portal.entity.mongo.User;
import com.simpaisa.portal.repository.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PortalUserRepository  {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PortalUserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public PortalUser findByEmail(String email) {
        PortalUser result = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(email));

            result =  mongoTemplate.findOne(query, PortalUser.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  result;
    }


    public List<PortalUser> findAll(){
        try{
            return mongoTemplate.findAll(PortalUser.class);
        }
        catch(Exception e){
            throw e;
            // return null;
        }
    }

    public Page<PortalUser> findAllPageable(Pageable pageable, String email){
        Page<PortalUser> result = null;
        //Query query = null;
        try{

            Query query = new Query().with(pageable);

            if(email != null){
                query.addCriteria(Criteria.where("email").regex(email));
            }

            List<PortalUser> users = mongoTemplate.find(query, PortalUser.class, "users");

            result = PageableExecutionUtils.getPage(
                    users,
                    pageable,
                    () -> mongoTemplate.count(query, PortalUser.class)
            );

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public List<PortalRole> getAllRolesForUser(){
        try{

            return mongoTemplate.findAll(PortalRole.class);

        }
        catch (Exception e){

        }
        return null;
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



    public PortalUser save(PortalUser user) {
        try {
            mongoTemplate.save(user);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return user;
    }


    public long countByBusinessName(String businessName) {

        Query query = new Query();
        query.addCriteria(Criteria.where("businessName").is(businessName));
        return mongoTemplate.count(query, User.class);
        // return 0;
    }


    public long countByWebsite(String website) {
        Query query = new Query();
        query.addCriteria(Criteria.where("website").is(website));
        return mongoTemplate.count(query, User.class);

    }

    public long delete(PortalUser user){

        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(user.getEmail()));
            DeleteResult result = mongoTemplate.remove(query, PortalUser.class);

            return result.getDeletedCount();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }
}
