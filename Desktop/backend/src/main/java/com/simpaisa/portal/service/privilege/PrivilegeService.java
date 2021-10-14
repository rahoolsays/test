package com.simpaisa.portal.service.privilege;

import com.simpaisa.portal.entity.mongo.Privilege;
import com.simpaisa.portal.repository.interfaces.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {

    @Autowired
    PrivilegeRepository privilegeRepository;

    public List<Privilege> getAllPrivileges(){
        try{
            return privilegeRepository.getAllPrivileges();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Privilege> getMerchantDefaultPrivileges(){
        try{
            return privilegeRepository.getMerchantDefaultPrivileges();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public List<Privilege> getAdminDefaultPrivileges(){
        try{
            return privilegeRepository.getAdminDefaultPrivileges();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
