package com.simpaisa.portal.service;

import com.simpaisa.portal.enums.Responses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import com.simpaisa.portal.utility.Utility;

@Service
public class KeyFileStorageService {

    /*private final Path keyFileStorageLocation;*/

    @Value("${keyfile.upload-dir}")
    private String location;

    @Value("${keyfile.simpaisa-public-key-dir}")
    private String simpaisaKeyLocation;

    @Value("${keyfile.simpaisa-public-key-file}")
    private String simpaisaFileName;

    private final static String FILE_EXTENSION = ".pem";


    /*@Autowired
    public KeyFileStorageService(KeyFileStorageProperties keyFileStorageProperties){
        this.keyFileStorageLocation = Paths.get(keyFileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.keyFileStorageLocation);
        } catch (Exception ex) {
           // throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
            System.out.println("Could not create the directory where the uploaded files will be stored.");
            ex.printStackTrace();
        }
    }*/

    public Map<String, Object> storeFile(MultipartFile keyFile, long merchantId) throws IOException {

        Map<String, Object> response = null;


        //Normalize file name
        String originalFileName = StringUtils.cleanPath(keyFile.getOriginalFilename());
        String targetFileName = "";
        String fileExtension = ".pem";

        try {

            //Check if the file's name contains invalid characters
            if(originalFileName.contains("..")){
                response = Utility.getResponse(Responses.INVALID_FILE);
            }

            targetFileName = getMerchantPublicKeyFileName(merchantId);

            //Copy file to target location
            /*Path targetLocation = this.fileStorageLocation.resolve(fileName);*/
            Path targetLocation = Paths.get(location + targetFileName);

            long fileCopied = 0l;

             fileCopied = Files.copy(keyFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
           // System.out.println(new String(Files.readAllBytes(targetLocation)));

             if(fileCopied > 0){
                 response = Utility.getResponse(Responses.SUCCESS);
             }
             else {
                 response = Utility.getResponse(Responses.SYSTEM_ERROR);
             }
        }
        catch (Exception e){
            response = Utility.getResponse(Responses.SYSTEM_ERROR);
        }

        return response;
    }

    public String readPublicKey(long merchantId)
    {
        String response = "";
        Path targetLocation = Paths.get(location + getMerchantPublicKeyFileName(merchantId));
        try{
            response = new String(Files.readAllBytes(targetLocation));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;

    }

    public String readSimpaisaFile(){

        String response = "";
        Path targetLocation = Paths.get(simpaisaKeyLocation + simpaisaFileName);
        try{
            response = new String(Files.readAllBytes(targetLocation));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }

    private String getMerchantPublicKeyFileName(long merchantId)
    {
        return merchantId + "_" + "PublicKey" + FILE_EXTENSION;
    }

    public Resource downloadSimpaisaKey() {

        Resource resource = null;
        try{
            Path filePath = Paths.get(simpaisaKeyLocation + simpaisaFileName);
            resource = new UrlResource(filePath.toUri());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return resource;
    }
}
