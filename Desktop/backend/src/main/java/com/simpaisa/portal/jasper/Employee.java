package com.simpaisa.portal.jasper;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data @Getter @Setter @ToString
public class Employee {

    public String tax;
    public Double perc;
}
