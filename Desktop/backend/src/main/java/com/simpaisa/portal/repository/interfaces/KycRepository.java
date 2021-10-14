package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mongo.kyc.KYC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface KycRepository {

    public KYC save(KYC kyc);

    public KYC findByEmail(String email);

    public Page<KYC> findAllInReview(int step, Pageable pageable);

    public KYC findById(String id);
}
