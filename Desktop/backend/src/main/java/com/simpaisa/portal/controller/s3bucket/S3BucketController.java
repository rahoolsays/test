package com.simpaisa.portal.controller.s3bucket;

import com.simpaisa.portal.service.s3bucket.S3BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/s3bucket")
@CrossOrigin(origins = "*")
public class S3BucketController {

    @Autowired
    S3BucketService s3BucketService;

    @PostMapping("/upload/{keyName}/logo")
    public ResponseEntity uploadLogo(@PathVariable("keyName")String keyName, @RequestParam("file") MultipartFile file){
        Map<String , Object> response = null;
        try {
            response = s3BucketService.uploadFile(keyName, file);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response, HttpStatus.OK);
    }

}
