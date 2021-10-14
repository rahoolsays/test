package com.simpaisa.portal.service.apiservice;

import com.simpaisa.portal.entity.api.request.*;
import com.simpaisa.portal.enums.InputType;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.repository.api.*;
import com.simpaisa.portal.utility.ApiRequest;
import com.simpaisa.portal.utility.ApiResponse;
import com.simpaisa.portal.utility.ApiUtility;
import com.simpaisa.portal.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApiService {


    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private GlobalService service;

    @Autowired
    private ApiBodyRepository apiBodyRepo;
    @Autowired
    private ApiHeaderRepository apiHeaderRepo;
    @Autowired
    private ApiParameterRepo apiParameterRepo;

    @Autowired
    private ApiParameterListRepo parameterListRepo;



    public List<ApiData> getAll(){
        List<ApiData> list = new ArrayList<>();

        list = apiRepository.findAll();
        return list;
    }

    public Map<String, Object> returnResponse(ApiRequest data){

        try {
            if (!data.getEndPoint().isEmpty() && data.getBody() !=  null) {
                HashMap<String, String> headers = new HashMap<String, String> ();
                headers.put("Accept","Application/json");
                headers.put("Content-Type","Application/json");
                Map<String, Object> response =   ApiUtility.postRequest(headers , data.getBody() , data.getEndPoint());
                return  response;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public Map<String, Object> returnResponseGet(ApiRequest data){

        try {
            if (!data.getEndPoint().isEmpty() && data.getBody() !=  null) {
                HashMap<String, String> headers = new HashMap<String, String> ();
                headers.put("Accept","Application/json");
                headers.put("Content-Type","Application/json");
                Map<String, Object> response =   ApiUtility.getRequest(headers , data.getBody() , data.getEndPoint());
                return  response;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public ApiData getById(String id){

        ApiData dbData = apiRepository.findById(id).get();
        return dbData;


    }

    public Boolean deleteById(String id){
        try {
            apiRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public Map<String , Object> getByKey(String key){
        ApiData dbData = apiRepository.findByTitle(key);
        if(dbData != null){
            return getResponse(Responses.SUCCESS , dbData);
        }else{
            return getResponse(Responses.ERROR , "No record found!");
        }
    }

    public ApiResponse editData(ApiData data) {

        if(data.getId() == null){
            return returnResponse(0 , "Invalid Request!");
        }

        if(data.getBody().size() == 0){
            return  returnResponse(0 , "Body required!");
        }

        ApiData dbData = apiRepository.findById(data.getId()).get();
        if(data.getTitle() != null){
            dbData.setTitle(data.getTitle());
        }
        if(data.getMainHeading() != null){
            dbData.setMainHeading(data.getMainHeading());
        }
        if(data.getAction() != null){
            dbData.setAction(data.getAction());
        }
        if(data.getEndPoint() != null){
            dbData.setAction(data.getEndPoint());
        }
        if(data.getEndUrl() != null){
            dbData.setEndUrl(data.getEndUrl());
        }
        if(data.getMainDescription() != null){
            dbData.setMainDescription(data.getMainDescription());
        }
        if(data.getSubHeading() != null){
            dbData.setSubHeading(data.getSubHeading());
        }
        if(data.getSubDescription() != null){
            dbData.setSubDescription(data.getSubDescription());
        }
        Set<ApiBody> bodySet = new HashSet<>();
        Set<ApiHeaders> headerSet = new HashSet<>();
        for (ApiBody apiBody : data.getBody()) {
            if(apiBody.getKey() == null || apiBody.getValue() == null || apiBody.getType() == null){
                return  returnResponse(0 , "Invalid Body");
            }else{
                Set<ApiParameters>  dropDownList = new HashSet<>();
                if (apiBody.getInputType() != null && apiBody.getInputType().equals(InputType.Dropdown)) {
                    if(apiBody.getParameter().size() == 0){
                        returnResponse(0 , "Invalid body");
                    }

                    for (ApiParameters parameters : apiBody.getParameter()) {
                        if(parameters.getValue() == null){
                            return  returnResponse(0 , "Invalid Body");
                        }
                        if(parameters.getWithMerchantId() == null ||  parameters.getWithMerchantId().isEmpty()){
                            parameters.setWithMerchantId(null);
                        }

                        Set<ApiParameterList>  paramList = new HashSet<>();
                        for (ApiParameterList parameterList : parameters.getParameterList()) {
                            if(parameterList.getKey() == null || parameterList.getValue() == null || parameterList.getType() == null){
                                return  returnResponse(0 , "Invalid Parameter");
                            }
                            if(parameterList.getIsDefault() == null || parameterList.getIsDefault() == false){
                                parameterList.setIsDefault(false);
                            }
                            paramList.add(parameterListRepo.save(parameterList));
                        }
                        parameters.setParameterList(paramList);
                        dropDownList.add(apiParameterRepo.save(parameters));
                        apiBody.setParameter(dropDownList);
                    }
                }
                bodySet.add(apiBodyRepo.save(apiBody));
            }
        }
        for (ApiHeaders apiHeaders : data.getHeader()) {
            if(apiHeaders.getKey() == null || apiHeaders.getValue() == null || apiHeaders.getType() == null){
                return  returnResponse(0 , "Invalid Header");
            }else{
                headerSet.add(apiHeaderRepo.save(apiHeaders));

            }
        }
        dbData.setBody(bodySet);
        dbData.setHeader(headerSet);
        if(data.getMethod() != null){
        dbData.setMethod(data.getMethod());

        }else{
        dbData.setMethod("POST");
        }
        apiRepository.save(dbData);
        return returnResponse(1 , "Request Successful");
    }



    public ApiResponse adData(ApiData data) {


        if(data.getBody().size() == 0){
            return  returnResponse(0 , "Body required!");
        }
        if(data.getTitle() == null){
            return  returnResponse(0 , "Invalid Title");
        }
        if(data.getEndUrl() == null){
            return  returnResponse(0 , "Invalid url");
        }
        ApiData api = apiRepository.findByTitle(data.getTitle());

        if(api != null){
            return  returnResponse(0 , "Title already exist!");
        }

        Set<ApiBody> bodySet = new HashSet<>();
        Set<ApiHeaders> headerSet = new HashSet<>();
        for (ApiBody apiBody : data.getBody()) {
            if(apiBody.getKey() == null || apiBody.getValue() == null || apiBody.getType() == null){
                return  returnResponse(0 , "Invalid Body");
            }else{
                Set<ApiParameters>  dropDownList = new HashSet<>();
                if (apiBody.getInputType() != null && apiBody.getInputType().equals(InputType.Dropdown)) {
                    if(apiBody.getParameter().size() == 0){
                        returnResponse(0 , "Invalid body");
                    }
                    for (ApiParameters parameters : apiBody.getParameter()) {
                        if(parameters.getValue() == null){
                            return  returnResponse(0 , "Invalid Body");
                        }
                        if(parameters.getWithMerchantId() == null ||  parameters.getWithMerchantId().isEmpty()){
                            parameters.setWithMerchantId(null);
                            }

                        Set<ApiParameterList>  paramList = new HashSet<>();
                        for (ApiParameterList parameterList : parameters.getParameterList()) {
                            if(parameterList.getKey() == null || parameterList.getValue() == null || parameterList.getType() == null){
                                return  returnResponse(0 , "Invalid Parameter");
                            }
                            if(parameterList.getIsDefault() == null || parameterList.getIsDefault() == false){
                                parameterList.setIsDefault(false);
                            }
                            paramList.add(parameterListRepo.save(parameterList));
                        }
                        parameters.setParameterList(paramList);
                        dropDownList.add(apiParameterRepo.save(parameters));
                        apiBody.setParameter(dropDownList);
                    }
                }
                bodySet.add(apiBodyRepo.save(apiBody));
            }
        }
        for (ApiHeaders apiHeaders : data.getHeader()) {
            if(apiHeaders.getKey() == null || apiHeaders.getValue() == null || apiHeaders.getType() == null){
                return  returnResponse(0 , "Invalid Header");
            }else{
                headerSet.add(apiHeaderRepo.save(apiHeaders));

            }
        }
        data.setBody(bodySet);
        data.setHeader(headerSet);
        data.setMethod("POST");
        apiRepository.save(data);
       return returnResponse(1 , "Request Successful");
    }



    public ApiResponse returnResponse(Integer status , String message){
        ApiResponse res = new ApiResponse(status , message , "");
        return res;
    }

    private Map<String, Object> getResponse(Responses responses , Object data){
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(ApiUtility.STATUS, responses.getStatus());
        response.put(ApiUtility.MESSAGE, responses.getMessage());
        response.put("data", data);
        return response;
    }



}
