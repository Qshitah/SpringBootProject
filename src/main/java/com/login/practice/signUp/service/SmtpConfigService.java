package com.login.practice.signUp.service;

import com.login.practice.signUp.entity.SmtpConfig;
import com.login.practice.signUp.entity.User;
import com.login.practice.signUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SmtpConfigService {

    @Autowired
    private UserRepository userRepository;

    public SmtpConfig loadSmtpConfigForAdmin(){
        Set<User> adminUsers = userRepository.findAdminUsers();
        if(adminUsers!= null){
            User adminUser = adminUsers.iterator().next();
                // Retrieve SMTP settings from adminUser and return as SmtpConfig object
                SmtpConfig smtpConfig = new SmtpConfig();
                smtpConfig.setHost(adminUser.getSmtpConfig().getHost());
                smtpConfig.setPort(adminUser.getSmtpConfig().getPort());
                smtpConfig.setUsername(adminUser.getSmtpConfig().getUsername());
                smtpConfig.setPassword(adminUser.getSmtpConfig().getPassword());

            return smtpConfig;

        }

        // Handle the case where no admin user with SMTP settings is found
        return null;
    }
}
