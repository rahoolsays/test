package com.simpaisa.portal.repository.api;

import com.simpaisa.portal.entity.api.request.ApiData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends MongoRepository<ApiData , String> {

    ApiData findByTitle(String title);

}
