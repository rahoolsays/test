package com.simpaisa.portal.entity.api;

import com.simpaisa.portal.enums.FieldType;
import com.simpaisa.portal.enums.InputType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "apiParameterList")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiParameterList {

    @Id
    private String id;
    private String key;
    private String value;
    private FieldType type;
    private InputType inputType;

}
