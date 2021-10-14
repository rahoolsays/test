package com.simpaisa.portal.repository;

import com.simpaisa.portal.entity.mongo.Role;
import com.simpaisa.portal.repository.interfaces.RoleRepository;
import com.simpaisa.portal.utility.Utility;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {


    private final MongoTemplate mongoTemplate;

    @Autowired
    public RoleRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Role findByRole(String role) {
        Role result = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("role").is(role));
            result = mongoTemplate.findOne(query, Role.class);
        }catch (Exception ex){
            throw ex;
        }

        return result;
    }

    @Override
    public Role findById(String id) {
        Role result = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(id));
            result = mongoTemplate.findOne(query, Role.class);
        }catch (Exception ex){
            throw ex;
        }

        return result;
    }

    @Override
    public Role saveRole(Role role) {
        try {
            mongoTemplate.save(role);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return role;
    }

    @Override
    public List<Role> getAll() {
        List<Role> roles = null;
        try{
            Query query = new Query();
            List<String> restrictedRoles = new ArrayList<>();
            restrictedRoles.add(Utility.ROLE_ADMIN);
            restrictedRoles.add(Utility.ROLE_MERCHANT);
            query.addCriteria(Criteria.where("role").nin(restrictedRoles));
            System.out.println(query.toString());
            roles = mongoTemplate.find(query, Role.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return roles;
    }
}
