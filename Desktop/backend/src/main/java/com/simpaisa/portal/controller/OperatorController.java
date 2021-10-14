package com.simpaisa.portal.controller;


import com.simpaisa.portal.entity.mysql.merchant.MerchantIdAndName;
import com.simpaisa.portal.entity.mysql.operator.Operator;
import com.simpaisa.portal.entity.mysql.operator.OperatorIdAndName;
import com.simpaisa.portal.entity.mysql.productconfiguration.ProductConfiguration;
import com.simpaisa.portal.service.OperatorService;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/operator")
@CrossOrigin(origins = "*")
public class OperatorController {

    @Autowired
    OperatorService operatorService;

    @GetMapping("/getAllOperators")
    public ResponseEntity getAllOperators(@RequestParam("productId") int ProductId){
        List<OperatorIdAndName> operatorIdAndNames = null;
        List<OperatorIdAndName> operatorIdAndNames2 = null;

        try{
            operatorIdAndNames = operatorService.getAllOperators();
            operatorIdAndNames2 = operatorService.getAllOperatorsNotConfigured(ProductId);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<OperatorIdAndName>>(operatorIdAndNames2, HttpStatus.OK);
    }

    @GetMapping("/getListOperators")
    public Page<Operator> getListOperators(@RequestParam("orderBy") String orderBy, @RequestParam("direction") String direction,
                                           @RequestParam("pageNo") int pageNo, @RequestParam("size") int size){
        Page<Operator> operators=null;

        try{
            operators = operatorService.listOperators(orderBy, direction, pageNo, size);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return operators;
    }

    @GetMapping("/getAllOperator")
    public ResponseEntity getAllOperator(){
        List<OperatorIdAndName> operatorIdAndNames = null;
//        List<OperatorIdAndName> operatorIdAndNames2 = null;

        try{
            operatorIdAndNames = operatorService.getAllOperators();
//            operatorIdAndNames2 = operatorService.getAllOperatorsNotConfigured(ProductId);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<OperatorIdAndName>>(operatorIdAndNames, HttpStatus.OK);
    }

    @GetMapping("/getOperatorByOperatorId")
    public ResponseEntity getOperatorByOperatorId(@RequestParam("OperatorId") int OperatorId){
        OperatorIdAndName operatorIdAndNames = null;
        List<OperatorIdAndName> operatorIdAndNames2 = null;

        try{
            operatorIdAndNames = operatorService.findByOperatorId(OperatorId);
//            operatorIdAndNames2 = operatorService.getAllOperatorsNotConfigured(ProductId);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<OperatorIdAndName>(operatorIdAndNames, HttpStatus.OK);
    }


    @GetMapping("/getOperator")
    public Operator getOperator(@RequestParam("OperatorId") int OperatorId){
        Operator operator = null;
        Operator operator2 = null;

        try{
            operator = operatorService.findOperatorId(OperatorId);
//            operatorIdAndNames2 = operatorService.getAllOperatorsNotConfigured(ProductId);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return operator;
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Operator operator){
        Operator result = operator;

        try{

            operatorService.save(operator);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<Operator>(result, HttpStatus.OK);
    }
}

