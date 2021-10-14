package com.simpaisa.portal.controller;

import com.simpaisa.portal.entity.mongo.kyc.KYC;
import com.simpaisa.portal.service.KycService;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kyc")
@CrossOrigin(origins = "*")
public class KycController {

    @Autowired
    private KycService kycService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody KYC kyc){

        KYC kycResult = null;
        try{
            kycResult = kycService.save(kyc);
            if(kycResult!=null){
                if(kycResult.getId()!=null){
                    kyc.setId(kycResult.getId());
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(kyc, HttpStatus.OK);
    }

    @PostMapping("/findByEmail")
    public ResponseEntity<?> findByEmail(@RequestBody HashMap<String, String> data){
        KYC kyc = null;
        try{
            kyc = kycService.findByEmail(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(kyc, HttpStatus.OK);
    }

    @PostMapping(value = "/documentUpload", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadDocuments(@RequestParam("document") List<MultipartFile> multipartFiles, @RequestParam("merchantId") Long merchantId, @RequestParam("legalStructure") String legalStructure){
        Map<String, Object> response = null;
        try{
            response = kycService.uploadDocuments(multipartFiles, merchantId, legalStructure);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


    @PostMapping(value = "/getDocuments")
    public ResponseEntity<?> getDocuments( @RequestParam("merchantId") Long merchantId, @RequestParam("legalStructure") String legalStructure){
        ResponseEntity<?> response = null;
        try{
            response = kycService.getDocuments(merchantId, legalStructure);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    @PostMapping(value = "list", produces = "application/json")
    public Page<KYC> findAllInReview(@RequestParam("step") int step, @RequestParam("orderBy") String orderBy, @RequestParam("direction") String direction,
                                     @RequestParam("pageNo") int pageNo, @RequestParam("size") int size){


        Page<KYC> list = kycService.findAllInReview(step, orderBy, direction, pageNo, size);
        return list;
    }

    @GetMapping("")
    public ResponseEntity<?> findById(@RequestParam("id") String  id){
        KYC kyc = null;
        try{
            kyc = kycService.findById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<KYC>(kyc, HttpStatus.OK);
    }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, @RequestParam("merchantId") Long merchantId, @RequestParam("legalStructure") String legalStructure, HttpServletRequest request) {
        // Load file as Resource
        return kycService.downloadFile(fileName, merchantId, legalStructure, request);
    }







}
