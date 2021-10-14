package com.simpaisa.portal.entity.api.request;


import com.simpaisa.portal.enums.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "apiHeader")
@Data @NoArgsConstructor @AllArgsConstructor
public class ApiHeaders {

    @Id
    private String id;
    private String key;
    private String value;
    private FieldType type;




}
