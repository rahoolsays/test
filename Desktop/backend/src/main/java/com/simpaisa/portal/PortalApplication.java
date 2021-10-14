package com.simpaisa.portal;

import com.mysql.cj.protocol.x.AsyncMessageReader;
import com.simpaisa.portal.config.keyfilestorage.KeyFileStorageProperties;
import com.simpaisa.portal.config.security.CustomeUserDetailsService;
import com.simpaisa.portal.email.EmailService;
import com.simpaisa.portal.entity.mongo.revenue.RevenueShare;
import com.simpaisa.portal.repository.interfaces.*;
import com.simpaisa.portal.service.asyncmethod.AysncMethods;

import com.simpaisa.portal.service.revenue.RevenueShareService;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.*;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({
		KeyFileStorageProperties.class
})
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class PortalApplication implements CommandLineRunner {

	@Autowired
	private CustomeUserDetailsService userDetailsService;

//	@Autowired
//	RoleRepository roleRepository;
//	@Autowired
//	private CustomeUserDetailsService userDetailsService;
//	@Autowired
//	EmailService emailService;
//	@Autowired
//	AysncMethods aysncMethods;
//	@Autowired
//	MongoTemplate mongoTemplate;
	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}

	@Autowired
	AysncMethods aysncMethods;
//	@Autowired
//	MerchantRepository merchantRepository;
//
//	@Autowired
//	KycRepository kycRepository;
//	@Autowired
//	TransactionRepository transactionRepository;
//	@Autowired
//	RevenueSharesRepository revenueSharesssRepository;
//
//	@Autowired
//	RevenueShareService revenueShareService;

//	@Autowired
//	MerchantRepository merchantRepository;

	@Autowired
	ProductRepository productRepository;
	@Override
	public void run(String... args) throws Exception {

//		System.out.println(productRepository.findByMerchantId(1000004));
//		System.out.println(merchantRepository.getAllMerchants());

//		for(int i=0;i<10;i++) {
//			RevenueShare revenueShare = new RevenueShare();
//
//			revenueShare.setCountry("Pakistan");
//			revenueShare.setCreatedDate(new Date());
//			revenueShare.setCurrency("PKR");
//			revenueShare.setEndUserPrice(300);
//			revenueShare.setOperator("Easypaisa");
//			revenueShare.setPayout(300);
//			revenueShare.setPayoutCycle(2);
//			revenueShare.setPayoutPercent(300);
//			revenueShare.setPriceExVat(301);
//
//
//			revenueSharesssRepository.save(revenueShare);
//		}

		//System.out.println(revenueShareService.findAll("createdDate", "DESC",0,10));



		//System.out.println("findByTransactionId =" + transactionRepository.findByTransactionId(5497764));
//		KYC kyc = new KYC();
//		kyc.setId("60adfbfde583bc1a80fa3c6d");
//		Map<String, Boolean> services = new HashMap<>();
//		services.put("DCB", true);
//		services.put("WALLETS", true);
//		kyc.setServices(services);
//
//
//	//	BusinessPoc businessPoc = new BusinessPoc();
//
//		KYC kyc1 = kycRepository.save(kyc);
//		System.out.println(kyc1);
		//System.out.println("merchant == "  + merchantRepository.findByEmail("pawan@simpaisa.com"));

	//	System.out.println("byId  = " + mongoTemplate.findById("607ae3900a0ec16d532c1c56", User.class));
	//	aysncMethods.sendEmail(new HashMap<>(), Utility.SUBJECT_WELCOME, "pawan@simpaisa.com", Utility.WELCOME_EMAIL_TEMPLATE);
		//System.out.println("emailTemplate == " +  ;);
//		User user = new User();
//		user.setPassword("admin");
//		user.setEmail("admin@gmail.com");
//		user.setEnabled(true);
//		user.setBusinessName("bsda");
//		user.setWebsite("");
//
//		userDetailsService.saveUser(user);
		//		Role role = new Role();
//		role.setRole(Utility.ROLE_MERCHANT);
//		roleRepository.saveRole(role);
//
//		Role role1 = new Role();
//		role1.setRole(Utility.ROLE_ADMIN);
//		roleRepository.saveRole(role1);
	}




}
