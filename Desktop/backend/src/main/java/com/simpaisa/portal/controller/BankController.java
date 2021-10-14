package com.simpaisa.portal.controller;

import com.simpaisa.portal.entity.mysql.bank.Bank;
import com.simpaisa.portal.service.bank.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank")
@CrossOrigin(origins = "*")
public class BankController {

    @Autowired
    BankService bankService;

    @PostMapping("all")
    public ResponseEntity getAll(){
        List<Bank> banks = null;
        try {
            banks = bankService.getAll();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(banks);
    }
}
