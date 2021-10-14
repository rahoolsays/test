package com.simpaisa.portal.utility;

import java.util.Map;

public class ApiRequest {

    private String endPoint;
    private Map<String, String> body;

//    getter setter

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }
}
