package com.login.practice;

import com.login.practice.Entity.ParentCategory;
import com.login.practice.repository.CategoryRepository;
import com.login.practice.signUp.repository.TokenRepository;
import com.login.practice.signUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
public class LoginPracticeApplication {

	@Autowired
	private CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(LoginPracticeApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(){

		return runner -> {

		};

	}

}
