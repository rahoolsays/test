package com.simpaisa.portal.model.kyc.legalstructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicLimited {
    private  String cnicFront;
    private  String cnicBack;
    private  String accountMaintenanceCetificate;
    private  String ntnCertificate;
    private  String last3MonthBill;
    private boolean uploaded;
}
