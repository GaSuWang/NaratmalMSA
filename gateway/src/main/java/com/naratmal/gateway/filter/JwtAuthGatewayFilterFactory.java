package com.naratmal.gateway.filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import com.auth0.jwt.JWT;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
@Component
public class JwtAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthGatewayFilterFactory.Config> {
    @Value("${jwt.secret.key}")
    String accessSecretKey;

    public JwtAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            ServerHttpRequest request = exchange.getRequest();
            //헤더에 토큰 있는지 확인
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return returnError(response,"NOT_EXIST_TOKEN",HttpStatus.UNAUTHORIZED);
            }
            //토큰 가져오기
            String token = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
            //토큰 유효성 검사
            try{
                //Leeway -> 여유시간
                JWT.require(Algorithm.HMAC512(accessSecretKey.getBytes())).acceptLeeway(5).build().verify(token);
            } catch (Exception e){
                return returnError(response,"INVALID_TOKEN",HttpStatus.UNAUTHORIZED);
            }
            String payload = JWT.decode(token).getPayload();
            String decodePayload = new String(Base64.getUrlDecoder().decode(payload));
            String userEmail = new Gson().fromJson(decodePayload, Map.class).get("sub").toString();


            request.mutate().header("Authorization-Email",userEmail);



            return chain.filter(exchange);
        };
    }

    public Mono<Void> returnError(ServerHttpResponse response, String msg, HttpStatus status){
        response.setStatusCode(status);
        DataBuffer buffer = response.bufferFactory().wrap(msg.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    public static class Config{

    }
}
