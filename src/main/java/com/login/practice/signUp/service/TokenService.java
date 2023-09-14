package com.login.practice.signUp.service;

import com.login.practice.signUp.entity.Token;
import com.login.practice.signUp.entity.User;
import com.login.practice.signUp.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Token generateToken(User user){
        Token token = new Token();

        token.setToken(generateUniqueToken());
        // Set the expiration time, for example , one hour from now
        token.setExpirationDateTime(LocalDateTime.now().plusHours(1));
        token.setUser(user);

        return  tokenRepository.save(token);

    }

    private String generateUniqueToken() {
        //Generate a unique token
        return UUID.randomUUID().toString();
    }

    public boolean isTokenExpired(Token verificationToken) {
        // Implement your token expiration logic here
        // You can compare the token's expiration time with the current time
        LocalDateTime expirationDateTime = verificationToken.getExpirationDateTime();
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expirationDateTime.isBefore(currentDateTime);
    }


}
