package com.simpaisa.portal.controller.user;

import com.simpaisa.portal.entity.mongo.PortalRole;
import com.simpaisa.portal.entity.mongo.PortalUser;
import com.simpaisa.portal.entity.mongo.User;
import com.simpaisa.portal.service.user.PortUserService;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class PortUserController {

    @Autowired
    PortUserService portUserService;

    @PostMapping("all")
    public ResponseEntity getAll(@RequestBody Map<String, Object> data){
        Page<PortalUser> userList = null;

        try{
            userList = portUserService.getAll(data);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(userList);

    }

    @PostMapping("roles")
    public ResponseEntity getAllRoles(){
        List<PortalRole> roles = null;

        try {
            roles = portUserService.getAllRoles();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(roles);
    }

    @PostMapping("save")
    public  ResponseEntity save(@RequestBody PortalUser user){
        Map<String, Object> response = null;

        try{
            response = portUserService.saveUser(user);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);

    }

    @PostMapping("update")
    public  ResponseEntity update(@RequestBody PortalUser user){
        Map<String, Object> response = null;

        try{
            response = portUserService.updateUser(user);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);

    }

    @PostMapping("get")
    public ResponseEntity getUser(@RequestBody Map<String, Object> data){

        PortalUser user = null;

        try{
            user = portUserService.getUser(data.get(Utility.EMAIL).toString());

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("delete")
    public ResponseEntity deleteUser(@RequestBody PortalUser user){

        Map<String, Object> response = null;

        try {
            response = portUserService.deleteUser(user);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);
    }


}
