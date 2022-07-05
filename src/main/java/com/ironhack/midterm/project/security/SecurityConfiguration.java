package com.ironhack.midterm.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


    @Configuration
    public class SecurityConfiguration {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
            security.httpBasic();
            security.csrf().disable();
            security.authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/account/*/balance").hasAnyRole("ACCOUNT_HOLDER", "ADMIN")
                    .antMatchers(HttpMethod.PATCH, "/account/*/balance").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/account/transfer").hasAnyRole("ACCOUNT_HOLDER", "THIRD_PARTY")
                    .antMatchers(HttpMethod.POST, "/user/third-party").hasRole("ADMIN")
                    .anyRequest().permitAll();

            return security.build();
        }

//            @Bean
//            public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//                return authenticationConfiguration.getAuthenticationManager();
//            }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    }
