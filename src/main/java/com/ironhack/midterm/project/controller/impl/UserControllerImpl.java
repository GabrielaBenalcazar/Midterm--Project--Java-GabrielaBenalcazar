package com.ironhack.midterm.project.controller.impl;

import com.ironhack.midterm.project.controller.interfaces.UserController;
import com.ironhack.midterm.project.models.user.ThirdParty;
import com.ironhack.midterm.project.models.user.User;
import com.ironhack.midterm.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     *Third-party users must be added to the database by an admin.
     */
    @PostMapping("/user/third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public void addThirdPartyUser(String name, String password, String hashKey) {
        User user = new ThirdParty(name, password, hashKey);

        userRepository.save(user);
    }
}
