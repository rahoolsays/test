package com.simpaisa.portal.controller.email;

import com.simpaisa.portal.service.email.EmailGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("email")
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private EmailGenericService emailService;


    @PostMapping("/send")
    public ResponseEntity send(@RequestBody HashMap<String,String> data){
        Map<String , Object> response = null;
        try {
            response = emailService.send(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response, HttpStatus.OK);
    }

    @PostMapping("/sendProduct")
    public ResponseEntity sendProduct(@RequestBody HashMap<String,String> data){
        Map<String , Object> response = null;
        try {
            response = emailService.sendProduct(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response, HttpStatus.OK);
    }
}
