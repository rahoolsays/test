package com.simpaisa.portal.service.s3bucket;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Service
public class S3BucketService {
    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;


    public Map<String,Object> uploadFile(String keyName, MultipartFile file){
        Map<String, Object> response = null;
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            PutObjectResult putObjectResult =  s3Client.putObject(bucketName, keyName, file.getInputStream(), metadata);
            //get uploaded url
            URL s3Url = s3Client.getUrl(bucketName, keyName);
            if(s3Url!=null){
                if(s3Url.toExternalForm()!=null){
                    response = Utility.getResponse(Responses.SUCCESS);
                    response.put(Utility.LOGO_URL, s3Url.toExternalForm());

                }
            }
        }catch (AmazonServiceException ase){
            ase.printStackTrace();
        }catch (AmazonClientException ace){
            ace.printStackTrace();
            throw ace;
        }catch (Exception ioe){
            ioe.printStackTrace();
        }
        if(response == null){
            response = Utility.getResponse(Responses.SYSTEM_ERROR);
        }
        return response;
    }
}
