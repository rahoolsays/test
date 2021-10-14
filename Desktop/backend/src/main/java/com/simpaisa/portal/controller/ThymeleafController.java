package com.simpaisa.portal.controller;

import com.simpaisa.portal.entity.mongo.User;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/simpaisa/template")
public class ThymeleafController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/verifyEmail/{id}")
    public String verifyEmail(@PathVariable(name = "id") String id, Model model){
        System.out.println("id = " + id);
        User user = null;
        try{
            user = mongoTemplate.findById(id, User.class);
            if(user!=null){
                if(!user.isVerified()) {
                    user.setVerified(true);
                    mongoTemplate.save(user);
                    model.addAttribute("loginUrl", Utility.LOGIN_URL);
                    return "verify_success";
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();

        }

        return "verify_fail";
    }
}
