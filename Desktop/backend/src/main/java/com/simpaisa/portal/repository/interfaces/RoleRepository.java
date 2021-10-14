package com.simpaisa.portal.repository.interfaces;


import com.simpaisa.portal.entity.mongo.Role;

import java.util.List;

public interface RoleRepository {
    Role findByRole(String role);
    Role findById(String id);
    Role saveRole(Role role);
    List<Role> getAll();
}
