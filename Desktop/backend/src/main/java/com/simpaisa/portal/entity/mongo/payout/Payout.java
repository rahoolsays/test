package com.simpaisa.portal.entity.mongo.payout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
@Document(collection = "payout")
public class Payout {
    @Id
    private String id;
    private String email;
    private String type;
    private String currency;
    private String ibn;
    private String bankLocation;
    private String bankName;
    private String bic;
    private Date createdDate;
    private Date updatedDate;

}
