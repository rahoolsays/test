package com.simpaisa.portal.entity.mongo.kyc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "kyc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KYC {

    @Id
    private String id;
    private String email;
    private String physicalCountry;
    private String physicalCity;
    private String legalStructure;
    private String legalBusinessAddress;
    private Date businessSince;
    private Date createdDate;
    private Date updatedDate;
    private String category;
    private String paymentCurrency;
    private double mounthlySales;
    private double mounthlyVolume;
    private double dailyTransactionCount;
    private double dailyTransactionAmount;

    private double monthlyTransactionCount;
    private double monthlyTransactionAmount;

    private double yearlyTransactionCount;
    private double yearlyTransactionAmount;
    private double minimumTransactionAmount;
    private double maximumTransactionAmount;
    private double mounthlySalesBefore;
    private double mounthlySalesAfter;
    private String websiteUrl;
    private boolean agree;
    private int step;
    private Map<String, Boolean> services;
    private BusinessPoc businessPoc = new BusinessPoc();
    private FinancePoc financePoc = new FinancePoc();
    private TechPoc techPoc = new TechPoc();

    private Long merchantId;

}
