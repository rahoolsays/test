package com.simpaisa.portal.controller;

import com.simpaisa.portal.entity.mysql.merchant.Merchant;
import com.simpaisa.portal.entity.mysql.merchant.MerchantIdAndName;
import com.simpaisa.portal.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/merchant")
@CrossOrigin(origins = "*")
public class MerchantController {


    @Autowired
    MerchantService merchantService;

    @PostMapping("/findByEmail")
    public ResponseEntity findByEmail(@RequestBody HashMap<String, String> data){

        Map<String , Object> response = null;
        System.out.println(" data = " + data);
        try{
            response = merchantService.findByEmail(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response , HttpStatus.OK);
    }


    @GetMapping("/getByMerchantId")
    public Merchant getByMerchantId(@RequestParam("merchantId") int merchantId){

        Merchant response = null;
        try{
            response = merchantService.getByMerchantId(merchantId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }



    @GetMapping("/getAllMerchants")
    public ResponseEntity getAllMerchants(){
        List<MerchantIdAndName> merchantIdAndNames = null;

        try{
            merchantIdAndNames = merchantService.getAllMerchants();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<MerchantIdAndName>>(merchantIdAndNames, HttpStatus.OK);
    }

    @PostMapping("/updatePostback")
    public ResponseEntity updatePostback(@RequestBody HashMap<String, String> data){

        Map<String , Object> response = null;
        System.out.println(" data = " + data);
        try{
            response = merchantService.updatePostback(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response , HttpStatus.OK);
    }


    @PostMapping("/updateLogo")
    public ResponseEntity updateLogo(@RequestBody HashMap<String, String> data){

        Map<String , Object> response = null;
        System.out.println(" data = " + data);
        try{
            response = merchantService.updateLogo(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response , HttpStatus.OK);
    }



    @PostMapping("/create/live")
    public ResponseEntity goLive(@RequestBody HashMap<String, String> data){
        Map<String , Object> response = null;
        try{

            response = merchantService.goLive(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response , HttpStatus.OK);


    }
}
