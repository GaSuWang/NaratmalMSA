package com.naratmal.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class RegistUserReq {

    @NotEmpty
    String userEmail;
    @NotEmpty
    String userNickname;
    @NotEmpty
    String userName;
    @NotEmpty
    String userLocation;
}
