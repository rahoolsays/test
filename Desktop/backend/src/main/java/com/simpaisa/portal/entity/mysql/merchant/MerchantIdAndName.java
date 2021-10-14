package com.simpaisa.portal.entity.mysql.merchant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MerchantIdAndName {
    private static final long serialVersionUID = 1L;
    private long id;
    private String firstName;
    private String lastName;
}
