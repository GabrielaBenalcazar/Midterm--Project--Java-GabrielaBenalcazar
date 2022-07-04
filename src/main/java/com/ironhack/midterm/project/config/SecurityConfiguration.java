package com.ironhack.midterm.project.config;

import com.ironhack.midterm.project.models.user.UserRole;
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
                .antMatchers(HttpMethod.GET, "/account/*/balance").hasAnyRole(UserRole.ACCOUNT_HOLDER.toString(), UserRole.ADMIN.toString())
                .antMatchers(HttpMethod.PATCH, "/account/*/balance").hasRole(UserRole.ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/account/transfer").hasAnyRole(UserRole.ACCOUNT_HOLDER.toString(), UserRole.THIRD_PARTY.toString())
                .antMatchers(HttpMethod.POST, "/user/third-party").hasRole(UserRole.ADMIN.toString())
                .anyRequest().permitAll();

        return security.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
