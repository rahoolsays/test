package com.simpaisa.portal.utils;

import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.LinkedHashMap;

public class CipherUtil {
	

	   // private static final String KEY_LOCATION = "D:\\merchantKeys\\";
	   private static final String KEY_LOCATION ="/usr/local/Simpaisa-Work/merchantKeys/";

	    public static final String ALGORITHM = "SHA256withRSA";
	    /*public final static String MERCHANT_PUBLIC_KEY = "_PublicKey.pem";*/
	    public final static String MERCHANT_PRIVATE_KEY = "merchantPrivateKey.pem";
	    public final static String SIMPAISA_PUBLIC_KEY = "simpaisaPublicKey.pem";
	    public final static String SIMPAISA_PRIVATE_KEY = "simpaisaPrivateKey.pem";

	    /*
	     *
	     * Method to sign
	     *
	     * */
	    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
	        Signature privateSignature = Signature.getInstance(ALGORITHM);
	        privateSignature.initSign(privateKey);
	        privateSignature.update(plainText.getBytes(StandardCharsets.UTF_8));

	        byte[] signature = privateSignature.sign();

	        return java.util.Base64.getEncoder().encodeToString(signature);
	    }

	    public static String sign(String plainText) throws Exception {
	        return sign(plainText, getPemPrivateKey(SIMPAISA_PRIVATE_KEY));
	    }

	    public static String sign(LinkedHashMap<String, Object> plainText) throws Exception {
	        Gson gson = new Gson();
	        return sign(gson.toJson(plainText, LinkedHashMap.class), getPemPrivateKey(SIMPAISA_PRIVATE_KEY));
	    }

	    public static  boolean verify(LinkedHashMap<String, Object> request, String signature, long merchantId) throws Exception {
	        boolean valid = false;
	        Gson gson = new Gson();

	        String json = gson.toJson(request, LinkedHashMap.class);

	        System.out.println(json.toString());
	        try{
	            valid = verify(json.toString(), signature, getPemPublicKey(merchantId));
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }

	        return valid;
	    }

	    public static  boolean verify(String plainText, String signature, long merchantId) throws Exception {
	       boolean valid = false;
	       try{
	           valid = verify(plainText, signature, getPemPublicKey(merchantId));
	       }
	       catch (Exception e)
	       {
	           e.printStackTrace();
	       }

	       return valid;
	    }

	    private static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
	        Signature publicSignature = Signature.getInstance(ALGORITHM);
	        publicSignature.initVerify(publicKey);
	        publicSignature.update(plainText.getBytes(StandardCharsets.UTF_8));

	        byte[] signatureBytes = java.util.Base64.getDecoder().decode(signature);

	        return publicSignature.verify(signatureBytes);
	    }

	    /*
	     *
	     *  Method to encrypt text
	     *
	     *  */
	    /*private String encrypt(String plainText){
	        try {
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.ENCRYPT_MODE, getPemPublicKey(SIMPAISA_PRIVATE_KEY));

	            return Base64.encodeBase64String(cipher.doFinal(plainText.getBytes())) ;
	        } catch(Exception exception) {
	            exception.printStackTrace();
	        }
	        return null;
	    }*/

	    /*
	     *
	     *  Method to decrypt text
	     *
	     *  */
	   /* private String decrypt(String encryptedText){
	        try {
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.DECRYPT_MODE, getPemPrivateKey(MERCHANT_PUBLIC_KEY));

	            byte[] decryptedTextArray = cipher.doFinal(Base64.decodeBase64(encryptedText));

	            return new String(decryptedTextArray);
	        } catch(Exception exception) {
	            exception.printStackTrace();
	        }
	        return null;
	    }*/

	    /*
	     *
	     * Method to access private-key from pem file
	     *
	     * */
	    public static PublicKey getPemPublicKey(long merchantId) throws Exception {


	        String temp = null;
	        String fileName = KEY_LOCATION + merchantId + "_" + "PublicKey.pem";
	        System.out.println(fileName);
	        try {
	            /*byte[] bdata = FileCopyUtils.copyToByteArray((new ClassPathResource(fileName)).getInputStream());*/
	            byte[] bdata = FileCopyUtils.copyToByteArray(new File(fileName));
	            temp = new String(bdata, StandardCharsets.UTF_8);
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        }
	        catch (Exception e)
	        {
	            throw e;
	        }
	        String pubKeyPEM = temp.replace("-----BEGIN RSA PUBLIC KEY-----", "")
	                .replace("-----BEGIN PUBLIC KEY-----", "")
	                .replace("\n", "")
	                .replace("-----END RSA PUBLIC KEY-----", "")
	                .replace("-----END PUBLIC KEY-----", "");

	        X509EncodedKeySpec specPriv = new X509EncodedKeySpec(Base64.decodeBase64(pubKeyPEM.getBytes()));
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        return keyFactory.generatePublic(specPriv);
	    }



	    /*
	     *
	     * Method to access private-key from pem file
	     *
	     * */
	    public static PrivateKey getPemPrivateKey(String fileName) throws Exception {


	        String temp = null;
	        try {
	            byte[] bdata = FileCopyUtils.copyToByteArray((new ClassPathResource(fileName)).getInputStream());
	            temp = new String(bdata, StandardCharsets.UTF_8);
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        }
	        String privKeyPEM = temp.replace("-----BEGIN RSA PRIVATE KEY-----", "")
	                .replace("\n", "")
	                .replace("-----END RSA PRIVATE KEY-----", "");

	        PKCS8EncodedKeySpec specPriv = new PKCS8EncodedKeySpec(Base64.decodeBase64(privKeyPEM.getBytes()));
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        return keyFactory.generatePrivate(specPriv);
	    }
	    
	    
	   


}
