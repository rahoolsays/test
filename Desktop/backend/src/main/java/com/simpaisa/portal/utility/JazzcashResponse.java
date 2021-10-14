package com.simpaisa.portal.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JazzcashResponse {

    String response;
    String responseCode;
    String otcToken;
    String tid;


}