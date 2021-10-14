package com.simpaisa.portal.jasper;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
@Getter
@Setter
@ToString
public class Summary {
    public List<SummaryEmployee> e;
    public List<SummaryCustomer> c;
}
