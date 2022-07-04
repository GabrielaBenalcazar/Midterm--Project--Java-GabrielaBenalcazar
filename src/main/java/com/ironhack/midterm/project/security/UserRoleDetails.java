package com.ironhack.midterm.project.security;

import com.ironhack.midterm.project.models.user.ThirdParty;
import com.ironhack.midterm.project.models.user.User;
import com.ironhack.midterm.project.models.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().toString()));

        return authorities;
    }

    @Override
    public String getPassword() {
        if (user.getRole() == UserRole.THIRD_PARTY) {
            return ((ThirdParty)user).getHashKey();
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

    public UserRole getRole() {
        return this.user.getRole();
    }

    public Long getId() {
        return this.user.getId();
    }

    public User getUser() { return this.user; }
}
