package com.simpaisa.portal.entity.mysql.settlements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Settlement implements Serializable {

    private static final long serialVersionUID = 1L;

    private String displayName;
    private int percentile;
    private double value;
    private String applyOn;
}
