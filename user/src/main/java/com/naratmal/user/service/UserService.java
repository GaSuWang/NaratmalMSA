package com.naratmal.user.service;


import com.naratmal.user.dto.UpdateRes;
import com.naratmal.user.dto.UserInfo;
import com.naratmal.user.dto.UserLoginRes;
import org.springframework.stereotype.Service;


public interface UserService {
    public boolean checkNickname(String nickname);
    public UserLoginRes registUser(String userEmail, String userName, String userNickname, String userLocation) throws Exception;
    public UserLoginRes login(String code) throws Exception;
    public String reissueToken(String refreshToken);
    public UpdateRes updateUser(Long userSeq, String userEmail, String userLocation, String userName, String userNickname);
    public UserInfo getUserInfo(String userEmail);
    public Long getUserSeq(String userEmail);
}
