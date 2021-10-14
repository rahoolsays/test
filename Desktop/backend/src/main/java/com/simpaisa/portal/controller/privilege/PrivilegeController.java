package com.simpaisa.portal.controller.privilege;

import com.simpaisa.portal.entity.mongo.Privilege;
import com.simpaisa.portal.service.privilege.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("privileges")
public class PrivilegeController {

    @Autowired
    PrivilegeService privilegeService;

    @PostMapping("all")
    public ResponseEntity getAllPrivileges(){
        List<Privilege> response = null;
        try{
            response = privilegeService.getAllPrivileges();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);
    }
}
