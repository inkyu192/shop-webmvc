package com.toy.shopwebmvc.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@RedisHash(value = "refresh", timeToLive = 604800)
public class Token {

    @Id
    private String account;
    private String refreshToken;
    private LocalDateTime createdDate;

    public static Token createToken(String account, String refreshToken) {
        Token token = new Token();
        token.account = account;
        token.refreshToken = refreshToken;
        token.createdDate = LocalDateTime.now();

        return token;
    }
}
