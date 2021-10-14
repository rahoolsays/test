package com.simpaisa.portal.service;
import com.simpaisa.portal.config.security.CustomeUserDetailsService;
import com.simpaisa.portal.config.security.JwtTokenProvider;
import com.simpaisa.portal.email.EmailService;
import com.simpaisa.portal.entity.mongo.Privilege;
import com.simpaisa.portal.entity.mongo.Role;
import com.simpaisa.portal.entity.mongo.User;
import com.simpaisa.portal.entity.mysql.merchant.Merchant;
import com.simpaisa.portal.entity.mysql.product.Product;
import com.simpaisa.portal.entity.mysql.productconfiguration.ProductConfiguration;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.repository.interfaces.MerchantRepository;
import com.simpaisa.portal.repository.interfaces.RoleRepository;
import com.simpaisa.portal.repository.interfaces.UserRepository;
import com.simpaisa.portal.service.asyncmethod.AysncMethods;
import com.simpaisa.portal.service.email.EmailGenericService;
import com.simpaisa.portal.service.privilege.PrivilegeService;
import com.simpaisa.portal.service.product.ProductService;
import com.simpaisa.portal.service.productconfiguration.ProductConfigurationService;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.*;
@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CustomeUserDetailsService userDetailsService;
    @Autowired
    private AysncMethods aysncMethods;
    @Autowired
    EmailService emailService;
    @Autowired
    private EmailGenericService emailGenericService;
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConfigurationService productConfigurationService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PrivilegeService privilegeService;
    public Map<String , Object> login(HashMap<String, String> data){
        Map<String, Object> response = null;
        String role = null;
        try {
            if (data.containsKey(Utility.EMAIL) && data.get(Utility.EMAIL).trim().length() > 0) {
                if(data.containsKey(Utility.PASSWORD) && data.get(Utility.PASSWORD).trim().length()> 0) {
                    String email = data.get(Utility.EMAIL);
                    User userExists = userDetailsService.findByEmail(email);
                    if(userExists == null){
                        return getResponse(Responses.INVALID_EMAIL);
                    }
                    String token = null;
                    try {
                        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.get(Utility.PASSWORD)));
                        token  = jwtTokenProvider.createToken(email, userRepository.findByEmail(email).getRoles());
                        if(!userExists.isVerified()){
                            return getResponse(Responses.USER_NOT_VERIFIED);
                        }
                    }catch (BadCredentialsException ex){
                        return getResponse(Responses.INVALID_CREDENTIAL);
                    }
                    response = getResponse(Responses.SUCCESS);
                    response.put(Utility.EMAIL, email);
                    response.put(Utility.TOKEN, token);
                    List<Privilege> privileges = new ArrayList<>();
                    for(Role r : userExists.getRoles()){
                        role = r.getRole();
                        if(role.equals(Utility.ROLE_ADMIN))
                        {
                            privileges.addAll(privilegeService.getAdminDefaultPrivileges());
                        }
                        else if(role.equals(Utility.ROLE_MERCHANT))
                        {
                            privileges.addAll(privilegeService.getMerchantDefaultPrivileges());
                        }
                        else {
                            privileges.addAll(r.getPrivileges());
                        }
                        break;
                    }
                    response.put(Utility.ROLE,role);
                    response.put(Utility.PRIVILEGES, privileges);
                }else {
                    response = getResponse(Responses.INVALID_CREDENTIAL);
                }
            } else {
                response = getResponse(Responses.INVALID_EMAIL);
            }
        }catch (AuthenticationException ex){
            //throw  new BadCredentialsException("Invalid email / password");
            response = getResponse(Responses.SYSTEM_ERROR);
            ex.printStackTrace();
        }
        return response;
    }
//    public Map<String, Object> register(User user){
//        try {
//            User userExists = userDetailsService.findByEmail(user.getEmail());
//            if (userExists != null) {
//                return getResponse(Responses.USER_EXISTS);
//            }
//            long websiteCount = userDetailsService.countByWebsite(user.getWebsite());
//            if (websiteCount > 0) {
//                return getResponse(Responses.WEBSITE_REGISTERED);
//            }
//            long businessNameCount = userDetailsService.countByBusinessName(user.getBusinessName());
//            if (businessNameCount > 0) {
//                return getResponse(Responses.BUSINESS_REGISTERED);
//            }
////        Set<Role> roles = new HashSet<>();
////        Role role = roleRepository.findByRole(Utility.ROLE_MERCHANT);
////        roles.add(role);
////        user.setRoles(roles);
//            User savedUser = userDetailsService.saveUser(user);
//            //send email
//            if(savedUser!=null){
//                if(savedUser.getId()!=null){
//                    try{
//                        //add merchant
//                        merchantRepository.insert(getMerchant(user), Utility.JDBC_STAGING);
//                        //send email
//                        sendEmail(savedUser, Utility.VERIFICATION_EMAIL_TEMPLATE);
//                        sendEmail(savedUser, Utility.WELCOME_EMAIL_TEMPLATE);
//                    }catch (Exception ex){
//                        ex.printStackTrace();
//                    }
//                }
//            }
//            return getResponse(Responses.SUCCESS);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return getResponse(Responses.SYSTEM_ERROR);
//    }


    public Map<String, Object> register(User user){
        {
            try {
                User userExists = userDetailsService.findByEmail(user.getEmail());
                if (userExists != null) {
                    return getResponse(Responses.USER_EXISTS);
                }
                long websiteCount = userDetailsService.countByWebsite(user.getWebsite());
                if (websiteCount > 0) {
                    return getResponse(Responses.WEBSITE_REGISTERED);
                }
                long businessNameCount = userDetailsService.countByBusinessName(user.getBusinessName());
                if (businessNameCount > 0) {
                    return getResponse(Responses.BUSINESS_REGISTERED);
                }
//        Set<Role> roles = new HashSet<>();
//        Role role = roleRepository.findByRole(Utility.ROLE_MERCHANT);
//        roles.add(role);
//        user.setRoles(roles);
                User savedUser = userDetailsService.saveUser(user);
                //send email
                if(savedUser!=null){
                    if(savedUser.getId()!=null){
                        try{
                            //add merchant
                            long isSaved = merchantRepository.insert(getMerchant(user), Utility.JDBC_STAGING);
                            //add default product
                            if(isSaved > 0)
                            {
                                //If default entering of merchant already exist
                           /* Product prod = productRepository.findProductByMerchantId(isSaved);
                            if(prod.getProductName().equals("default"))
                                System.out.print("Product already exist");
                            else */
                                Product SaveDefProduct = productService.save(getProduct(isSaved));
                                if(SaveDefProduct != null)
                                {

                                    ProductConfiguration SavedProdConfig =new ProductConfiguration();

                                    for(int i = 0; i < Utility.OPERATORID.length; i++)
                                    {
                                        ProductConfiguration productConfig = new ProductConfiguration();
                                        productConfig.setActiveHe(true);
                                        productConfig.setActiveWifi(true);
                                        productConfig.setOperatorId(Utility.OPERATORID[i]);
                                        productConfig.setProductId(SaveDefProduct.getProductID());
                                        productConfig.setActiveBucket(false);
                                        productConfig.setActiveReminder(false);
                                        productConfig.setActiveRetry(true);
                                        productConfig.setActivefreeTrial(false);

                                        SavedProdConfig = productConfigurationService.save(productConfig);


                                    }
                                    // if(SavedProdConfig !=null)
                                    //  {
                                    //      System.out.print("Product Configurations stored successfully");
                                    //  }
                                }
                            }

                            //send email
                            sendEmail(savedUser, Utility.VERIFICATION_EMAIL_TEMPLATE);
                            sendEmail(savedUser, Utility.WELCOME_EMAIL_TEMPLATE);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
                return getResponse(Responses.SUCCESS);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return getResponse(Responses.SYSTEM_ERROR);
        }
    }


    private Product getProduct(long merchantId){
        {
            Product product = new Product();
            try{
                product.setActive(true);
                product.setAmount(3.0);
                product.setDescription("This is a demo weekly product for PKR3");
                product.setDaysInterval(7);
                product.setMerchantId(merchantId);
                product.setProductName(Utility.DEFAULT_PRODUCT);
                product.setStatus(2);
                product.setCategory(3L);
                product.setResponseUrl("https://owais.requestcatcher.com/");
                product.setFailureUrl("https://owais.requestcatcher.com/");
                product.setSuccessUrl("https://owais.requestcatcher.com/");
                product.setOnLive(0);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            return product;


        }
    }
    public Map<String, Object> resendEmail(HashMap<String, String> data){
        try {
            if(data.containsKey(Utility.EMAIL) && data.get(Utility.EMAIL).trim().length()>0) {
                User userExists = userDetailsService.findByEmail(data.get(Utility.EMAIL));
                if (userExists != null) {
                    if (userExists.isVerified()) {
                        return getResponse(Responses.ALREADY_VERIFIED);
                    }
                    sendEmail(userExists, Utility.VERIFICATION_EMAIL_TEMPLATE);
                    return getResponse(Responses.SUCCESS);
                } else {
                    return getResponse(Responses.INVALID_EMAIL);
                }
            }else {
                return getResponse(Responses.INVALID_EMAIL);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return getResponse(Responses.SYSTEM_ERROR);
        }
    }




    public Map<String, Object> changePassword(HashMap<String, String> data){
        String resetUrl = null;
        HashMap<String, String> emailBody = null;
        try {
            if(data.containsKey(Utility.CHANGE_PASSWORD_TOKEN) && data.get(Utility.CHANGE_PASSWORD_TOKEN).trim().length()>0) {

                if(data.containsKey(Utility.PASSWORD) && data.get(Utility.PASSWORD).trim().length()>0) {
                    User userExists = userDetailsService.findByToken(data.get(Utility.CHANGE_PASSWORD_TOKEN));
                    if (userExists != null) {
                        if (!userExists.isVerified()) {
                            return getResponse(Responses.USER_NOT_VERIFIED);
                        }
                        if (!userExists.isEnabled()) {
                            return getResponse(Responses.USER_INACTIVE);
                        }

                        LocalDateTime tokenDateTime = userExists.getResetTokenTime();
                        if(Utility.isPasswordTokenExpired(tokenDateTime)){
                            return getResponse(Responses.TOKEN_EXPIRED);
                        }

                        //reset password
                        userExists.setPassword(passwordEncoder.encode(data.get(Utility.PASSWORD)));
                        userExists.setResetToken(null);
                        userExists.setResetTokenTime(null);
                        userRepository.save(userExists);

                        return getResponse(Responses.SUCCESS);
                    } else {
                        return getResponse(Responses.INVALID_TOKEN);
                    }
                }else {
                    return getResponse(Responses.PASSWORD_EMPTY);
                }
            }else {
                return getResponse(Responses.INVALID_TOKEN);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return getResponse(Responses.SYSTEM_ERROR);
        }
    }


    public Map<String, Object> forgotPassword(HashMap<String, String> data){
        String resetUrl = null;
        HashMap<String, String> emailBody = null;
        try {
            if(data.containsKey(Utility.EMAIL) && data.get(Utility.EMAIL).trim().length()>0) {
                User userExists = userDetailsService.findByEmail(data.get(Utility.EMAIL));
                if (userExists != null) {
                    if (!userExists.isVerified()) {
                        return getResponse(Responses.USER_NOT_VERIFIED);
                    }if (!userExists.isEnabled()){
                        return getResponse(Responses.USER_INACTIVE);
                    }

                    userExists.setResetToken(Utility.generatePasswordResetToken());
                    userExists.setResetTokenTime(LocalDateTime.now());

                    userRepository.save(userExists);
                    //send reset email
                    resetUrl = Utility.PORTAL_BASE_URL + "#/change-password/" + userExists.getResetToken();
                    emailBody = new HashMap<String, String>();

                    emailBody.put(Utility.EMAIL, userExists.getEmail());
                    emailBody.put(Utility.EMAIL_TYPE, Utility.EMAIL_TYPE_PASSWORD_RESET);
                    emailBody.put(Utility.PASSWORD_RESETURL, resetUrl);

                    emailGenericService.send(emailBody);

                    return getResponse(Responses.SUCCESS);
                } else {
                    return getResponse(Responses.INVALID_EMAIL);
                }
            }else {
                return getResponse(Responses.INVALID_EMAIL);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return getResponse(Responses.SYSTEM_ERROR);
        }
    }



    private Map<String, Object> getResponse(Responses responses){
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(Utility.STATUS, responses.getStatus());
        response.put(Utility.MESSAGE, responses.getMessage());
        return response;
    }
    public void sendEmail(User user,String emailTemplate){
        String verificationUrl = null;
        Map<String, Object> map = new HashMap<>();
        try {
            if(emailTemplate.equals(Utility.VERIFICATION_EMAIL_TEMPLATE)) {
                verificationUrl = Utility.BASE_URL + "simpaisa/template/verifyEmail/" + user.getId();
                map.put("verificationUrl", verificationUrl);
                map.put("title", user.getTitle());
                map.put("firstname", user.getFirstName());
                map.put("lastname", user.getLastName());
                emailService.sendMailWithTemplate(map, Utility.SUBJECT_VERIFICATION, user.getEmail(), Utility.VERIFICATION_EMAIL_TEMPLATE);
            }else if(emailTemplate.equals(Utility.WELCOME_EMAIL_TEMPLATE)){
                emailService.sendMailWithTemplate(map, Utility.SUBJECT_WELCOME, user.getEmail(), Utility.WELCOME_EMAIL_TEMPLATE);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private Merchant getMerchant(User user){
        Merchant merchant = null;
        try{
            merchant = new Merchant();
            merchant.setEmail(user.getEmail());
            merchant.setFirstName(user.getFirstName());
            merchant.setLastName(user.getLastName());
            merchant.setPostbackUrl("https://owais.requestcatcher.com/");
            merchant.setTitle(user.getTitle());
            merchant.setThreshold(100);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return merchant;
    }
}
