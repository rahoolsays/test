package com.simpaisa.portal.entity.mysql.operator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operator {
    private Integer operatorID;
    private Integer active;
    private String contact;
    private Integer countryID;
    private Date createdDate;
    private String description;
    private String operatorName;
    private String prefix;
    private String revenueType;
    private String revenueValue;
    private Date updatedDate;
    private String channelId;
}
