package com.simpaisa.portal.utility;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApiUtility {

    public final static String EMAIL = "email";
    public final static String PASSWORD = "password";





    public final static String MESSAGE = "message";
    public final static String STATUS = "status";
    public final static Integer SUCCESS_STATUS = 1;
    public final static Integer ERROR_STATUS = 0;
    public final static String SUCCESS_MESSAGE = "Request Successful";

    public final static String TOKEN = "token";
    public final static String ROLE = "role";

    public final static String ROLE_ADMIN = "ADMIN";
    public final static String ROLE_MERCHANT = "MERCHANT";

//    DOCUMENT WORK
    public final static String ENDPOINT = "endPoint";
    public final static String BODY = "body";


    public static final String SUBJECT_VERIFICATION = "Verification Email";
    public static final String SUBJECT_WELCOME = "Welcome Email";

    public static final String TO = "muhiuddin.shahani@gmail.com";
    public static final String FROM="muhiuddin@simpaisa.com"; // domain email


    public static final String BASE_URL = "http://localhost:8074/";

    //Email Templates
    public static final String VERIFICATION_EMAIL_TEMPLATE = "verification-email-template.ftl";
    public static final String WELCOME_EMAIL_TEMPLATE = "welcome-email-template.ftl";


    public static String mapToJsonString(Map<String, String> parameters)
    {
        Gson gson = new Gson();
        String json = gson.toJson(parameters);
        return json;
    }



    public static Map postRequest(Map<String, String> headers, Map<String, String> parameters, String url)  {

        try {
            HttpResponse<JsonNode> result =
                    Unirest.post(url)
                            .headers(headers)
                            .body(mapToJsonString(parameters))
                            .asJson();


             return new Gson().fromJson(result.getBody().getObject().toString(), LinkedHashMap.class);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }
    public static Map getRequest(Map<String, String> headers, Map<String, String> parameters, String url)  {

        Map<String , Object > objMap = null;
        objMap.putAll(parameters);

        try {
            HttpResponse<JsonNode> response = Unirest.get(url).
                    headers(headers).
                    queryString(objMap).
                    asJson();

             return new Gson().fromJson(response.getBody().getObject().toString(), LinkedHashMap.class);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

   // public final static String PASSWORD = "password";
}
