package com.simpaisa.portal.service.apiservice;

import com.simpaisa.portal.entity.api.sidebar.DocumentSidebar;
import com.simpaisa.portal.repository.api.SidebarRepository;
import com.simpaisa.portal.utility.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SidebarService {

    @Autowired
    SidebarRepository repository;

    @Autowired
    ApiService apiService;


    public List<DocumentSidebar> getAll(){
        return repository.findAll();
    }


    public ApiResponse addMenu(DocumentSidebar menu){
        if(menu.getText() != null && menu.getText().isEmpty()){
            return returnResponse( 0 , "Enter text!" , "");
        }

        if(menu.getChildMost() != null && menu.getChildMost()){
            if(menu.getAction() != null && !menu.getAction().isEmpty()){
                menu.setAction(menu.getAction());
                menu.setChildMost(true);
            }else{
                return returnResponse( 0 , "Enter Action!" , "");
            }
        }else{
            menu.setChildMost(false);
        }
        repository.save(menu);
       return returnResponse(1 , "Added successfully!" , "");

    }




    public Boolean removeMenuItem(DocumentSidebar item){
        if (item.getChildMost() == null || !item.getChildMost()) {
                return false;
        }
        try {
            repository.deleteById(item.getId());
            return true;

        }catch (Exception e){
            return false;
        }
    }
    public ApiResponse returnResponse(Integer status , String message ,Object data){
        ApiResponse res = new ApiResponse(status , message , data);
        return res;
    }

}
