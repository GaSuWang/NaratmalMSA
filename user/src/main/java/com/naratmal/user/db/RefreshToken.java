package com.naratmal.user.db;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;



@Getter
@Builder
@AllArgsConstructor
@RedisHash(value="refreshToken",timeToLive = 360000)
public class RefreshToken {
    @Id
    private String email;

    private String token;
}
