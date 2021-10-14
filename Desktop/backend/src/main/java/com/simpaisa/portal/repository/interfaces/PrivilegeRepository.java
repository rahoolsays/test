package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mongo.Privilege;

import java.util.List;

public interface PrivilegeRepository {

    Privilege findByCode(String code);
    Privilege save(Privilege privilege);
    List<Privilege> getMerchantDefaultPrivileges();
    List<Privilege> getAdminDefaultPrivileges();
    List<Privilege> getAllPrivileges();
}
