package com.simpaisa.portal.repository;

import com.simpaisa.portal.entity.mongo.kyc.KYC;
import com.simpaisa.portal.repository.interfaces.KycRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KycRepositoryImpl implements KycRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public KycRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public KYC save(KYC kyc) {
        KYC kycResult = null;
        try{
            kycResult = mongoTemplate.save(kyc);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kyc;
    }

    @Override
    public KYC findByEmail(String email) {
        KYC kyc = null;
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(email));

            kyc = mongoTemplate.findOne(query, KYC.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kyc;
    }

    @Override
    public Page<KYC> findAllInReview(int step, Pageable pageable) {
        Page<KYC> result = null;
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("step").ne(step)).
                    with(pageable)
                    .skip(pageable.getPageSize()*pageable.getPageNumber())
                    .limit(pageable.getPageSize());

            List<KYC> kycList = mongoTemplate.find(query, KYC.class);
            long count = mongoTemplate.count(query.skip(-1).limit(-1), KYC.class);

            result = new PageImpl<KYC>(kycList, pageable, count);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public KYC findById(String id) {
        KYC kyc = null;
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(id));

            kyc = mongoTemplate.findOne(query, KYC.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kyc;
    }
}
