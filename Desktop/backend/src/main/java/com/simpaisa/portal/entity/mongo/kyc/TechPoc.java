package com.simpaisa.portal.entity.mongo.kyc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechPoc {

    private String contactPerson;
    private String contactNumber;
    private String contactEmail;
    private String designation;
}
