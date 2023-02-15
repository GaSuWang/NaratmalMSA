package com.naratmal.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserLoginRes {
    Boolean isSignUp;
    String loginResult;
    String email;
}
