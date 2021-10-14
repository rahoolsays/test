package com.simpaisa.portal.service.apiservice;

import com.simpaisa.portal.entity.api.description.ApiDescription;
import com.simpaisa.portal.repository.api.DescriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DescriptionService {

    @Autowired
    DescriptionRepo descriptionRepo;

    public ApiDescription saveDescription(ApiDescription description){
        ApiDescription desc = null;
        try{

            desc = descriptionRepo.save(description);
        }catch (Exception e){
            e.printStackTrace();
        }
        return desc;
    }

    public ApiDescription getById(String id){
        ApiDescription desc = null;
        try{
            desc = descriptionRepo.findById(id).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return desc;
    }
    public Boolean deleteDescription(String id){
        Boolean flag = false;
        try{
             descriptionRepo.deleteById(id);
            flag = true;
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
    public List<ApiDescription> getAll(){
       List<ApiDescription>  desc = new ArrayList<>();
        try{
            desc = descriptionRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return desc;
    }

}
