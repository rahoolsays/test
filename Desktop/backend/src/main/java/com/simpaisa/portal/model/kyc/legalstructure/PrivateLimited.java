package com.simpaisa.portal.model.kyc.legalstructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateLimited {
   private String cnicFront;
    private String cnicBack;
    private String formA;
    private String incorporationCetificate;
    private String articleOfAssociation ;
    private String ntnCertificate;
    private boolean uploaded;
}
