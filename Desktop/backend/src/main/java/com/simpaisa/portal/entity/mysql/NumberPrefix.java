package com.simpaisa.portal.entity.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "number_prefix")
public class NumberPrefix {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @Column(name = "prefixID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prefixID;

    @Column(name = "active")
    private boolean active;

    @Column(name = "prefix")
    private String prefix;


}
