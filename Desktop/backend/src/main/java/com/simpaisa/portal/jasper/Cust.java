package com.simpaisa.portal.jasper;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@Data
public class Cust {
    public String taxName;
    public String perc;
    public Double amount;
}
