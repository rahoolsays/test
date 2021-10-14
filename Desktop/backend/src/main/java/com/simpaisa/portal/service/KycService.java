package com.simpaisa.portal.service;

import com.simpaisa.portal.entity.mongo.kyc.KYC;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.exception.MyFileNotFoundException;
import com.simpaisa.portal.model.kyc.legalstructure.Partnership;
import com.simpaisa.portal.model.kyc.legalstructure.PrivateLimited;
import com.simpaisa.portal.model.kyc.legalstructure.PublicLimited;
import com.simpaisa.portal.repository.interfaces.KycRepository;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KycService {

    @Autowired
    KycRepository kycRepository;

    @Value("${document.upload-dir}")
    String location;

    public KYC save(KYC kyc){
       KYC  kycResult = null;
        try{

         if(kyc!=null) {
             if (kyc.getId() != null) {
                 kyc.setUpdatedDate(new Date());
             } else {
                 kyc.setCreatedDate(new Date());
                 kyc.setUpdatedDate(new Date());
             }
         }

        kycResult = kycRepository.save(kyc);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return kycResult;

    }

    public KYC findByEmail(HashMap<String, String> data) {
        KYC kyc = null;
        try{
            if(data.containsKey(Utility.EMAIL)){
                kyc = kycRepository.findByEmail(data.get(Utility.EMAIL));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kyc;
    }

    public KYC findById(String id) {
        KYC kyc = null;
        try{
            kyc = kycRepository.findById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return kyc;
    }

    public Map<String, Object> uploadDocuments(List<MultipartFile> multipartFiles, Long merchantId, String legalStructure) {

        Map<String, Object> response = null;
        HashMap<String, Integer> fileUploadedName = new HashMap<String, Integer>();
        HashMap<String, Integer> fileUploadedNameWithoutExtention = new HashMap<String, Integer>();

        String mainDir = null;
        try{

            String merchantDir = location + merchantId;

            File dir = new File(merchantDir);
            if(!dir.exists()) dir.mkdirs();
            if(legalStructure.equalsIgnoreCase("Partnership")){

                mainDir = merchantDir + "/" + "Partnership";
            }else if(legalStructure.equalsIgnoreCase("PrivateLimited")){
                mainDir = merchantDir + "/" + "PrivateLimited";
            }else if(legalStructure.equalsIgnoreCase("PublicLimited")){
                mainDir = merchantDir + "/" + "PublicLimited";
            }

            System.out.println("mainDir " + mainDir);
            File targetDir = new File(mainDir);
            if(!targetDir.exists()) targetDir.mkdirs();



            for(MultipartFile multipartFile: multipartFiles){

                System.out.println("multipartFileName = " + multipartFile.getOriginalFilename());
                fileUploadedName.put(multipartFile.getOriginalFilename(), 1);
                fileUploadedNameWithoutExtention.put(multipartFile.getOriginalFilename().split("\\.")[0], 1);
                Path target = Paths.get(targetDir +"/" + multipartFile.getOriginalFilename());
                Files.copy(multipartFile.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            }

            //delete repeat name file from folder
            File[] filesInForlder = targetDir.listFiles();
            for(File file: filesInForlder){
                if(file.isFile()){
                    if(!fileUploadedName.containsKey(file.getName())){
                        try {
                            String fileName[] = file.getName().split("\\.");
                            System.out.println("file.getName().split(\".\")[0]==" +fileName[0] );
                            if(fileUploadedNameWithoutExtention.containsKey(fileName[0])) {
                                file.delete();
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                        System.out.println("fileName = " + file.getName());
                    }

                }
            }
            response = Utility.getResponse(Responses.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            response = Utility.getResponse(Responses.SYSTEM_ERROR);
        }

        return response;
    }

    public ResponseEntity<?> getDocuments(Long merchantId, String legalStructure) {
        ResponseEntity<?> response = null;
        Partnership partnership = null;
        PrivateLimited privateLimited = null;
        PublicLimited publicLimited = null;
        String mainDir = null;
        try{

            String merchantDir = location + merchantId;

            File dir = new File(merchantDir);

            if(legalStructure.equalsIgnoreCase("Partnership")){

                mainDir = merchantDir + "/" + "Partnership";
                partnership = new Partnership();
            }else if(legalStructure.equalsIgnoreCase("PrivateLimited")){
                mainDir = merchantDir + "/" + "PrivateLimited";
                privateLimited = new PrivateLimited();
            }else if(legalStructure.equalsIgnoreCase("PublicLimited")){
                mainDir = merchantDir + "/" + "PublicLimited";
                publicLimited = new PublicLimited();
            }

            File targetDir = new File(mainDir);

            File[] filesInForlder = targetDir.listFiles();
            if(filesInForlder!=null && filesInForlder.length>0)
            for(File file: filesInForlder){
                if(file.isFile()){
                    if(legalStructure.equalsIgnoreCase("Partnership")){
                     getPartnerShip(file.getName(), partnership);

                    }else if(legalStructure.equalsIgnoreCase("PrivateLimited")){
                        getPrivateLimited(file.getName(), privateLimited);
                    }else if(legalStructure.equalsIgnoreCase("PublicLimited")){
                        getPublicLimited(file.getName(), publicLimited);
                    }
                }
            }

            if(legalStructure.equalsIgnoreCase("Partnership")){
                if(partnership!=null) partnership.setUploaded(true);
                return new ResponseEntity<Partnership>(partnership, HttpStatus.OK);

            }else if(legalStructure.equalsIgnoreCase("PrivateLimited")){
                if(privateLimited!=null) privateLimited.setUploaded(true);
                return new ResponseEntity<PrivateLimited>(privateLimited, HttpStatus.OK);
            }else if(legalStructure.equalsIgnoreCase("PublicLimited")){
                if(publicLimited!=null) publicLimited.setUploaded(true);
                return new ResponseEntity<PublicLimited>(publicLimited, HttpStatus.OK);
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }





    public ResponseEntity<Resource> downloadFile( String fileName,  Long merchantId, String legalStructure, HttpServletRequest request) {
        // Load file as Resource

        String fileLocation = location + merchantId+ "/" + legalStructure;
        Resource resource = loadFileAsResource(fileName,fileLocation);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {

        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }



    public Resource loadFileAsResource(String fileName, String fileLocation) {
        try {
            Path fileStorageLocation;
            fileStorageLocation = Paths.get(fileLocation)
                    .toAbsolutePath().normalize();
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    Partnership getPartnerShip(String fileName, Partnership partnership){

        try{

            if(fileName!=null){
                if(fileName.contains(Utility.CNIC_FRONT)){
                    partnership.setCnicFront(fileName);
                }else if(fileName.contains(Utility.CNIC_BACK)){
                    partnership.setCnicBack(fileName);
                }else if(fileName.contains(Utility.AUTH_LETTER)){
                    partnership.setAuthorityLetter(fileName);
                }else if(fileName.contains(Utility.MERCHANT_AGREEMENT)){
                    partnership.setMerchantAgreement(fileName);
                }else if(fileName.contains(Utility.NTN_CERTIFICATE)){
                    partnership.setNtnCertificate(fileName);
                }else if(fileName.contains(Utility.PARTNERSHIP_DEED)){
                    partnership.setPartnershipDeed(fileName);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return partnership;
    }


     PrivateLimited getPrivateLimited(String fileName, PrivateLimited privateLimited){

        try{

            if(fileName!=null){
                if(fileName.contains(Utility.CNIC_FRONT)){
                    privateLimited.setCnicFront(fileName);
                }else if(fileName.contains(Utility.CNIC_BACK)){
                    privateLimited.setCnicBack(fileName);
                }else if(fileName.contains(Utility.ARTICLE_OF_ASSOCIATION)){
                    privateLimited.setArticleOfAssociation(fileName);
                }else if(fileName.contains(Utility.FORM_A)){
                    privateLimited.setFormA(fileName);
                }else if(fileName.contains(Utility.NTN_CERTIFICATE)){
                    privateLimited.setNtnCertificate(fileName);
                }else if(fileName.contains(Utility.INCORPORATION_CERTIFCATE)){
                    privateLimited.setIncorporationCetificate(fileName);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return privateLimited;
    }

    PublicLimited getPublicLimited(String fileName, PublicLimited publicLimited){

        try{

            if(fileName!=null){
                if(fileName.contains(Utility.CNIC_FRONT)){
                    publicLimited.setCnicFront(fileName);
                }else if(fileName.contains(Utility.CNIC_BACK)){
                    publicLimited.setCnicBack(fileName);
                }else if(fileName.contains(Utility.ACCOUNT_MAINTANANCE_CERTIFCATE)){
                    publicLimited.setAccountMaintenanceCetificate(fileName);
                }else if(fileName.contains(Utility.LAST_3_MONTH_BILL)){
                    publicLimited.setLast3MonthBill(fileName);
                }else if(fileName.contains(Utility.NTN_CERTIFICATE)){
                    publicLimited.setNtnCertificate(fileName);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return publicLimited;
    }

    public Page<KYC> findAllInReview(int step, String orderBy, String direction, int pageNo, int size) {
        Page<KYC> result = null;
        Pageable pageable  = null;

        try{
            if(direction.equalsIgnoreCase(Utility.SQL_DESC)){
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, orderBy));
            }else {
                pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.ASC, orderBy));
            }

            result = kycRepository.findAllInReview(step, pageable);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
