package com.simpaisa.portal.controller;

import com.simpaisa.portal.service.KeyFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;

import java.util.Map;

@RestController
@RequestMapping("keyfile")

@CrossOrigin(origins = "*")

public class KeyFileController {

    @Autowired
    KeyFileStorageService keyFileStorageService;

    @PostMapping(value = "upload" , consumes = { "multipart/form-data"})
    public ResponseEntity<?>  uploadKeyFile(@RequestParam("file") MultipartFile keyFile, @RequestParam("merchantId") Long merchantId){

        Map<String, Object> response = null;
        try {
             response =  keyFileStorageService.storeFile(keyFile, merchantId);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object> )response, HttpStatus.OK);

    }

    @PostMapping("read")
    public String readPublicKey(@RequestParam("merchantId") Long merchantId){

        String response = "";

        try {
            response = keyFileStorageService.readPublicKey(merchantId);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }

    @PostMapping("getSimpaisaKey")
    public String readSimpaisaKey(){

        String response = "";

        try{
            response = keyFileStorageService.readSimpaisaFile();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return  response;
    }

    @GetMapping("download")
    public ResponseEntity<Resource> downloadSimpaisaKey(HttpServletRequest request){

        Resource resource = keyFileStorageService.downloadSimpaisaKey();

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }
}
