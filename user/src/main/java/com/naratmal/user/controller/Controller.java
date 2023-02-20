package com.naratmal.user.controller;


import com.naratmal.user.dto.RegistUserReq;
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
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/{code}")
    public ResponseEntity<UserLoginRes> login(@PathVariable String code){
        logger.info("[login User] Parameter: {}",code);
        UserLoginRes res = null;
        return ResponseEntity.status(200).body(res);
    }

}
