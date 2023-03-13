package com.naratmal.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateReq {
    private String userLocation;
    private String userName;
    private String userNickname;
}
