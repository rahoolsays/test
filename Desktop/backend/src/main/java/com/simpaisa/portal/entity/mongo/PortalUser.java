package com.simpaisa.portal.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class PortalUser {
    @Id
    private String id;

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String email;

    private String password;
    //    private String fullname;
    private String title;
    private String firstName;
    private String lastName;
    private String businessName;
    private String businessCountry;
    private String timeZone;
    private String findUs;

    private String website;
    private boolean enabled;
    private boolean verified;
    @DBRef
    private Set<PortalRole> roles;
}
