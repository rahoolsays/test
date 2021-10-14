package com.simpaisa.portal.entity.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "apiData")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiData {

    @Id
    private String id;
    private String title;
    private String mainHeading;
    private String mainDescription;
    private String subHeading;
    private String subDescription;
    private String endPoint;
    private String action;
    private String endUrl;
    private String method;
    @DBRef
    private Set<ApiHeaders> header;
    @DBRef
    private Set<ApiBody> body;




}
