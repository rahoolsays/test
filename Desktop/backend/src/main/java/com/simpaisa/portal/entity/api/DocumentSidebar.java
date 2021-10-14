package com.simpaisa.portal.entity.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "documentSidebar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSidebar {

    @Id
    private String id;
    private String text;
    private String icon;
    private String action;
    private String menuFatherId;
    private String opacity;
    private String children;
    private String apiType;
    private String isCollapsed;


}
