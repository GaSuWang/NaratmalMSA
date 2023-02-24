package com.naratmal.user.service;


import com.naratmal.user.db.User;
import com.naratmal.user.db.UserRepository;
import com.naratmal.user.dto.UserLoginRes;
import com.naratmal.user.utils.JwtUtil;
import com.naratmal.user.utils.KakaoUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /*
     * nickname search 결과 null이면 해당 닉네임 사용 가능 return true
     * 그렇지 않으면 return false
     * */
    @Override
    public boolean checkNickname(String nickname) {
        return userRepository.findByuserNickname(nickname) == null;
    }

    @Override
    public UserLoginRes registUser(String userEmail, String userName, String userNickname, String userLocation) throws Exception {

        UserLoginRes res = null;

        if (!checkNickname(userNickname)) {
            throw new Exception("DUPLICATED_NICKNAME");
        }

        User saveUser = User.builder()
                .userEmail(userEmail)
                .userLocation(userLocation)
                .userName(userName)
                .userNickname(userNickname)
                .build();

        User user = userRepository.findByUserEmail(userEmail);

        if (user != null) {
            throw new Exception("ALREADY_REGIST");
        }

        user = userRepository.save(saveUser);

        String accessJWT = JwtUtil.getAccessToken(user.getUserEmail());
        String refreshJWT = JwtUtil.getRefreshToken(user.getUserEmail());

        res = UserLoginRes.builder().accessToken(accessJWT).refreshToken(refreshJWT).email(user.getUserEmail()).isSignUp(false).build();
        return res;
    }

    @Override
    public UserLoginRes login(String code) throws Exception {
        String accessToken = "";
        String kakaoEmail = "";
        try {
            accessToken = KakaoUtil.getKakaoAccessToken(code);
            kakaoEmail = KakaoUtil.getKakaoEmail(accessToken);
            logger.info("[ Kakao Email ]: {}",kakaoEmail);
        } catch (Exception e) {
            throw e;
        }


        User user = userRepository.findByUserEmail(kakaoEmail);
        if (user == null) {
            logger.info("[ Login Error ] {} NOT Registed",kakaoEmail);
            return UserLoginRes.builder()
                    .accessToken("fail")
                    .isSignUp(true)
                    .email("")
                    .build();
        } else {
            logger.info("[ Login Success ] {}",kakaoEmail);
            //access & refresh token return
            String accessJWT = JwtUtil.getAccessToken(user.getUserEmail());
            String refreshJWT = JwtUtil.getRefreshToken(user.getUserEmail());

            return UserLoginRes.builder()
                    .accessToken(accessJWT)
                    .refreshToken(refreshJWT)
                    .isSignUp(false)
                    .email(user.getUserEmail())
                    .build();
        }

    }

    @Override
    public String reissueToken(String refreshToken) {
        String userEmail = JwtUtil.getUserEmail(refreshToken);
        return JwtUtil.getAccessToken(userEmail);
    }
}
