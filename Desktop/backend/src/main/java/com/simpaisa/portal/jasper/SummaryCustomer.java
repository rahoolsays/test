package com.simpaisa.portal.jasper;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class SummaryCustomer {
    public String custtext;
    public Double custamount;
    public String merctext;
    public Double mercamount;
}
