package com.ironhack.midterm.project.service.impl;

import com.ironhack.midterm.project.models.user.User;
import com.ironhack.midterm.project.repository.UserRepository;
import com.ironhack.midterm.project.security.UserRoleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserRoleDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByName(username);

        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UserRoleDetails(optionalUser.get());
    }
}
