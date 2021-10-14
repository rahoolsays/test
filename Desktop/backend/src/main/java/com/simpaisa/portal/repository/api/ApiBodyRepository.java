package com.simpaisa.portal.repository.api;

import com.simpaisa.portal.entity.api.request.ApiBody;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiBodyRepository extends MongoRepository<ApiBody, String> {
}
