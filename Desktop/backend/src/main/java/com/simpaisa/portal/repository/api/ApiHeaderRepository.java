package com.simpaisa.portal.repository.api;

import com.simpaisa.portal.entity.api.request.ApiHeaders;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiHeaderRepository extends MongoRepository<ApiHeaders , String> {
}
