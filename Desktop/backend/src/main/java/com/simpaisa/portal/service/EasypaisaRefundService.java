package com.simpaisa.portal.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;

@Service
public class EasypaisaRefundService {

    private final static  Logger logger = LoggerFactory.getLogger(EasypaisaRefundService.class);
	private static final String BASE_URI = "https://easypaystg.easypaisa.com.pk/easypay-service/rest/ReversalBusinessRestService/v1";

    private static final String CREDENTIALS = "UHVibGlzaEV4U29sdXRpb25zUHZ0THRkOjY5NWQ0NTBlMTgwYjgyMTc5YWY1MmYyMzM5M2VjYTI2";
    private static final String STORE_ID = "10252";
	
	
    public HashMap<String, String> refundAmount(String orderId, String amount, String orderDate, String transactionId,String cnic) {
        try {
            String response = postRequest(reversalRequestWithSignature(orderId, amount, orderDate, transactionId,cnic), BASE_URI + "/reversalBusiness"); // appending 0 to mobile number as easypiasa requirement

            if(response != null) {
                // convert JSON string to Map
                HashMap responseMap = new ObjectMapper().readValue(response, new TypeReference<HashMap>(){});
                return  (HashMap<String, String>) responseMap.get("response");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
    
	public String reversalRequestWithSignature (String orderId, String amount, String orderDate, String transactionId,String cnic) {
        StringBuilder otpRequest = new StringBuilder("{\"request\": ");
        try {

            otpRequest.append(fullReversalRequest(orderId, amount,orderDate,transactionId,cnic))
                    .append(",\"signature\": \"")
                    .append(generateSignature(fullReversalRequest(orderId, amount,orderDate,transactionId,cnic)))
                    .append("\"}");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return otpRequest.toString();
    }
	
	public String fullReversalRequest(String orderId, String amount, String orderDate, String transactionId,String cnic){
        StringBuilder paymentRequest = new StringBuilder("{\"externalSystemId\":\"");
        try {
            paymentRequest.append(transactionId)
                    .append("\",")
                    .append("\"storeId\":\"")
                    .append(STORE_ID)
                    .append("\",")
                    .append("\"reversalAmount\":\"")
                    .append(amount)
                    .append("\",")

                    .append("\"orderId\":\"")
                    .append(orderId)
                    .append("\",")
                    .append("\"orderDate\":\"")
                    .append(orderDate)
                    /*.append("\",")
                    .append("\"CNIC\":\"")
                    .append(cnic)*/
                    .append("\"}");
        }catch (Exception ex){
            ex.printStackTrace();
        }

       // System.out.println("reversal Request = " + paymentRequest.toString());
        return paymentRequest.toString();
    }
	
	
	public String generateSignature(String request) {

        String requestSignature = null;

        try {

            PrivateKey privateKey = getPemPrivateKey();
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(request.getBytes());
            byte[] signed = signature.sign();

            requestSignature = (Base64.encodeBase64String (signed));

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        //    System.out.println("Signature = " + requestSignature);
        return requestSignature;
    }
	
	private PrivateKey getPemPrivateKey() throws Exception {


        String temp = null;
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray((new ClassPathResource("private_key_pkcs8.pem")).getInputStream());
            temp = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("\n", "")
                .replace("-----END PRIVATE KEY-----", "");

        PKCS8EncodedKeySpec specPriv = new PKCS8EncodedKeySpec(Base64.decodeBase64(privKeyPEM.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(specPriv);
    }
	
	/*
    *
    *  Method to post request
    *
    *  */
   private String postRequest(String request, String url) throws IOException {
       HttpURLConnection postConnection = null;
       try {

           logger.info("EASYPAISA REQUEST :: " + request);

           URL obj = new URL(url);

           postConnection = (HttpURLConnection) obj.openConnection();
           postConnection.setRequestMethod("POST");
           postConnection.setRequestProperty("Content-Type", "application/json");
           postConnection.setRequestProperty("Credentials", CREDENTIALS);
           postConnection.setDoOutput(true);

           OutputStream os = postConnection.getOutputStream();
           os.write(request.getBytes());
           os.flush();
           os.close();

           int responseCode = postConnection.getResponseCode();

           String  responseMessage = "";
           if(postConnection!=null){
               if(postConnection.getResponseMessage()!=null){
                   responseMessage = postConnection.getResponseMessage();
               }
           }

           try ( BufferedReader in = new BufferedReader(
                   new InputStreamReader(postConnection.getInputStream()))) { //success

               String inputLine;
               StringBuffer response = new StringBuffer();
               while ((inputLine = in .readLine()) != null) {
                   response.append(inputLine);
               }
               in .close();
               logger.info("EASYPAISA RESPONSE :: " + response.toString() + " HttpResponseMessage:: " + responseMessage + " HttpResponseCode :: " + responseCode);

               return response.toString();
           } catch (Exception ex){

               logger.info("EASYPAISA RESPONSE :: Exception Occured " + " HttpResponseMessage:: " + responseMessage + " HttpResponseCode :: " + responseCode);
           }

       } catch (Exception exception) {
           exception.printStackTrace();
       }finally{
           if(postConnection!=null){
               postConnection.disconnect();
           }
       }



       return null;
   }

}
