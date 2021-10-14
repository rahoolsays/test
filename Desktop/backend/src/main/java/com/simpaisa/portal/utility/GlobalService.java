package com.simpaisa.portal.utility;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GlobalService {


    public ResponseEntity getSuccessResponse(Object obj){

        ApiResponse res = new ApiResponse(1 , "Request successful" , obj);
        return ResponseEntity.ok()
                .header("statusCode", "1")
                .body(res);

    }

    public ResponseEntity getErrorResponse(String msg){

        ApiResponse res = new ApiResponse(0 , msg , "");
        return ResponseEntity.ok()
                .header("statusCode", "0")
                .body(res);


    }
}
