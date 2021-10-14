package com.simpaisa.portal.repository.api;

import com.simpaisa.portal.entity.api.description.ApiDescription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DescriptionRepo extends MongoRepository<ApiDescription, String> {
}
