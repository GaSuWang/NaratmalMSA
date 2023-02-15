package com.naratmal.user.controller;


import com.naratmal.user.dto.RegistUserReq;
import com.naratmal.user.dto.UserLoginRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class Controller {
    private Logger logger = LoggerFactory.getLogger(Controller.class);


    @PostMapping()
    public ResponseEntity<UserLoginRes> registUser(@Valid @RequestBody RegistUserReq userReq){
        logger.info("[Regist User] Parameter {}",userReq);
        UserLoginRes res = null;




        return ResponseEntity.status(200).body(res);
    }

}
