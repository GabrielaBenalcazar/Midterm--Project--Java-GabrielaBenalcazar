package com.ironhack.midterm.project.service.impl;

import com.ironhack.midterm.project.models.user.ThirdParty;
import com.ironhack.midterm.project.repository.UserRepository;
import com.ironhack.midterm.project.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void addThirdPartyUser(String name,String password, String hashKey) {
        ThirdParty thirdPartyUser = new ThirdParty(name,password,hashKey);
        if(thirdPartyUser.getName().length()==0){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(thirdPartyUser.getHashKey().length()==0){
            throw new IllegalArgumentException("HashKey cannot be empty");
        }
        userRepository.save(thirdPartyUser);
    }
}
