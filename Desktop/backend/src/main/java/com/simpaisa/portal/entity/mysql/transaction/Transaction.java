package com.simpaisa.portal.entity.mysql.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {
    @Id
    @Column(name = "transactionID")
    private Long transactionId;

    @Column(name = "amount")
    private float amount;
    @Column(name = "mobileNo")
    private String mobileNo;
    @Column(name = "status")
    private int status;
    @Column(name = "transactionType")
    private int type;
    @Column(name = "merchantID")
//    @JsonIgnore
    private Long merchantId;
    @Column(name = "operatorID")
    private Long operatorId;
    @Column(name = "productID")
    private Long productId;
    @Column(name = "userKey")
//    @JsonIgnore
    private String userKey;
    @Column(name = "createdDate")
    private Timestamp createdDate;
    @Column(name = "updatedDate")
    private Timestamp updatedDate;


}
