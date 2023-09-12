package com.login.practice.security;

import com.login.practice.signUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;
    private final DataSource dataSource; // Inject the DataSource

    public SecurityConfig(UserRepository userRepository,DataSource dataSource) {
        this.userRepository = userRepository;
        this.dataSource = dataSource;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // Define a bean for CustomAuthenticationFailureHandler
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler(userRepository);
    }




    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        //define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select username,password,enabled from users where username=?");

        //define query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.username, r.name FROM user_roles ur " +
                "INNER JOIN users u ON ur.user_id = u.id " +
                "INNER JOIN role r ON ur.role_id = r.id " +
                "WHERE u.username = ?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/public/**").permitAll()
                                .requestMatchers("/signup/**","/authenticated/**"
                                        ,"/verify/**","/forgot-password/**","/reset-password/**").permitAll()// Assuming your CSS files are in a "css" directory.
                                .requestMatchers("/leaders/**").hasRole("MANAGER")
                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/authenticated")
                        .loginProcessingUrl("/authenticateExistingUser")// URL for existing users to log in
                        .defaultSuccessUrl("/")
                        .failureHandler(customAuthenticationFailureHandler())
                        .permitAll()
                ).rememberMe(remember -> remember
                        .rememberMeParameter("flexSwitchCheckChecked")
                        .userDetailsService(userDetailsManager(dataSource))
                        .tokenValiditySeconds(604800) // 7 days
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/authenticated?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults());



        return httpSecurity.build();

    }

}
