package com.simpaisa.portal.repository.api;

import com.simpaisa.portal.entity.api.request.ApiParameterList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiParameterListRepo extends MongoRepository<ApiParameterList , String> {
}
