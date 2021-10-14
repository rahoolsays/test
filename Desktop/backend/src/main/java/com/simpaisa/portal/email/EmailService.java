package com.simpaisa.portal.email;


import com.simpaisa.portal.utility.Utility;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

//manages the emails
@Service
public class EmailService {
	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	Configuration config;

	// Sends all the lists (recursions, PiadUsers...) to email-template to parse,
	// then sends email with that template to all users.

	public String sendMailWithTemplate(Map<String, Object> model, String subject, String to, String emailTemplate) {
		
		
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Template template = config.getTemplate(emailTemplate);
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setTo(to);
			helper.setFrom(Utility.FROM);
			helper.setText(html, true);
			helper.setSubject(subject);

			javaMailSender.send(message);
			
//			boolean result = Files.deleteIfExists(Paths.get(dir+"/transaction.txt"));
//
//			if (result) {
//				System.out.println("Deleted the file: " + file.getName());
//				return "success";
//			} else {
//				System.out.println("Failed to delete the file.");
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
