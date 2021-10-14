package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mongo.User;

public interface UserRepository {
    User findByEmail(String email);
    User findByToken(String token);
//    User findById(String id);
    User save(User user);
    long countByBusinessName(String businessName);
    long countByWebsite(String website);


}
