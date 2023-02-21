package com.naratmal.user.service;


import com.naratmal.user.db.User;
import com.naratmal.user.db.UserRepository;
import com.naratmal.user.dto.UserLoginRes;
import com.naratmal.user.utils.KakaoUtil;
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
        return userRepository.findByuserNickname(nickname) == null;
    }

    @Override
    public UserLoginRes registUser(String userEmail, String userName, String userNickname, String userLocation) throws Exception {
        UserLoginRes res = null;
        if(!checkNickname(userNickname)){
            throw new Exception("DUPLICATED_NICKNAME");
        }

        User saveUser = User.builder()
                .userEmail(userEmail)
                .userLocation(userLocation)
                .userName(userName)
                .userNickname(userNickname)
                .build();
        User user = userRepository.findByUserEmail(userEmail);
        if(user!=null){
            throw new Exception("ALREADY_REGIST");
        }
        user = userRepository.save(saveUser);
        res = UserLoginRes.builder().loginResult("Success").email(user.getUserEmail()).isSignUp(false).build();
        return res;
    }

    @Override
    public UserLoginRes login(String code) throws Exception {
        String accessToken = KakaoUtil.getKakaoAccessToken(code);
        String kakaoEmail = KakaoUtil.getKakaoEmail(accessToken);
        User user = userRepository.findByUserEmail(kakaoEmail);
        if(user==null){
            return UserLoginRes.builder()
                    .loginResult("fail")
                    .isSignUp(true)
                    .email(user.getUserEmail())
                    .build();
        } else {
            return UserLoginRes.builder()
                    .loginResult("success")
                    .isSignUp(false)
                    .email(user.getUserEmail())
                    .build();
        }

    }
}
