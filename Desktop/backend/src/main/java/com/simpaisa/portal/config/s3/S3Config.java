package com.simpaisa.portal.config.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Value("${aws.s3.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public AmazonS3 s3Client(){
        BasicAWSCredentials awsCredentials  = new BasicAWSCredentials(accessKeyId, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                             .withRegion(Regions.fromName(region))
                              .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                              .build();

        return s3Client;
    }
}
