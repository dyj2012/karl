package com.karl.core.util;

import com.karl.core.auth.exception.TokenException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * Token 工具类
 *
 * @author 杜永军
 **/
@Service
public class JwtUtils {

    /**
     * Token密钥
     **/
    private static final String SECRECT = "KARL SECRET + 64 BIT > OVJPIDSFWEcvoOIDF1-232''F]WE=F23=-4VXCNONSFIOEIWI023";

    /**
     * 将密钥转换成byte数组
     **/
    private static byte[] getSecret() {
        return Base64.getEncoder().encode(SECRECT.getBytes());
    }

    /**
     * 生成Token
     **/
    private static String buildToken(String subject, Map<String, Object> payload, int hours) {
        return Jwts.builder().setClaims(payload).setSubject(subject).setExpiration(new Date(System.currentTimeMillis() + (hours * 36000000)))
                .signWith(SignatureAlgorithm.HS256, getSecret()).compact();
    }

    /**
     * 通过Map设置带Payload的Token，可以自定义有效期
     **/
    public static String generate(String subject, Map<String, Object> payload, int hours) {
        return buildToken(subject, payload, hours);
    }

    /**
     * 格式化Token，返回一个Jws对象
     **/
    public static Jws<Claims> parse(String subject, String token) {
        try {
            return Jwts.parser().requireSubject(subject).setSigningKey(getSecret()).parseClaimsJws(token);
        } catch (JwtException e) {
            throw new TokenException();
        }
    }

}