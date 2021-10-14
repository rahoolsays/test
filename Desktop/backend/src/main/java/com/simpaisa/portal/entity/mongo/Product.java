package com.simpaisa.portal.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Document(collection = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    private Long productID;
    private String productName;
    private Double amount;
    private String description;
    private boolean active;
    private Integer status;
    private Date createdDate;
    private Date updatedDate;
    private Long category;
    private Long merchantId;
    private Integer daysInterval;
    private String successUrl;
    private String failureUrl;
    private String responseUrl;
}
