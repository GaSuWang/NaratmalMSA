package com.naratmal.user.service;


import com.naratmal.user.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;


    /*
    * nickname search 결과 null이면 해당 닉네임 사용 가능 return true
    * 그렇지 않으면 return false
    * */
    @Override
    public boolean checkNickname(String nickname){
        if(userRepository.searchUserByuserNickname(nickname)==null) return true;
        else return false;
    }
}
