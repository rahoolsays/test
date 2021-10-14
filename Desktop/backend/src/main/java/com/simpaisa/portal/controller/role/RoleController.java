package com.simpaisa.portal.controller.role;

import com.simpaisa.portal.entity.mongo.Role;
import com.simpaisa.portal.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("all")
    public ResponseEntity getAllRoles(){
        List<Role> roles = null;
        try{
            roles = roleService.getAll();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(roles,HttpStatus.OK);
    }

    @PostMapping("save")
    public ResponseEntity saveRole(@RequestBody Role role){
        Map<String, Object> response = null;
        response = roleService.saveRole(role);
        return ResponseEntity.ok(response);
    }

    @PostMapping("update")
    public ResponseEntity updateRole(@RequestBody Role role){
        Map<String, Object> response = null;
        response = roleService.updateRole(role);
        return ResponseEntity.ok(response);
    }
}
