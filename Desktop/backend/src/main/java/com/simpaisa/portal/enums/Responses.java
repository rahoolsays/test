package com.simpaisa.portal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Responses {
    SUCCESS("0000", "Success"),
    INVALID_EMAIL("0001", "Invalid-Email"),
    INVALID_CREDENTIAL("0002", "Invalid-Credentials"),
    USER_EXISTS("0003", "User-Exists"),
    USER_NOT_VERIFIED("0004", "User-Not-Verified"),
    ERROR("0071", "Error"),

    BUSINESS_REGISTERED("0005", "Bussiness-Name-Registered"),
    WEBSITE_REGISTERED("0006", "Website-Registered"),
    NOT_EXPIRED("0007", "Not-Expired"),
    ALREADY_VERIFIED("0008", "Already-Verified"),
    NO_DATA("0009", "No-Data"),
    INVALID_FILE("0010","Invalid-file"),
    INVALID_REF("0044","Invalid-Reference"),
    INVALID_CUSTOMERNAME("0045","Invalid-Customer-Name"),
    INVALID_CUSTOMER_CONTACT("0046","Invalid-Customer-Contact"),
    INVALID_CUSTOMER_EMAIL("0047","Invalid-Customer-Email"),
    INVALID_CUSTOMER_DOB("0048","Invalid-Customer_DOB"),
    INVALID_CUSTOMER_GENDER("0049","Invalid-Customer-Gender"),
    INVALID_CUSTOMER_ACCOUNT("0050","Invalid-Customer-Account"),
    INVALID_ACCOUNT_TYPE("0051","Invalid-Account-Type"),
    INVALID_DESTINATION_BANK("0052","Invalid-Destination-Bank"),
    INVALID_CUSTOMER_REFERENCE("0053","Invalid-Customer-Reference"),
    INVALID_SIGNATURE("0054","Invalid-Signature"),
    REFERENCE_ALREADY_EXISTS("0055","reference-already-exists"),
    CUSTOMER_DOES_NOT_EXISTS("0056","Customer-does-not-exists"),
    INVALID_MERCHANT("0057", "Invalid-Merchant"),
    INVALID_START_DATE("0058", "Invalid-From-Date"),
    INVALID_END_DATE("0059", "Invalid-to-Date"),
    INVALID_AMOUNT_PRODUCT("0060","Invalid-Product/Amount"),
    INVALID_CALL("0061", "Invalid-Call"),
    ROLE_ALREADY_EXISTS("0062","Role already exists."),
    ACCOUNT_CAN_NOT_BE_UPDATED("0063","Account-can-not-be-updated"),
    ACCOUNT_TYPE_CAN_NOT_BE_UPDATED("0064","Account-type-can-not-be-updated"),
    DESTINATION_BANK_CAN_NOT_BE_UPDATED("0065","Destination-bank-can-not-be-updated"),

    MERCHANT_ALREADY_EXISTS("0066", "Merchant-Exists"),
    USER_INACTIVE("0069", "User-Inactive"),
    PASSWORD_EMPTY("0070", "Password-Empty"),

    INVALID_TOKEN("0071", "Invalid-Token"),
    TOKEN_EXPIRED("0072", "Token-Expired"),

    INVALID_REFUND_REQUEST("0045", "INVALID-REFUND-REQUEST"),
    MISSING_PARAMETER("0015", "INVALID-PARAMETERS"),
    REQUEST_ALREADY_EXIST("0019", "REQUEST-ALREADY-EXIST"),


    SYSTEM_ERROR("9999", "System-Error");
    @lombok.Getter
    private String status;
    @lombok.Getter
    private String message;

}
