package com.example.antifraudsystem.config;

import com.example.antifraudsystem.RestAuthenticationEntryPoint;
import com.example.antifraudsystem.enums.UserRole;
import com.example.antifraudsystem.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    public SecurityConfiguration(RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz ->
                        authz
                                .requestMatchers("/actuator/shutdown").permitAll() // needs to run test
                                .requestMatchers("/api/auth/user").permitAll()
                                .requestMatchers("/api/antifraud/suspicious-ip/**").permitAll()
                                .requestMatchers("/api/antifraud/transaction").hasRole(UserRole.MERCHANT.name())
                                .requestMatchers("/api/auth/user/*", "/api/auth/access","/api/auth/role").hasAnyRole(UserRole.ADMINISTRATOR.name())
                                .requestMatchers("/api/auth/list").hasAnyRole(UserRole.ADMINISTRATOR.name(), UserRole.SUPPORT.name())

                )
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
                .and()
                .authorizeHttpRequests() // manage access
                .and()
                .httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint); // Handles auth error;

        return http.build();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}
