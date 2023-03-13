package com.naratmal.user.controller;


import com.naratmal.user.db.User;
import com.naratmal.user.dto.RegistUserReq;
import com.naratmal.user.dto.UpdateReq;
import com.naratmal.user.dto.UpdateRes;
import com.naratmal.user.dto.UserLoginRes;
import com.naratmal.user.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")

public class Controller {
    private Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private UserService userService;

    @GetMapping("/checknickname/{nickname}")
    public ResponseEntity<Boolean> checkNickname(@PathVariable String nickname){
        logger.info("[CheckNickname] Parameter: {}",nickname);
        return ResponseEntity.ok(userService.checkNickname(nickname));
    }

    @PostMapping()
    public ResponseEntity<UserLoginRes> registUser(@Valid @RequestBody RegistUserReq userReq){
        logger.info("[Regist User] Parameter: {}",userReq);
        UserLoginRes res = null;
        try {
            res = userService.registUser(userReq.getUserEmail(),userReq.getUserName(),userReq.getUserNickname(),userReq.getUserLocation());
        } catch (Exception e){
            if("DUPLICATED_NICKNAME".equals(e.getMessage())) return ResponseEntity.status(900).body(null);
            else if("ALREADY_REGIST".equals(e.getMessage())) return ResponseEntity.status(901).body(null);
        }

        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/{code}")
    public ResponseEntity<UserLoginRes> login(@PathVariable String code){
        logger.info("[login User] Parameter: {}",code);
        UserLoginRes res = null;
        try {
            res = userService.login(code);
        } catch (Exception e) {
            logger.error("error: ",e);
            return ResponseEntity.status(500).body(null);
        }
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/accessToken/{refreshToken}")
    public ResponseEntity<String> getAccessToken (@PathVariable String refreshToken){
        try{
            return ResponseEntity.status(200).body(userService.reissueToken(refreshToken));
        } catch (RuntimeException e){
            return ResponseEntity.status(902).body("INVALID_REFRESH_TOKEN");
        }
    }

    @PutMapping()
    public ResponseEntity<UpdateRes> updateUser (@RequestBody UpdateReq req, @RequestHeader(value = "Authorization-Email") String userEmail){
        Long userSeq = userService.getUserSeq(userEmail);
        UpdateRes res = userService.updateUser(userSeq,userEmail,req.getUserLocation(),req.getUserName(),req.getUserNickname());
        return ResponseEntity.status(200).body(res);
    }
    

//    @GetMapping()
//    public ResponseEntity<GetInfo> getUserInfo(){
//
//    }


}
