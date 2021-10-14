package com.simpaisa.portal.entity.mysql.reporting.monthwise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthWiseRevenue {
    String monthName;
    Double amount;
}
