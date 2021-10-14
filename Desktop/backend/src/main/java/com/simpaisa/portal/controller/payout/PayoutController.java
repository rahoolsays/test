package com.simpaisa.portal.controller.payout;

import com.simpaisa.portal.entity.mongo.payout.Payout;
import com.simpaisa.portal.entity.mysql.product.Product;
import com.simpaisa.portal.service.payout.PayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("payout")
@CrossOrigin(origins = "*")
public class PayoutController {

    @Autowired
    private PayoutService payoutService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Payout payout){
        Payout result = null;

        System.out.println(" payout = " + payout.toString());
        try{

            result = payoutService.save(payout);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<Payout>(result, HttpStatus.OK);
    }

    @PostMapping("/findByEmail")
    public ResponseEntity<?> findByEmail(@RequestBody HashMap<String, String> data){
        Payout payout = null;
        try{
            payout = payoutService.findByEmail(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(payout, HttpStatus.OK);
    }
}
