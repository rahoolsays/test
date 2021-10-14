package com.simpaisa.portal.service.role;

import com.simpaisa.portal.entity.mongo.Role;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.repository.interfaces.RoleRepository;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAll(){
        try {
            return roleRepository.getAll();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> saveRole(Role role){
        Map<String, Object> response = null;

        try {

            Role exists = roleRepository.findByRole(role.getRole());
            if (exists == null || role.getId() != null) {
                Role updatedRole = roleRepository.saveRole(role);
                if (updatedRole != null) {
                    response = Utility.getResponse(Responses.SUCCESS);
                }
            }
            else{
                response = Utility.getResponse(Responses.ROLE_ALREADY_EXISTS);
            }
        }
        catch (DuplicateKeyException dke){
            response = Utility.getResponse(Responses.ROLE_ALREADY_EXISTS);
        }
        catch(Exception e){
            response = Utility.getResponse(Responses.SYSTEM_ERROR);
        }

        return response;
    }

    public Map<String, Object> updateRole(Role role){
        Map<String, Object> response = null;

        try {

            Role exists = roleRepository.findById(role.getId());
           // if (exists == null || role.getId() != null) {
            if(exists.getRole().equalsIgnoreCase(role.getRole())) {
                Role updatedRole = roleRepository.saveRole(role);
                if (updatedRole != null) {
                    response = Utility.getResponse(Responses.SUCCESS);
                }
            }
            else
            {
                response = Utility.getResponse(Responses.INVALID_CALL);
            }
            //}
//            else{
//                response = Utility.getResponse(Responses.ROLE_ALREADY_EXISTS);
//            }
        }
        catch (DuplicateKeyException dke){
            response = Utility.getResponse(Responses.ROLE_ALREADY_EXISTS);
        }
        catch(Exception e){
            response = Utility.getResponse(Responses.SYSTEM_ERROR);
        }

        return response;
    }
}
