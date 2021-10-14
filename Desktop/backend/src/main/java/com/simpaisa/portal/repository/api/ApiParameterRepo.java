package com.simpaisa.portal.repository.api;

import com.simpaisa.portal.entity.api.request.ApiParameters;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiParameterRepo extends MongoRepository<ApiParameters , String> {
}
