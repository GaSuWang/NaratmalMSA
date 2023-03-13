package com.naratmal.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRes {
    private String userName;
    private String userNickname;
    private String userLocation;
}
