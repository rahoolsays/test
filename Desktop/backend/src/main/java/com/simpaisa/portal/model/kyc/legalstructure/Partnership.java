package com.simpaisa.portal.model.kyc.legalstructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partnership {
    private String cnicFront;
    private String cnicBack;
    private String partnershipDeed;
    private String authorityLetter;
    private String merchantAgreement ;
    private String ntnCertificate;
    private boolean uploaded;
}
