package com.simpaisa.portal.entity.api.request;


import com.simpaisa.portal.enums.FieldType;
import com.simpaisa.portal.enums.InputType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Document(collection = "apiBody")
@Data @NoArgsConstructor @AllArgsConstructor
public class ApiBody {

    @Id
    private String id;
    private String key;
    private String value;
    private FieldType type;
    private InputType inputType;
    @DBRef
    private Set<ApiParameters> parameter;



}
