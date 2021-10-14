package com.simpaisa.portal.entity.mysql.productconfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_configuration")
public class ProductConfiguration {
    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "active_Wifi")
    private boolean activeWifi;
    @Column(name = "active_Bucket")
    private boolean activeBucket;
//    @Column(name = "direct_payment")
//    private boolean directPayment;
    @Column(name = "active_Reminder")
    private boolean activeReminder;
    @Column(name = "productID")
    private Long productId;


//    @Column(name = "freeTrialInterval")
//    private int freeTrialInterval;
    @Column(name = "active_Retry")
    private boolean activeRetry;

    @Column(name = "active_HE")
    private boolean activeHe;

    @Column(name = "operatorID")
    private Long operatorId;


    @Column(name = "active_freeTrial")
    private boolean activefreeTrial;
//    @Column(name = "service_id")
//    private String serviceId;
//    @Column(name = "subscription_sms")
//    private boolean subscriptionSms;
//    @Column(name = "recursion_sms")
//    private boolean recursionSms;
//    @Column(name = "unsub_sms")
//    private boolean unsubSms;

    @Column(name = "upgrade_products")
    private String upgradeProducts;
    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name="createdBy")
    private String createdBy;



}
