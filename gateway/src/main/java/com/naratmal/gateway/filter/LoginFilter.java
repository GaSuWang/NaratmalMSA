package com.naratmal.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class LoginFilter extends AbstractGatewayFilterFactory<LoginFilter.Config> {

    public LoginFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange,chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            System.out.println();
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                System.out.println(response.getStatusCode());
            }));
        });
    }

    public static class Config{

    }
}
