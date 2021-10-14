package com.simpaisa.portal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class HblKonnectService {
	private static String API_URL = "https://testpaymentapi.hbl.com/OpenApiRest/api/OpenApi/ProcessTransaction";
	private final static String OTP_TRANCODE = "01";
	private final static String FUND_TRANSFER_TRANCODE = "02";
	private final static String REFUND_TRANCODE = "03";
	private final static String ACCOUNT_TITLE_TRANCODE = "04";
	private final static String ACCOUNT_VERIFICATION_TRANCODE = "05";
	private final static String ACCOUNT_BALANCE_TRANCODE = "06";
	//private String CHANNEL = "SimPaisa_MobileBanking";
	private String CHANNEL = "SimPaisa_Web";
	private String USER_ID = "simpaisaadmin";
	private String PASSWORD = "Cm55fhDbNL";
	
	
/*	public static void main(String[] args) {
		try {
			HblKonnectImpl hblKonnectImpl = new HblKonnectImpl();
			//hblKonnectImpl.postRequest(hblKonnectImpl.getOtpRequest("2-5667777777735-03211548787",  "CG-1234566788", "03466245388", "6888666877797", "500", "TESTING"), API_URL);

			String request = hblKonnectImpl.getOtpRequest("2-5667777777735-03211548787",  "CG-1234566788", "08140019452301", "7140212128643", "500", "TESTING");
			*//*String request = hblKonnectImpl.getFundTransferRequest("2-5667777777735-03211548787",
					"CG-1234566788","03466245388","6888666877797","500","","","TESTTING",
					"TESTTING","TESTTING");*//*


			hblKonnectImpl.postRequest(request,API_URL);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	*/

	public Map makeOtpRequest(Map<String, String> data) {
		Map response = null;
		try {
		 response = postRequest(getOtpRequest(data.get("stan"), data.get("clientGuid"), data.get("accountNumber"),
				data.get("cnic"), data.get("amount"), "testing"), API_URL);




	}
		catch (Exception e) {
		e.printStackTrace();
	}

		return response;

	}

	public Map makeFTRequest(Map<String, String> data) {
		Map response = null;
		try {
			 response = postRequest(getFundTransferRequest(data.get("stan"), data.get("clientGuid"), data.get("accountNumber"),
					data.get("cnic"),data.get("amount"),data.get("otp"), data.get("refId"),"testing"), API_URL);

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return response;

	}

	public Map makeRefundRequest(Map<String, String> data) {
		Map response = null;
		try {
			response = postRequest(getRefundRequest(data.get("stan"), data.get("clientGuid"), data.get("accountNumber"),
					data.get("cnic"),data.get("amount"), data.get("refId"),"testing"), API_URL);

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return response;

	}

	public String getOtpRequest(String stan, String clientGuid,
								String srcAccount, String srcCnic, String amount,String narration){
		StringBuilder request = new StringBuilder("");
		try {
			request.append(
					makeHeader(OTP_TRANCODE,stan,clientGuid)
			)
					.append("\"Body\": {")
					.append("\"SrcAccount\": \"")
					.append(encrypt(srcAccount))
					.append("\",")
					.append("\"SrcCnic\": \"")
					.append(encrypt(srcCnic))
					.append("\",")
					.append("\"Amount1\": \"")
					.append(encrypt(amount))
					.append("\",")
					.append("\"Narration\": \"")
					.append(encrypt(narration))
					.append("\"}}");
		} catch (Exception exception){
			exception.printStackTrace();
		}
		System.out.println(request);
		return request.toString();
	}

	public String getFundTransferRequest(String stan, String clientGuid,
										 String srcAccount, String srcCnic, String amount, String passCode, String refId, String narration
			//, String data1, String data2
	)
	{
		StringBuilder request = new StringBuilder("");
		try {
			request.append(
					makeHeader(FUND_TRANSFER_TRANCODE,stan,clientGuid)
			)
					.append("\"Body\": {")
					.append("\"SrcAccount\": \"")
					.append(encrypt(srcAccount))
					.append("\",")
					.append("\"SrcCnic\": \"")
					.append(encrypt(srcCnic))
					.append("\",")
					.append("\"Amount1\": \"")
					.append(encrypt(amount))
					.append("\",")
					.append("\"Passcode\": \"")
					.append(encrypt(passCode))
					.append("\",")
					.append("\"RefId\": \"")
					.append(encrypt(refId))
					.append("\",")
					.append("\"Narration\": \"")
					.append(encrypt(narration))
					//.append("\",")
					//.append("\"Data1\": \"")
					//.append(encrypt(data1))
					//.append("\",")
					//.append("\"Data2\": \"")
					//.append(encrypt(data2))
					.append("\"}}");
		} catch (Exception exception){
			exception.printStackTrace();
		}
		System.out.println(request);
		return request.toString();

	}

	public String getRefundRequest(String stan, String clientGuid,
								   String srcAccount, String srcCnic, String amount,  String refId, String narration)
	{
		StringBuilder request = new StringBuilder("");
		try {
			request.append(
					makeHeader(REFUND_TRANCODE,stan,clientGuid)
			)
					.append("\"Body\": {")
					.append("\"SrcAccount\": \"")
					.append(encrypt(srcAccount))
					.append("\",")
					.append("\"SrcCnic\": \"")
					.append(encrypt(srcCnic))
					.append("\",")
					.append("\"Amount1\": \"")
					.append(encrypt(amount))
					.append("\",")
					.append("\"RefId\": \"")
					.append(encrypt(refId))
					.append("\",")
					.append("\"Narration\": \"")
					.append(encrypt(narration))
					.append("\"}}");
		} catch (Exception exception){
			exception.printStackTrace();
		}
		System.out.println(request);
		return request.toString();
	}

	public String getAccountTitleRequest(String stan, String clientGuid,
										 String srcAccount, String srcCnic, String passCode, String refId, String narration)
	{
		StringBuilder request = new StringBuilder("");
		try {
			request.append(
					makeHeader(ACCOUNT_TITLE_TRANCODE,stan,clientGuid)
			)
					.append("\"Body\": {")
					.append("\"SrcAccount\": \"")
					.append(encrypt(srcAccount))
					.append("\",")
					.append("\"SrcCnic\": \"")
					.append(encrypt(srcCnic))
					.append("\",")
					.append("\"Passcode\": \"")
					.append(encrypt(passCode))
					.append("\",")
					.append("\"RefId\": \"")
					.append(encrypt(refId))
					.append("\",")
					.append("\"Narration\": \"")
					.append(encrypt(narration))
					.append("\"}}");
		} catch (Exception exception){
			exception.printStackTrace();
		}
		System.out.println(request);
		return request.toString();
	}

	public String getAccountVerificationRequest(String stan, String clientGuid,
												String srcAccount, String srcCnic, String passCode, String refId, String narration)
	{
		StringBuilder request = new StringBuilder("");
		try {
			request.append(
					makeHeader(ACCOUNT_VERIFICATION_TRANCODE,stan,clientGuid)
			)
					.append("\"Body\": {")
					.append("\"SrcAccount\": \"")
					.append(encrypt(srcAccount))
					.append("\",")
					.append("\"SrcCnic\": \"")
					.append(encrypt(srcCnic))
					.append("\",")
					.append("\"Passcode\": \"")
					.append(encrypt(passCode))
					.append("\",")
					.append("\"RefId\": \"")
					.append(encrypt(refId))
					.append("\",")
					.append("\"Narration\": \"")
					.append(encrypt(narration))
					.append("\"}}");
		} catch (Exception exception){
			exception.printStackTrace();
		}
		System.out.println(request);
		return request.toString();
	}

	public String getAccountBalanceRequest(String stan, String clientGuid,
										   String srcAccount, String srcCnic, String passCode, String refId, String narration)
	{
		StringBuilder request = new StringBuilder("");
		try {
			request.append(
					makeHeader(ACCOUNT_BALANCE_TRANCODE,stan,clientGuid)
			)
					.append("\"Body\": {")
					.append("\"SrcAccount\": \"")
					.append(encrypt(srcAccount))
					.append("\",")
					.append("\"SrcCnic\": \"")
					.append(encrypt(srcCnic))
					.append("\",")
					.append("\"Passcode\": \"")
					.append(encrypt(passCode))
					.append("\",")
					.append("\"RefId\": \"")
					.append(encrypt(refId))
					.append("\",")
					.append("\"Narration\": \"")
					.append(encrypt(narration))
					.append("\"}}");
		} catch (Exception exception){
			exception.printStackTrace();
		}
		System.out.println(request);
		return request.toString();
	}

	/*
	 *
	 *  Method to create header of requests
	 *
	 *  */
	private String makeHeader(String tranCode, String stan, String clientGuid){
		StringBuilder request = new StringBuilder("");
		try
		{
			request.append("{ \"Header\": {")
					.append("\"TranCode\": \""+tranCode+"\",")
					.append("\"Stan\": \"")
					.append(encrypt(stan))
					.append("\",")
					.append("\"Channel\": \"")
					.append(encrypt(CHANNEL))
					.append("\",")
					.append("\"UserID\": \"")
					.append(USER_ID)
					.append("\",")
					.append("\"Password\": \"")
					.append(encrypt(PASSWORD))
					.append("\",")
					.append("\"ClientGuid\": \"")
					.append(encrypt(clientGuid))
					.append("\"},");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return request.toString();

	}
	
	
	
	/*
	 * 
	 *  Method to post request 
	 *  
	 *  */
    private Map<String, String> postRequest(String request, String url) throws IOException {
		HttpURLConnection postConnection = null;
		Map<String, String> responseMap = new LinkedHashMap<String, String>();
		try {
			
			
	        URL obj = new URL(url);
	        
	        postConnection = (HttpURLConnection) obj.openConnection();
	        postConnection.setRequestMethod("POST");
	        postConnection.setRequestProperty("Content-Type", "application/json");
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
	        	System.out.println("HBL RESPONSE :: " + response.toString() + " HttpResponseMessage:: " + responseMessage + " HttpResponseCode :: " + responseCode);
	        	
	        	
	        	Map<String,Object> result = new ObjectMapper().readValue(response.toString(), HashMap.class);
				Map<String, Object> map = ((Map<String, Object>) result.get("Response"));
	        	Iterator it = ((Map<String, Object>) result.get("Response")).entrySet().iterator();
				//System.out.println(decrypt(map.get("ResponseDetail1").toString())); SUCCESS or FAILED
	        	while (it.hasNext()) {
	                Map.Entry pair = (Map.Entry)it.next();

					if(StringUtils.isNotEmpty(pair.getValue().toString())) {
						responseMap.put(String.valueOf(pair.getKey()), ((!"ResponseCode".equals(pair.getKey())) ? decrypt(pair.getValue().toString()) : String.valueOf(pair.getValue())));
					}
					else
					{
						responseMap.put(String.valueOf(pair.getKey()),  String.valueOf(pair.getValue()));
					}
	            }
	        	
	            return responseMap;
	        } catch (Exception exception){
	        	
				System.out.println("HBL RESPONSE :: Exception Occured " + " HttpResponseMessage:: " + responseMessage + " HttpResponseCode :: " + responseCode);
				exception.printStackTrace();
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
	
	
	
	
	/*
	 *  
	 *  Method to encrypt text 
	 *  
	 *  */
	private String encrypt(String plainText){
        try {
        	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPemPublicKey());
            
            return Base64.encodeBase64String(cipher.doFinal(plainText.getBytes())) ;
        } catch(Exception exception) {
        	exception.printStackTrace();
        }
        return null;
    }
    
	
	
	
	
	/*
	 * 
	 *  Method to decrypt text 
	 *  
	 *  */
    private String decrypt(String encryptedText){
    	try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, getPemPrivateKey());
            
            byte[] decryptedTextArray = cipher.doFinal(Base64.decodeBase64(encryptedText));
            
            return new String(decryptedTextArray);
    	} catch(Exception exception) {
    		exception.printStackTrace();
    	}
    	return null;
    }
	
	
    
	
	
	/* 
	 * 
	 * Method to access private-key from pem file 
	 * 
	 * */
    private PublicKey getPemPublicKey() throws Exception {
    	
    	
    	String temp = null;
    	try {
            byte[] bdata = FileCopyUtils.copyToByteArray((new ClassPathResource("hbl_konnect_pub.pem")).getInputStream());
            temp = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        String pubKeyPEM = temp.replace("-----BEGIN RSA PUBLIC KEY-----", "")
        						.replace("\n", "")
        						.replace("-----END RSA PUBLIC KEY-----", "");
        
        X509EncodedKeySpec  specPriv = new X509EncodedKeySpec(Base64.decodeBase64(pubKeyPEM.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(specPriv);
    }
	
    
	
	/* 
	 * 
	 * Method to access private-key from pem file 
	 * 
	 * */
    private PrivateKey getPemPrivateKey() throws Exception {
    	
    	
    	String temp = null;
    	try {
            byte[] bdata = FileCopyUtils.copyToByteArray((new ClassPathResource("hbl_konnect_prv_pcks8.pem")).getInputStream());
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

}
