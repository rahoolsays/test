package com.simpaisa.portal.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpaisa.portal.utility.JazzcashResponse;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class JazzCashRestService {
    private final static Logger logger = Logger.getLogger(JazzCashRestService.class);


//	private static final String MERCHANT_ID = "Simpaisa01";
//	private static final String SUB_MERCHANT_ID = "";
//	private static final String MERCHANT_PASSWORD = "v6vdhyexx2";
//	private static final String MERCHANT_HASHKEY = "v07xu92f4s";
//	private static final String CURRENCY = "PKR";
//	private static final String POSTBACK_URI = "https://api.simpaisa.com:8443/";
//	private static final String TRANSACTION_TYPE = "MWALLET";
//	private static final String LANGUAGE = "EN";
//	private static final String API_VERSION = "1.1";
//	private static final String DESCRIPTION = "Thankyou for using Jazz Cash";
//	private static final String REFERENCE = "OrderID";


//    private static final String MERCHANT_ID = "MC10780";
//    private static final String SUB_MERCHANT_ID = "";
//    private static final String MERCHANT_PASSWORD = "41y9zv6x13";
//    private static final String MERCHANT_HASHKEY = "99vs53e06s";
//    private static final String CURRENCY = "PKR";
//    private static final String POSTBACK_URI = "https://api.simpaisa.com:8443/";
//    private static final String TRANSACTION_TYPE = "MWALLET";
//    private static final String LANGUAGE = "EN";
//    private static final String API_VERSION = "1.1";
//    private static final String DESCRIPTION = "Thankyou for using Jazz Cash";
//    private static final String REFERENCE = "billRef";
    //private static final String CNIC = "345678";

    private static final String MERCHANT_ID = "00169117";
    private static final String SUB_MERCHANT_ID = "";
    private static final String MERCHANT_PASSWORD = "7y456502uw";
    private static final String MERCHANT_HASHKEY = "134y9s1x5y";


    //	 private static final String MERCHANT_ID = "00127373";
//	 private static final String SUB_MERCHANT_ID = "";
//	 private static final String MERCHANT_PASSWORD = "u4424sxyuy";
//	 private static final String MERCHANT_HASHKEY = "219vwhhse7";
//	 private static final String POSTBACK_URI = "http://api.simpaisa.com:9991/jazzcash/transaction/postback";
//	 private static final String BASE_URL = "https://payments.jazzcash.com.pk/PayAxisExternalStatusService/StatusService_v11.svc";


    //    private static final String MERCHANT_ID = "MC21842";
//    private static final String SUB_MERCHANT_ID = "";
//    private static final String MERCHANT_PASSWORD = "u03yusby8u";
//    private static final String MERCHANT_HASHKEY = "4a8894h000";
    private static final String CURRENCY = "PKR";
    private static final String POSTBACK_URI = "https://api.simpaisa.com:8443/";
    private static final String TRANSACTION_TYPE = "MWALLET";
    private static final String LANGUAGE = "EN";
    private static final String MerchantMPIN = "1013";
    private static final String API_VERSION = "1.1";
    private static final String DESCRIPTION = "Thankyou for using Jazz Cash";
    private static final String REFERENCE = "billRef";




    // private static final String BASE_URL = "https://tokfe2.jazzcash.com.pk/PayAxisExternalStatusService/StatusService_v11.svc?wsdl";

    private static final String REST_BASE_URL = "https://payments.jazzcash.com.pk/ApplicationAPI/API/2.0/Purchase/DoMWalletTransaction";

    private static final String REST_BASE_URL_REFUND =   "https://payments.jazzcash.com.pk/ApplicationAPI/API/Purchase/domwalletrefundtransaction";

    private static final String INQUIRE_URL =   "https://sandbox.jazzcash.com.pk/ApplicationAPI/API/PaymentInquiry/Inquire";



    public JazzcashResponse makePayment(String mobileNo, String amount, String CNIC, String reference){

        JazzcashResponse jazzcashResponse = null;
        HashMap<String, String> responseHashMap = null;
        try {
            String transTime =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String transEndTime = new SimpleDateFormat("yyyyMMddHHmmss").format(getExpiryDate(2));
            String transReference = "T"+transTime;

            String hashInput = getParamsString(amount, "0" +mobileNo, transTime, transEndTime, transReference, reference, DESCRIPTION,CNIC);

            String hash =  getHash(hashInput);



            String paymentRequest = getPaymentRequest(transReference, amount, transTime, transEndTime, reference,
                    DESCRIPTION, "0"+mobileNo,  hash,CNIC);

            String response =  postRequest(paymentRequest, REST_BASE_URL);
            System.out.println("Response = " + response);
            if(response != null) {
                jazzcashResponse = new JazzcashResponse();
                HashMap responseMap = new ObjectMapper().readValue(response, new TypeReference<HashMap>() {});
                responseHashMap =    (HashMap<String, String>) responseMap;
                jazzcashResponse.setResponse( responseHashMap.get("pp_ResponseMessage"));
                jazzcashResponse.setResponseCode(responseHashMap.get("pp_ResponseCode"));
                jazzcashResponse.setTid(responseHashMap.get("pp_TxnRefNo"));
                //  jazzcashResponse.response(responseHashMap.get(""));
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return jazzcashResponse;

    }


    public JazzcashResponse refundApi(String referenceNumber, String amount){

        JazzcashResponse jazzcashResponse = null;
        HashMap<String, String> responseHashMap = null;
        try {

            String hashInput = getRefundString(referenceNumber, amount);

            String hash =  getHash(hashInput);



            String paymentRequest =refundPayment(referenceNumber, amount, hash);

            String response =  postRequest(paymentRequest, REST_BASE_URL_REFUND);
            System.out.println("Response = " + response);
            if(response != null) {
                jazzcashResponse = new JazzcashResponse();
                HashMap responseMap = new ObjectMapper().readValue(response, new TypeReference<HashMap>() {});
                responseHashMap =    (HashMap<String, String>) responseMap;
                jazzcashResponse.setResponse( responseHashMap.get("pp_ResponseMessage"));
                jazzcashResponse.setResponseCode(responseHashMap.get("pp_ResponseCode"));
                jazzcashResponse.setTid(responseHashMap.get("pp_TxnRefNo"));
                //  jazzcashResponse.response(responseHashMap.get(""));
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return jazzcashResponse;

    }


    public HashMap<String, Object> jazzcashInquire(String transReference){

        JazzcashResponse jazzcashResponse = null;
        HashMap<String, Object> responseHashMap = null;
        try {

            String hashInput = getParamsInquire(transReference);

            String hash =  getHash(hashInput);




//                    "{\n" +
//                    "\n" +
//                    "    \"pp_TxnRefNo\": \""+transReference+"\",\n" +
//                    "\n" +
//                    "    \"pp_MerchantID\": \""+MERCHANT_ID+"\",\n" +
//                    "\n" +
//                    "    \"pp_Password\": \""+MERCHANT_PASSWORD+"\",\n" +
//                    "\n" +
//                    "    \"pp_SecureHash\": \""+hash+"\"\n" +
//                    "\n" +
//                    "}";
//                    getPaymentRequest(transReference, amount, transTime, transEndTime, reference,
//                    DESCRIPTION, "0"+mobileNo,  hash,CNIC);

            String paymentRequest =getInquireRequest(transReference, hash);
            System.out.println("Request = " + paymentRequest);
            String response =  postRequest(paymentRequest, INQUIRE_URL);
            System.out.println("Response = " + response);
            if(response != null) {
                jazzcashResponse = new JazzcashResponse();
                HashMap responseMap = new ObjectMapper().readValue(response, new TypeReference<HashMap>() {});
                responseHashMap =    (HashMap<String, Object>) responseMap;
//                jazzcashResponse.setResponse( responseHashMap.get("pp_ResponseMessage"));
//                jazzcashResponse.setResponseCode(responseHashMap.get("pp_ResponseCode"));
//                jazzcashResponse.setTid(responseHashMap.get("pp_TxnRefNo"));
                //  jazzcashResponse.response(responseHashMap.get(""));
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    private static String getInquireRequest(String transReference, String hash){
        StringBuilder request = new StringBuilder("{\"pp_TxnRefNo\":\"");

        try{
            request.append(transReference)
                    .append("\",")
                    .append("\"pp_MerchantID\":\"")
                    .append(MERCHANT_ID)
                    .append("\",")
                    .append("\"pp_Password\":\"")
                    .append(MERCHANT_PASSWORD)
                    .append("\",")
                    .append("\"pp_SecureHash\":\"")
                    .append(hash)
                    .append("\"}");
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return request.toString();
    }



    private static String getPaymentRequest(String tranRefNumber, String amount, String transTime, String transEndTime, String reference,
                                            String description, String mobileNo, String hash, String CNIC) {
        StringBuilder request = new StringBuilder("{\"pp_Amount\":");

        try {
            request.append(amount)
                    .append(",")
                    .append("\"pp_BankID\":\"\",")
                    .append("\"pp_BillReference\":\"")
                    .append(reference)
                    .append("\",")
                    .append("\"pp_Description\":\"")
                    .append(description)
                    .append("\",")
                    .append("\"pp_Language\":\"")
                    .append(LANGUAGE)
                    .append("\",")
                    .append("\"pp_MerchantID\":\"")
                    .append(MERCHANT_ID)
                    .append("\",")
//			.append("\"pp_Description\":\"")
//			.append(description)
//			.append("\",")
                    .append("\"pp_Password\":\"")
                    .append(MERCHANT_PASSWORD)
                    .append("\",")
                    .append("\"pp_SubMerchantID\":\"")
                    .append(SUB_MERCHANT_ID)
                    .append("\",")
                    .append("\"pp_ProductID\":\"")
                    .append("")
                    .append("\",")
                    .append("\"pp_TxnCurrency\":\"")
                    .append(CURRENCY)
                    .append("\",")
                    .append("\"pp_TxnDateTime\":\"")
                    .append(transTime)
                    .append("\",")
                    .append("\"pp_TxnRefNo\":\"")
                    .append(tranRefNumber)
                    .append("\",")
                    .append("\"pp_TxnExpiryDateTime\":\"")
                    .append(transEndTime)
                    .append("\",")
                    .append("\"ppmpf_1\":\"")
                    .append("")
                    .append("\",")
                    .append("\"ppmpf_2\":\"")
                    .append("")
                    .append("\",")
                    .append("\"ppmpf_3\":\"")
                    .append("")
                    .append("\",")
                    .append("\"ppmpf_4\":\"")
                    .append("")
                    .append("\",")
                    .append("\"ppmpf_5\":\"")
                    .append("")
                    .append("\",")
                    .append("\"pp_MobileNumber\":\"")
                    .append(mobileNo)
                    .append("\",")
                    .append("\"pp_CNIC\":\"")
                    .append(CNIC)
                    .append("\",")
                    .append("\"pp_SecureHash\":\"")
                    .append(hash)
                    .append("\"}");




        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return request.toString();
    }


    private static String refundPayment(String tranRefNumber, String amount, String hash) {
        StringBuilder request = new StringBuilder("{\"pp_TxnRefNo\":");

        try {
            request.append("\"").
                    append(tranRefNumber)
                    .append("\",")
                    .append("\"pp_Amount\":\"")
                    .append(amount)
                    .append("\",")
                    .append("\"pp_TxnCurrency\":\"")
                    .append(CURRENCY)

                    .append("\",")
                    .append("\"pp_MerchantID\":\"")
                    .append(MERCHANT_ID)
                    .append("\",")

                    .append("\"pp_Password\":\"")
                    .append(MERCHANT_PASSWORD)
                    .append("\",")
                    .append("\"pp_MerchantMPIN\":\"")
                    .append(MerchantMPIN)
                    .append("\",")
                    .append("\"pp_SecureHash\":\"")
                    .append(hash)
                    .append("\"}");





        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return request.toString();
    }

    /*
     *
     *  Method to get incremented date as per given days-interval
     *
     *  */
    public static Date getExpiryDate(Integer daysInterval) {

        Calendar nextPaymentCalendar = new GregorianCalendar();
        nextPaymentCalendar.setTime(new Date());
        nextPaymentCalendar.add(Calendar.DAY_OF_MONTH, daysInterval);

        return nextPaymentCalendar.getTime();
    }


    public static String getRefundString(String tranRefNumber, String amount) {

        StringBuilder sortedString = new StringBuilder(MERCHANT_HASHKEY);
        try {
            Map<String, String> params = new TreeMap<String, String>();
            params.put("pp_TxnRefNo", tranRefNumber);
            params.put("pp_Amount", amount);
            params.put("pp_TxnCurrency", CURRENCY);
            params.put("pp_MerchantID", MERCHANT_ID);
            params.put("pp_Password", MERCHANT_PASSWORD);
            params.put("pp_MerchantMPIN", MerchantMPIN);


            System.out.println("Hash Input = ");
            System.out.println(params.toString());


            for(Map.Entry<String, String> map: params.entrySet()) {
                if((map.getValue() != null) && (map.getValue().trim().length() >0))
                    sortedString.append("&" + map.getValue());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return sortedString.toString();
    }

    /*
     *
     *  Method to generate "&" appended string in alphabatical order for hasing,
     *	where params value is not null or empty
     *
     *  */
    public static String getParamsString(String amount, String mobileNo, String transTime, String transEndTime, String transReference, String reference, String description,String CNIC) {

        StringBuilder sortedString = new StringBuilder(MERCHANT_HASHKEY);
        try {
            Map<String, String> params = new TreeMap<String, String>();
            params.put("pp_Language", LANGUAGE);
            params.put("pp_MerchantID", MERCHANT_ID);
            params.put("pp_SubMerchantID", SUB_MERCHANT_ID);
            params.put("pp_Password", MERCHANT_PASSWORD);
            params.put("pp_BankID", "");
            params.put("pp_ProductID", "");
            params.put("pp_TxnRefNo", transReference);
            params.put("pp_Amount", amount);
            params.put("pp_TxnCurrency", CURRENCY);
            params.put("pp_TxnDateTime", transTime);
            params.put("pp_BillReference", reference);
            params.put("pp_Description", description);
            params.put("pp_TxnExpiryDateTime", transEndTime);
            params.put("pp_SecureHash","");
            params.put("ppmpf_1", "");
            params.put("ppmpf_2","");
            params.put("ppmpf_3","");
            params.put("ppmpf_4","");
            params.put("ppmpf_5","");
            params.put("pp_MobileNumber", mobileNo);
            params.put("pp_CNIC", CNIC);

            System.out.println("Hash Input = ");
            System.out.println(params.toString());


            for(Map.Entry<String, String> map: params.entrySet()) {
                if((map.getValue() != null) && (map.getValue().trim().length() >0))
                    sortedString.append("&" + map.getValue());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return sortedString.toString();
    }


    /*
     *
     *  Method to generate "&" appended string in alphabatical order for hasing,
     *	where params value is not null or empty
     *
     *  */
    public static String getParamsInquire(String transReference) {

        StringBuilder sortedString = new StringBuilder(MERCHANT_HASHKEY);
        try {
            Map<String, String> params = new TreeMap<String, String>();

            params.put("pp_MerchantID", MERCHANT_ID);
            params.put("pp_Password", MERCHANT_PASSWORD);
            params.put("pp_TxnRefNo", transReference);
            params.put("pp_SecureHash","");

            System.out.println("Hash Input = ");
            System.out.println(params.toString());
            for(Map.Entry<String, String> map: params.entrySet()) {
                if((map.getValue() != null) && (map.getValue().trim().length() >0))
                    sortedString.append("&" + map.getValue());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return sortedString.toString();
    }



    /*
     *
     *  Method to generate hash against request params
     *
     *
     *  */
    public static String  getHash(String paramsString) {
        String hmac = null;

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret = new SecretKeySpec(MERCHANT_HASHKEY.getBytes(), "HmacSHA256");
            mac.init(secret);
            byte[] digest = mac.doFinal(paramsString.getBytes());

            BigInteger hash = new BigInteger(1, digest);
            hmac = hash.toString(16);
            if (hmac.length() % 2 != 0) {
                hmac = "0" + hmac;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return hmac ;

    }


    private static String postRequest(String request, String url) throws IOException {

        try {
            logger.info("JAZZCASHREST REQUEST :::" + request);

            URL obj = new URL(url);
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("Content-Type", "application/json");

            postConnection.setDoOutput(true);

            OutputStream os = postConnection.getOutputStream();
            os.write(request.getBytes());
            os.flush();
            os.close();

            int responseCode = postConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        postConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                }
                in .close();
                logger.info("JAZZCASHREST RESPONSE :: " + response.toString());
                return response.toString();
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        postConnection.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                }
                in .close();
                logger.info("JAZZCASHREST RESPONSE :: " + response.toString());
                return response.toString();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    static JazzCashRestService jazzCashRestService = new JazzCashRestService();


    public static void main(String[] args) throws IOException {
        JazzCashRestService easypaisaImplementaiton = new JazzCashRestService();

        // System.out.println("Response of inquire =" + easypaisaImplementaiton.jazzcashInquire("T20210208155115"));

        System.out.println("inquire request = " + getInquireRequest("transss123", "hasdasd12312"));
        //3123456789
        // JazzcashResponse str = easypaisaImplementaiton.makePayment("3123456789", "100","345678","T"+12412);

//        jazzCashRestService.postRequest("{\n" +
//                "    \"merchantId\": \"1000004\",\n" +
//                "    \"operatorId\": \"100007\",\n" +
//                "     \"productId\": 1009,\n" +
//                "    \"transactionType\": \"1\",\n" +
//                "    \"userKey\": \"test-06\",\n" +
//                "    \"msisdn\": \"3133925769\"\n" +
//                "}", "https://staging.simpaisa.com:9121/v2/wallets/transaction/initiate");


        //   jazzCashRestService.makePayment((""+ "3133925769"), new DecimalFormat("#").format(1 * 100), "445137","" + "1241241");
        // System.out.println("Str = " + str.toString());
//
//        System.out.println("Response Message = " + str.get("pp_ResponseMessage"));
//        System.out.println("Response Code = " + str.get("pp_ResponseCode"));
//	HashMap<String, String> response = easypaisaImplementaiton.makeLinkRequest("3133925769", 123112l, "1","6005") ;
//
//	System.out.println("Response =" +response);
//	System.out.println("ResponseCode =" +response.get("responseCode"));
//
//		String response = easypaisaImplementaiton.deactiveLink("3313511370", "0000204651" );
//		System.out.println("EasyPay Response = " + response);
    }

}
