package com.login.practice.security;

import com.login.practice.signUp.entity.SmtpConfig;
import com.login.practice.signUp.service.SmtpConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

    @Autowired
    private SmtpConfigService smtpConfigService;
    @Bean
    public JavaMailSender javaMailSender() {
        SmtpConfig smtpSettings = smtpConfigService.loadSmtpConfigForAdmin();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Configure the mailSender with SMTP settings
        mailSender.setHost(smtpSettings.getHost());
        mailSender.setPort(smtpSettings.getPort());
        mailSender.setUsername(smtpSettings.getUsername());
        mailSender.setPassword(smtpSettings.getPassword());

        // Enable TLS or SSL
        mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true"); // Enable STARTTLS



        // Additional mail sender configuration (TLS, SSL, etc.) can go here if needed

        return mailSender;
    }

}
