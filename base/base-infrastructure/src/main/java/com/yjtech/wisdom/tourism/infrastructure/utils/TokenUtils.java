package com.yjtech.wisdom.tourism.infrastructure.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
    //自定义秘钥
    private static final String SECRET = "0x6c717a";

    //生成token
    public static String getToken(String uuid) {
        try {
            //参数为自定义秘钥
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            String token = JWT.create()
                    //签名
                    .withIssuer("auth0")
                    //token中携带的值
                    .withClaim("uuid", uuid)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return "";
    }

    //解析token
    public static String verifyToken(String token) {
        try {                            //秘钥
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    //签名
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            //解析token
            DecodedJWT jwt = verifier.verify(token);
            //通过生成token是存入参数的key取出
            Claim uuid = jwt.getClaim("uuid");
            //返回从token中解析出的值
            return uuid.asString();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        return "";
    }

    //解析token
    public static DecodedJWT parseToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                //签名
                .withIssuer("auth0")
                .build(); //Reusable verifier instance
        //解析token
        DecodedJWT jwt = verifier.verify(token);
        //通过生成token是存入参数的key取出
        return jwt;
    }
}
