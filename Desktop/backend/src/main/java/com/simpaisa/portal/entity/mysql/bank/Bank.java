package com.simpaisa.portal.entity.mysql.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bank implements Serializable  {

    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
}
