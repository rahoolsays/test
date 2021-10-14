package com.simpaisa.portal.entity.mysql.operator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OperatorIdAndName {

    private static final long serialVersionUID = 1L;
    private long id;
    private String Name;
}
