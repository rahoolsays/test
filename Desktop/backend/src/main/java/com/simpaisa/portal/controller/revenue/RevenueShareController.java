package com.simpaisa.portal.controller.revenue;

import com.simpaisa.portal.entity.mongo.revenue.RevenueShare;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.service.revenue.RevenueShareService;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@RequestMapping("/revenueshare")
@CrossOrigin(origins = "*")
public class RevenueShareController {
    @Autowired
    RevenueShareService revenueShareService;

    @PostMapping(value = "/list", produces = "application/json")
    public Page<RevenueShare> findAll(@RequestParam("orderBy") String orderBy, @RequestParam("direction") String direction,
                                      @RequestParam("pageNo") int pageNo, @RequestParam("size") int size){

        Page<RevenueShare> revenueShares = revenueShareService.findAll(orderBy, direction, pageNo, size);
        return revenueShares;

    }

    @PostMapping(value = "/list/json", produces = "application/json")
    public Page<RevenueShare> findAllRequest(@RequestBody Map<String, Object> data){
        String orderBy = data.get(Utility.ORDER_BY).toString();
        String direction = data.get(Utility.DIRECTION).toString();
        int pageNo = Integer.valueOf(data.get(Utility.PAGE_NO).toString()) ;
        int size = Integer.valueOf(data.get(Utility.LIMIT).toString());
        Page<RevenueShare> revenueShares = revenueShareService.findAll(orderBy, direction, pageNo, size);
        return revenueShares;
    }
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody RevenueShare revenueShare){
        RevenueShare result = null;
        try{
            result = revenueShareService.save(revenueShare);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<RevenueShare>(result, HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<?> findById(@RequestParam("id") String id){
        RevenueShare result = null;
        try{
            result = revenueShareService.findById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<RevenueShare>(result, HttpStatus.OK);
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteById(@RequestParam("id") String id ){
        try{
            revenueShareService.deleteById(id);
            return new ResponseEntity<LinkedHashMap<String, Object>>(Utility.getResponse(Responses.SUCCESS), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<LinkedHashMap<String, Object>>(Utility.getResponse(Responses.SYSTEM_ERROR), HttpStatus.OK);
        }
    }
}
