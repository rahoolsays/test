package com.simpaisa.portal.service.asyncmethod;

import com.simpaisa.portal.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AysncMethods {

    @Autowired
    private EmailService emailService;
    @Async("threadPoolTaskExecutor")

    public void sendEmail(Map<String, Object> model, String subject, String to, String emailTemplate){
        emailService.sendMailWithTemplate(model, subject, to, emailTemplate);
    }
}
