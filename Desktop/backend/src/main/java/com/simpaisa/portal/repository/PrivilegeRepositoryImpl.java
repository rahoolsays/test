package com.simpaisa.portal.repository;

import com.simpaisa.portal.entity.mongo.Privilege;
import com.simpaisa.portal.repository.interfaces.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrivilegeRepositoryImpl implements PrivilegeRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public PrivilegeRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Privilege> getMerchantDefaultPrivileges(){
    List<Privilege> privileges = null;
    try{
        Query query = new Query();
        query.addCriteria(Criteria.where("merchantDefault").is(true));
        privileges = mongoTemplate.find(query, Privilege.class);

    }
    catch (Exception e){
        e.printStackTrace();
    }

    return privileges;

    }

    @Override
    public List<Privilege> getAdminDefaultPrivileges(){
        List<Privilege> privileges = null;
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("adminDefault").is(true));
            privileges = mongoTemplate.find(query, Privilege.class);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return privileges;

    }

    @Override
    public List<Privilege> getAllPrivileges() {
        try{
            return mongoTemplate.findAll(Privilege.class);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Privilege findByCode(String code) {
        Privilege privilege = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("code").is(code));
            privilege = mongoTemplate.findOne(query, Privilege.class);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return privilege;
    }

    @Override
    public Privilege save(Privilege privilege) {
        try {
            mongoTemplate.save(privilege);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return privilege;
    }
}
