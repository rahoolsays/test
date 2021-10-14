package com.simpaisa.portal.utility;

import com.simpaisa.portal.enums.Responses;
import org.jboss.logging.Logger;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Utility {

    public final static String EMAIL = "email";
    public final static String PASSWORD = "password";
    public final static String TITLE = "title";
    public final static String FIRST_NAME = "firstName";
    public final static String LAST_NAME = "lastName";
    public final static String EMAIL_TYPE = "emailType";
    public final static String MESSAGE = "message";
    public final static String STATUS = "status";
    public final static String TOKEN = "token";
    public final static String CHANGE_PASSWORD_TOKEN = "token";
    public final static String ROLE = "role";
    public final static String PRIVILEGES = "privileges";
    public final static String POSTBACK_URL = "postbackUrl";

    public final static String ENVIRONMENT = "environment";
    public final static String REJECTION_REASON = "rejectionReason";
    public final static String PASSWORD_RESETURL = "resetPassword";


    public final static short JDBC_STAGING = 1;
    public final static short JDBC_LIVE = 2;




    public final static String MERCHANT_DETAIL = "merchantDetail";

    public final static String ROLE_ADMIN = "ADMIN";
    public final static String ROLE_MERCHANT = "MERCHANT";

    public final static String EMAIL_TYPE_KYC_REVIEW = "KYC_REVIEW";
    public final static String EMAIL_TYPE_KYC_APPROVED = "KYC_APPROVED";
    public final static String EMAIL_TYPE_REJECTED = "KYC_REJECTED";

    public final static String EMAIL_TYPE_PASSWORD_RESET = "PASSWORD_RESET";

    public static final String SUBJECT_VERIFICATION = "Verification Email";
    public static final String SUBJECT_WELCOME = "Welcome Email";
    public static final String SUBJECT_KYC_REVIEW = "KYC Review";

    public static final String SUBJECT_KYC_REVIEW_APPROVED = "KYC Review Approved";
    public static final String SUBJECT_KYC_REVIEW_REJECTED = "KYC Review Rejected";
    public static final String SUBJECT_PASSWORD_RESET = "Reset Password";

    public static final String TO = "muhiuddin.shahani@gmail.com";
    public static final String FROM="muhiuddin@simpaisa.com"; // domain email


//    public static final String BASE_URL = "http://localhost:8074/";
//    public static final String LOGIN_URL = "http://localhost:4200/login";
//
//    public static final String PORTAL_BASE_URL = "http://localhost:4200/";

    public static final String BASE_URL = "http://ec2-13-213-68-198.ap-southeast-1.compute.amazonaws.com:8074/";
    public static final String LOGIN_URL = "http://ec2-13-213-68-198.ap-southeast-1.compute.amazonaws.com:4200/login";
    public static final String PORTAL_BASE_URL = "http://ec2-13-213-68-198.ap-southeast-1.compute.amazonaws.com:4200/";


    // Operator Codes
    public static final long[] OPERATORID = {100007L,100008L};
    public static final String DEFAULT_PRODUCT = "default";

    //Email Templates
    public static final String VERIFICATION_EMAIL_TEMPLATE = "verification-email-template.ftl";
    public static final String WELCOME_EMAIL_TEMPLATE = "welcome-email-template.ftl";

    public static final String KYC_REVIEW_TEMPLATE = "kyc_review_template.ftl";
    public static final String KYC_REVIEW_APPROVED_TEMPLATE = "kyc_review_approved_template.ftl";
    public static final String KYC_REVIEW_REJECTED_TEMPLATE = "kyc_review_rejected_template.ftl";
    public static final String PASSWORD_RESET_TEMPLATE = "password_change_template.ftl";

   // public final static String PASSWORD = "password";


        Map<String, Object> response = new LinkedHashMap<String, Object>();
    public static final String MERCHANTS_URL = "/merchants/";
    public static final String DISBURSEMENTS = "/disbursements/";

    //Mongo DB Collection Names
    public static final String CUSTOMER = "disbursement_customer";

    //Mandotory CUstomer fields
    public static final String REFERENCE = "reference";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String CUSTOMER_CONTACT = "customerContact";
    public static final String CUSTOMER_DOB = "customerDob";
    public static final String CUSTOMER_EMAIL = "customerEmail";
    public static final String GENDER = "customerGender";
    public static final String ACCOUNT = "customerAccount";
    public static final String ACCOUNT_TYPE = "accountType";
    public static final String DESTINATION_BANK = "destinationBank";
    public static final String CUSTOMER_REF = "customerReference";
    public static final String AMOUNT = "amount";
    public static final String CURRENCY = "currency";
    public static final String NARRATION = "narration";
    public static final String FROM_DATE = "fromDate";
    public static final String TO_DATE = "toDate";
    public static final String STATE = "state";
    public static final String LIMIT = "limit";
    public static final String COMMENTS = "comments";
    public static final String CUSTOMER_ADDRESS = "customerAddress";


    //Optional Customer Fields
    public static final String ADDRESS = "customerAddress";
    public static final String COUNTRY = "country";
    public static final String CITY = "city";
    public static final String STREET_ADDRESS = "streetAddress";
    public static final String POSTAL_CODE = "postalCode";
    public static final String LANDMARK = "landmark";
    public static final String FREEFORMADDRESS = "freeformAddress";
    public static final String MARITAL_STATUS = "customerMaritalStatus";
    public static final String CNIC = "customerIdNumber";
    public static final String CNIC_EXPIRY = "customerIdExpirationDate";
    public static final String NTN = "customerNtnNumber";
    public static final String BRANCH_CODE = "branchCode";
    public static final String MERCHANT_ID = "merchantId";
    //public static final String LOGO = "logo";
    public static final String LOGO_URL = "logoUrl";
    public static final String REQUEST = "request";
    public static final String SIGNATURE = "signature";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String SQL_ASC = "ASC";
    public static final String SQL_DESC = "DESC";
    public static final String PAGE_NO ="pageNo" ;
    public static final String ORDER_BY = "orderBy";
    public static final String DIRECTION = "direction";


    //FileNames
    public static final String AUTH_LETTER ="authorityLetter";
    public static final String CNIC_BACK ="cnicBack";
    public static final String CNIC_FRONT ="cnicFront";
    public static final String MERCHANT_AGREEMENT ="merchantAgreement";
    public static final String NTN_CERTIFICATE ="ntnCertificate";
    public static final String PARTNERSHIP_DEED ="partnershipDeed";

    public static final String ARTICLE_OF_ASSOCIATION ="articleOfAssociation";
    public static final String FORM_A ="formA";
    public static final String INCORPORATION_CERTIFCATE ="incorporationCetificate";
    public static final String ACCOUNT_MAINTANANCE_CERTIFCATE ="accountMaintenanceCetificate";
    public static final String LAST_3_MONTH_BILL ="last3MonthBill";

    public static final String SEARCH = "search";
    public static final String APPLY_ON_CUSTOMER = "CUSTOMER";
    public static final String APPLY_ON_MERCHANT = "MERCHANT";


    public  static  final int KYC_IN_REVIEW = 1;
    public  static  final int KYC_ACCEPTED = 2;
    public  static  final int KYC_IN_REJECTED = 3;


    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
    //generate Reset Token
    public static String generatePasswordResetToken(){
        StringBuilder sb = new StringBuilder();
       return sb.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

    public static boolean isPasswordTokenExpired(final LocalDateTime tokenGenrationDate){
        LocalDateTime nowTime = LocalDateTime.now();
        Duration duration = Duration.between(tokenGenrationDate, nowTime);

        return duration.toMinutes()>= EXPIRE_TOKEN_AFTER_MINUTES;
    }
    public static Date formatDateStrToDate(String dateStr, String format) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            simpleDateFormat.setLenient(false);
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
           // logger.error(Logger.class.getName(), "Error occurred while converting {0} to {1}.",
          //          new Object[] { dateStr, format }, e);
            System.out.println( "Error occurred while converting {0} to {1}.");
        }
        return date;
    }

    public static Timestamp formatDateStrToTimeStamp(String dateStr, String format) {
        Date date = formatDateStrToDate(dateStr, format);
        Timestamp timestamp = (date == null ? null : new Timestamp(date.getTime()));
        return timestamp;
    }

    public static boolean isValidMaritialStatus(String value){
        boolean res = false;
        if(value.equalsIgnoreCase("SINGLE") || value.equalsIgnoreCase("MARRIED") || value.equalsIgnoreCase("DIVORCED"))
        {
            res = true;
        }

        return res;

    }
    public static boolean isValidGender(String value)
    {
        boolean res = false;
        if(value.equalsIgnoreCase("MALE") || value.equalsIgnoreCase("FEMALE") || value.equalsIgnoreCase("OTHER"))
        {
            res = true;
        }

        return res;
    }

    public static boolean isValidAccountType(String value){
        boolean res = false;
        if (value.equalsIgnoreCase("BA") || value.equalsIgnoreCase("DW"))
        {
            res = true;
        }
        return res;
    }

    public static LinkedHashMap<String, Object> getResponse(Responses responses){
        LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(Utility.STATUS, responses.getStatus());
        response.put(Utility.MESSAGE, responses.getMessage());
        return response;
    }

    public static LinkedHashMap<String, Object> getResponse(Responses responses, Map<String, Object> data){
        LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(Utility.STATUS, responses.getStatus());
        response.put(Utility.MESSAGE, responses.getMessage());

        if(data.containsKey(Utility.REFERENCE))
            response.put(Utility.REFERENCE,data.get(Utility.REFERENCE));

        if(data.containsKey(Utility.CUSTOMER_REF))
            response.put(Utility.REFERENCE,data.get(Utility.REFERENCE));


        if(data.containsKey(Utility.STATE))
            response.put(Utility.STATE, data.get(Utility.STATE));

        return response;
    }

    public static double formatAmount(double amount){
        DecimalFormat df = new DecimalFormat("#.##");
        double value = Double.valueOf(df.format(amount));
        return value;
    }




}
