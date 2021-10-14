package com.simpaisa.portal.entity.mongo.revenue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor

@Document(collection = "revenue_share")
public class RevenueShare {
    @Id
    private String id;
    private String country;
    private String operator;
    private float endUserPrice;
    private float priceExVat;
    private String currency;
    private float payout;
    private float payoutPercent;
    private int payoutCycle;
    private Date createdDate;
    private Date updatedDate;
}
