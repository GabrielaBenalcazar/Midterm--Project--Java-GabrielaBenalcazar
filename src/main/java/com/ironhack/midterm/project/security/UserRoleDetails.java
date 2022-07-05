package com.ironhack.midterm.project.security;

import com.ironhack.midterm.project.models.user.Role;
import com.ironhack.midterm.project.models.user.ThirdParty;
import com.ironhack.midterm.project.models.user.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserRoleDetails implements UserDetails {

    private User user;

    public UserRoleDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for(Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        }


        return authorities;
    }

    @Override
    public String getPassword() {
        Role role = new Role("THIRD_PARTY");
        if (user.getRoles().contains(role) ) {
            return ((ThirdParty)user).getPassword();
        } else {
            return null;
        }
    }

    @Override
    public String getUsername() {
        return this.user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<Role> getRoles() {
        return this.user.getRoles();
    }
    public Long getId() {
        return this.user.getId();
    }

    public User getUser() { return this.user; }


}
