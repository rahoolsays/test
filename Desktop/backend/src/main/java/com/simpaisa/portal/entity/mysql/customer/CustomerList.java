package com.simpaisa.portal.entity.mysql.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "disbursement_customer")
public class CustomerList implements Serializable {

    @Id
    @Column(name = "reference")
    private String reference;

    @Column(name = "merchantId")
    @JsonIgnore
    private Long merchantId;

    @Column(name = "name")
    private String customerName;

    @Column(name = "account")
    private String customerAccount;

    @Column(name = "accountType")
    private String accountType;

    @Column(name = "destinationBank")
    private String destinationBank;

    @Column(name = "active")
    private Integer active;
}
