package com.login.practice.security;

import com.login.practice.signUp.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler  implements AuthenticationFailureHandler {

    private UserRepository userRepository;

    public CustomAuthenticationFailureHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // Get the username from the request
        String username = request.getParameter("username");


        // Get the current session or create a new one
        HttpSession session = request.getSession();

        if (userRepository.findByUsername(username) == null) {
            session.setAttribute("notExistsUsername", username);
        }else{
            if (userRepository.findByUsername(username).getEnabled() == 0){
                session.setAttribute("notVerified", false);
            }
        }

        session.setAttribute("failedSession", username);

        System.out.println("fck");


        response.sendRedirect("/authenticated"); // Redirect to the failure URL
    }
}
