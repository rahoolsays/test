package com.simpaisa.portal.controller;

import com.simpaisa.portal.entity.mongo.revenue.RevenueShare;
import com.simpaisa.portal.entity.mysql.NumberPrefix;
import com.simpaisa.portal.entity.mysql.operator.Operator;
import com.simpaisa.portal.entity.mysql.operator.OperatorIdAndName;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.service.PrefixService;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/prefix")
@CrossOrigin(origins = "*")
public class PrefixController {

    @Autowired
    PrefixService prefixService;

    @GetMapping("/getListPrefix")
    public Page<NumberPrefix> getListPrefix(@RequestParam("orderBy") String orderBy, @RequestParam("direction") String direction,
                                            @RequestParam("pageNo") int pageNo, @RequestParam("size") int size){
        Page<NumberPrefix> numberPrefixes=null;

        try{
            numberPrefixes = prefixService.listPrefixes(orderBy, direction, pageNo, size);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return numberPrefixes;
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteById(@RequestParam("id") Long id){
        try{
            prefixService.deleteById(id);
            return new ResponseEntity<LinkedHashMap<String, Object>>(Utility.getResponse(Responses.SUCCESS), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<LinkedHashMap<String, Object>>(Utility.getResponse(Responses.SYSTEM_ERROR), HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody NumberPrefix prefix){
        NumberPrefix result = null;
        try{
            result = prefixService.save(prefix);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<NumberPrefix>(result, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> findById(@RequestParam("id") Long id){
        NumberPrefix result = null;
        try{
            result = prefixService.findById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<NumberPrefix>(result, HttpStatus.OK);
    }

}
