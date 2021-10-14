package com.simpaisa.portal.entity.mysql.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "productID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productID;
    @Column(name = "productName")
    private String productName;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "description")
    private String description;
    //    private MerchantDetail merchantDetail;
    @Column(name ="active")
    private boolean active;
    @Column(name = "status")
    private Integer status;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
    @Column(name = "category")
    private Long category;
    @Column(name = "merchantID")
    private Long merchantId;
    @Column(name = "daysInterval")
    private Integer daysInterval;
    // Notification Url
//    private String accessUrl;
//    //    private String subscribeUrl;
//    private String unSubscribeUrl;
//    private String ussdCode;

    @Column(name = "successUrl")
    private String successUrl;
    @Column(name = "failureUrl")
    private String failureUrl;
    @Column(name = "responseUrl")
    private String responseUrl;
    @Column(name = "onLive")
    private int onLive;

//    private Integer freeTrialInterval;
//
//    private String code;
//    private String productReference;
}
