package com.simpaisa.portal.entity.mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "privileges")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Privilege {

    @Id
    private String id;

    private String code;

    private String name;

    private String type;
    private String path;
    private String title;
    private String rtlTitle;
    private String icon;
    private Boolean merchantDefault;
    private Boolean adminDefault;
    private Boolean createTab;
    private Integer sortOrder;
}
