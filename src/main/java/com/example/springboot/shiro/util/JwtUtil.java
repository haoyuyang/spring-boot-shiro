package com.example.springboot.shiro.util;

import com.example.springboot.shiro.exception.BusinessException;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtUtil {
    public static SecretKey generalKey() {
        byte[] encodedKey = "spring-boot-shiro".getBytes();
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 签发JWT
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public static String createJWT(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    /**
     * 验证JWT
     * @param jwtStr
     * @return
     */
    public static Claims validateJWT(String jwtStr) {
        Claims claims;
        try {
            claims = parseJWT(jwtStr);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(207, "Token已过期");
        } catch (Exception e) {
            throw new RuntimeException("您还未登录，请先登录");
        }
        return claims;
    }

    /**
     *
     * 解析JWT字符串
     * @param jwt
     * @return
     */
    private static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
