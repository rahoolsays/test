package com.simpaisa.portal.entity.mysql.disbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisbSettlementDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String displayName;
    private Double value;
    private Integer percentile;
    private Double amount;
}
