package com.login.practice.signUp.service;

import com.login.practice.signUp.entity.Token;
import com.login.practice.signUp.entity.User;
import com.login.practice.signUp.repository.TokenRepository;
import com.login.practice.signUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public boolean verifyUser(String token){
        //Find the token in the database
        Token verificationToken = tokenRepository.findByToken(token);

        if (verificationToken != null){
            if (!tokenService.isTokenExpired(verificationToken)){
                //update the user's status to enabled
                User user = verificationToken.getUser();
                user.setEnabled(1);

                //Save the updated user
                userRepository.save(user);

                //Delete the verification token or mark it as used, depending on your requirements
                tokenRepository.deleteById(verificationToken.getId());

                return true; //User Verified Successfully
            }
            
        }
        return false; //Invalid or expired token

    }


    @Transactional
    public boolean resetPassword(String email, String password,String token) {
        //Find if email exists
        User user = userRepository.findByEmail(email);
        //Find the token in the database
        Token verificationToken = tokenRepository.findByToken(token);
        if(user != null && verificationToken != null) {
            if (!tokenService.isTokenExpired(verificationToken)){
                // Encode the password before saving it to the database
                String encodedPassword = passwordEncoder.encode(password);

                // Add the "{bcrypt}" prefix to the encoded password
                user.setPassword(encodedPassword);
                userRepository.save(user);
                tokenRepository.delete(verificationToken);


                return true;
            }
        }

        return false;

    }
}
