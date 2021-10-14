package com.simpaisa.portal.controller.api;


import com.simpaisa.portal.entity.api.sidebar.DocumentSidebar;
import com.simpaisa.portal.repository.api.SidebarRepository;
import com.simpaisa.portal.service.apiservice.SidebarService;
import com.simpaisa.portal.utility.ApiResponse;
import com.simpaisa.portal.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sidebar")
public class SidebarController {
    @Autowired
    GlobalService service;

    @Autowired
    SidebarService sidebarService;

    @Autowired
    SidebarRepository repository;

    @GetMapping
    public ResponseEntity getAll(){
        try{
            return service.getSuccessResponse(sidebarService.getAll());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        try{

            return service.getSuccessResponse(repository.findById(id).get());

        }catch (Exception e){
            return service.getErrorResponse("Invalid request!");

        }

    }

    @PostMapping
    public ResponseEntity addMenuItem(@RequestBody DocumentSidebar item){
        try{

            ApiResponse resp = sidebarService.addMenu(item);
            if(resp.getStatusCode() == 1){
                return service.getSuccessResponse(resp.getData());
            }else{
                return service.getErrorResponse(resp.getMessage());

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    @PostMapping("/remove")
    public ResponseEntity deleteMenuItem(@RequestBody DocumentSidebar item){
       Boolean flag = sidebarService.removeMenuItem(item);

        if (flag == true) {
            return service.getSuccessResponse("Successfully deleted!");

        }else{
            return service.getErrorResponse("Operation failed! delete its child first");
        }
    }

}
