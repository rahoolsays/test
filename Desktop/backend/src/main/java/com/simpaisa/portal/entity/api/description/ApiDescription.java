package com.simpaisa.portal.entity.api.description;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiDescription {

    @Id
    private String id;
    private String title;
    private String action;
    List<Heading> headings;
    List<String> apiActions;

}
