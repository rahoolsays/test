package com.simpaisa.portal.controller.api;

import com.simpaisa.portal.entity.api.request.ApiData;
import com.simpaisa.portal.service.apiservice.ApiService;
import com.simpaisa.portal.utility.ApiRequest;
import com.simpaisa.portal.utility.ApiResponse;
import com.simpaisa.portal.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/api-data")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private GlobalService service;

    @GetMapping
    public ResponseEntity getAllApis(){
        try{
            return service.getSuccessResponse(apiService.getAll());
        }catch (Exception e){
            e.printStackTrace();
        }
      return null;
    }

  @GetMapping("/key/{key}")
    public ResponseEntity getByApiKey(@PathVariable String key){

        Map<String , Object> response = null;
        try{
            response = apiService.getByKey(key);
        }catch (Exception e){
            e.printStackTrace();
        }

      return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response, HttpStatus.OK);
  }



    @GetMapping("/header/{id}")
    public ResponseEntity deleteHeader(@PathVariable String id){
        try{
            return service.getSuccessResponse(apiService.getAll());
        }catch (Exception e){
            e.printStackTrace();
        }

      return null;
    }


 @GetMapping("/remove/{id}")
    public ResponseEntity deleteApi(@PathVariable String id){ boolean isDelete = apiService.deleteById(id);
     if (isDelete == true) {
        return service.getSuccessResponse("Delete successfully!");
     }else{
        return service.getSuccessResponse("Operation failed!");

     }
    }

    @GetMapping("/body/{id}")
    public ResponseEntity deleteBody(@PathVariable String id){
        try{
            return service.getSuccessResponse(apiService.getAll());
        }catch (Exception e){
            e.printStackTrace();
        }

      return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id){
        try{
            if(id == null){
                return service.getErrorResponse("Invalid Request!");
            }else{
                return service.getSuccessResponse(apiService.getById(id));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

      return null;

    }

    @PostMapping
    public ResponseEntity adApiData(@RequestBody ApiData data){

        try{
            ApiResponse resp =  apiService.adData(data);
            if(resp.getStatusCode() == 1){
            return service.getSuccessResponse(resp.getMessage());
            }else{
            return service.getErrorResponse(resp.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

      return null;

    }



    @PostMapping("/getResponse")
    public ResponseEntity getResponseOfApiPOST(@RequestBody ApiRequest data){

        try{

            Map<String, Object> response = null;
            response = apiService.returnResponse(data);
            return ResponseEntity.ok(response);

        }catch (Exception e){
            e.printStackTrace();
        }
      return null;
    }
    @PostMapping("/getRequest")
    public ResponseEntity getResponseOfApiGET(@RequestBody ApiRequest data){

        try{
            if(data.getBody() != null){

            Map<String, Object> response = null;
            response = apiService.returnResponseGet(data);
            return ResponseEntity.ok(response);
            }else{
                return service.getErrorResponse("Invalid body");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

   @PostMapping("/update")
    public ResponseEntity updateApiData(@RequestBody ApiData data){

        try{
            ApiResponse resp =  apiService.editData(data);
            if(resp.getStatusCode() == 1){
            return service.getSuccessResponse(resp.getMessage());
            }else{
            return service.getErrorResponse(resp.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

      return null;

    }

}
