package com.simpaisa.portal.entity.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Document(collection = "apiParameter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiParameters {
    @Id
    private String id;
    private String value;
    private String withMerchantId;
    @DBRef
    private Set<ApiParameterList> parameterList;

}
