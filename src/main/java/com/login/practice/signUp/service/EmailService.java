package com.login.practice.signUp.service;
import com.login.practice.security.EmailConfig;
import com.login.practice.signUp.entity.Token;
import com.login.practice.signUp.entity.User;
import com.login.practice.signUp.repository.TokenRepository;
import com.login.practice.signUp.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private EmailConfig mailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;


    public void sendVerificationEmail(User user, Token token){
        // Create a verification link with the token
        String verificationLink = "https://localhost:8080/verify?token=" + token.getToken();
        token.setToketype("Verification Email");
        tokenRepository.save(token);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(userRepository.findAdminUsers().iterator().next().getEmail());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Verify Your Email");
        mailMessage.setText("Click the following link to verify your email: " + verificationLink);

        mailSender.javaMailSender().send(mailMessage);

    }

    public void sendResetPassword(String email, Token token){
        // Create a verification link with the token
        String verificationLink = "https://localhost:8080/reset-password?token=" + token.getToken() + "&email=" + email;
        token.setToketype("Reset Password");
        tokenRepository.save(token);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(userRepository.findAdminUsers().iterator().next().getEmail());
        mailMessage.setTo(email);
        mailMessage.setSubject("Reset Your Password");
        mailMessage.setText("Click the following link to reset your password: " + verificationLink);

        mailSender.javaMailSender().send(mailMessage);

    }



}
