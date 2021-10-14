package com.simpaisa.portal.controller.api;

import com.simpaisa.portal.entity.api.description.ApiDescription;
import com.simpaisa.portal.entity.api.description.Heading;
import com.simpaisa.portal.service.apiservice.DescriptionService;
import com.simpaisa.portal.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/api-description")
public class DescriptionController {

    @Autowired
    GlobalService service;

    @Autowired
    DescriptionService descriptionService;

    @GetMapping
    public ResponseEntity getAll(){
        List<ApiDescription> list = new ArrayList<>();
        try{
            list = (descriptionService.getAll());

        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getSuccessResponse(list);
    }
    @PostMapping
    public ResponseEntity saveAndUpdateDescription(@RequestBody ApiDescription desc){
        ApiDescription description = null;

        if(desc.getTitle() == null || desc.getTitle().isEmpty()){
            return service.getErrorResponse("Invalid title!");
        }
        if(desc.getAction() == null || desc.getAction().isEmpty()){
            return service.getErrorResponse("Invalid action!");
        }

        for (Heading heading : desc.getHeadings()) {
            if(heading.getTitle() == null || heading.getTitle().isEmpty()){
            return service.getErrorResponse("Invalid title!");
            }

        }

        try{
            description = descriptionService.saveDescription(desc);

        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getSuccessResponse(description);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id){
        ApiDescription description = null;
        try{
            description = descriptionService.getById(id);
            if(description != null){
                return service.getSuccessResponse(description);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Invalid request!");
    }


    @GetMapping("/remove/{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        Boolean description = false;
        try{
            description = descriptionService.deleteDescription(id);
            if(description){
                return service.getSuccessResponse("Removed successfully!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Invalid request!");
    }




}
