package com.karl.core.auth.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Map;

/**
 * Token服务
 *
 * @author: 杜永军
 * @since: 2018/12/12 15:15
 * @update: 2018/12/24 14:14
 **/
public interface TokenService {
    String generate(Object subject, long id);

    String generate(Object subject, long id, int hours);

    String generate(Object subject, Map<String, Object> payload);

    String generate(Object subject, Map<String, Object> payload, int hours);

    Jws<Claims> parse(Object subject, String token);
}