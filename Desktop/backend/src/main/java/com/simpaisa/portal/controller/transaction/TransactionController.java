package com.simpaisa.portal.controller.transaction;

import com.simpaisa.portal.entity.mysql.customer.CustomerList;
import com.simpaisa.portal.entity.mysql.transaction.Transaction;
import com.simpaisa.portal.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("transaction")

@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @PostMapping(value = "/list",  produces = "application/json")
    public Page<Transaction> getCustomerList(@RequestParam("merchantId") Long merchantId, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end, @RequestParam("status") String status, @RequestParam("orderBy") String orderBy, @RequestParam("direction") String direction,
                                             @RequestParam("pageNo") int pageNo, @RequestParam("size") int size){


        Page<Transaction> list = transactionService.getTransaction( merchantId, start, end, status, orderBy, direction, pageNo, size);
        return list;

    }

    @PostMapping(value = "/findById", produces = "application/json")
    public ResponseEntity<?> findById(@RequestParam("id") long id){
        Transaction transaction  = null;
        try{
            transaction = transactionService.findById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
    }
}
