package com.naratmal.user.service;


import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public boolean checkNickname(String nickname){
        //check
        return true;
    }
}
