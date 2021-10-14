package com.simpaisa.portal.entity.mysql.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddress implements Serializable {

    private String country;
    private String city;
    private String state;
    private String streetAddress;
    private String postalCode;
    private String landmark;
    private String freeformAddress;
}
