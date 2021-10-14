package com.simpaisa.portal.controller;

import com.simpaisa.portal.config.security.JwtTokenProvider;
import com.simpaisa.portal.entity.mongo.User;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.service.AuthService;
import com.simpaisa.portal.utility.Utility;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {


    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody HashMap<String,String> data){
        Map<String , Object> response = null;
        try {
          response = authService.login(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response, HttpStatus.OK);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity register(@RequestBody User user){

        System.out.println("user = " + user);
        Map<String , Object> response = null;
        try {
            response = authService.register(user);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response, HttpStatus.OK);
    }


    @PostMapping("/resendEmail")
    public ResponseEntity resendEmail(@RequestBody HashMap<String, String> data){

        Map<String , Object> response = null;
        try{
            response = authService.resendEmail(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response , HttpStatus.OK);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity forgotPassword(@RequestBody HashMap<String, String> data){

        Map<String , Object> response = null;
        try{
            response = authService.forgotPassword(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response , HttpStatus.OK);
    }


    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody HashMap<String, String> data){

        Map<String , Object> response = null;
        try{
            response = authService.changePassword(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response , HttpStatus.OK);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity refreshToken(HttpServletRequest request){
        Map<String, Object> response = new LinkedHashMap<>();
        DefaultClaims claims = (DefaultClaims) request.getAttribute("claims");
        String token = jwtTokenProvider.createRefreshToken(claims);

        if(token.equals("not-expired")){
            response.put(Utility.MESSAGE, Responses.NOT_EXPIRED.getMessage());
            response.put(Utility.STATUS, Responses.NOT_EXPIRED.getStatus());
        }else {
            response.put(Utility.MESSAGE, Responses.SUCCESS.getMessage());
            response.put(Utility.STATUS, Responses.SUCCESS.getStatus());
            response.put(Utility.TOKEN, token);
        }
        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response, HttpStatus.OK);

    }







//    @GetMapping("/verifyEmail/{id}")
//    public  ResponseEntity verifyEmail(@PathVariable(name = "id") String id){
//        System.out.println("id = " + id);
//        User user = null;
//        Map<String, Object> model = new HashMap<>();
//        try{
//            user = mongoTemplate.findById(id, User.class);
//            if(user!=null){
//                user.setVerified(true);
//                mongoTemplate.save(user);
//                model.put("message", Responses.SUCCESS.getMessage());
//                model.put("status",  Responses.SUCCESS.getStatus());
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//            model.put("message", Responses.SYSTEM_ERROR.getMessage());
//            model.put("status", Responses.SYSTEM_ERROR.getStatus());
//        }
//
//        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)model, HttpStatus.OK);
//    }
    @GetMapping("/resource")
    public Map<String, Object> home(){
        Map<String, Object> model = new HashMap<>();

        try {

            model.put("id", UUID.randomUUID().toString());
            model.put("content", "Hello World");
        }catch (Exception ex){
            ex.printStackTrace();

        }
        return model;
    }
}
