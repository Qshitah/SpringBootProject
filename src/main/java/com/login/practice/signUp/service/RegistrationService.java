package com.login.practice.signUp.service;

import com.login.practice.signUp.entity.Role;
import com.login.practice.signUp.entity.Token;
import com.login.practice.signUp.entity.User;
import com.login.practice.signUp.repository.RoleRepository;
import com.login.practice.signUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void registerUser(User user){

        int roleClientId = 3;
        Optional<Role> clientOptional = roleRepository.findById(roleClientId);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        if (clientOptional.isPresent()){
            user.setRoles(List.of(clientOptional.get()));
        }else {
            user.setRoles(List.of(new Role("ROLE_CLIENT")));
        }
        user.setEnabled(0);

        userRepository.save(user);

        // Generate a verification token for the user
        Token token = tokenService.generateToken(user);

        // Send the verification email
        emailService.sendVerificationEmail(user, token);



    }




}
