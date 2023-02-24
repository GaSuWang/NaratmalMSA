package com.naratmal.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {
    private static String accessSecretKey;
    private static String refreshSecretKey;
    private static Integer accessExpirationTime;
    private static Integer refreshExpirationTime;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ISSUER = "naratmalssafy";

    @Autowired
    public JwtUtil(@Value("${jwt.secret.access}") String accessSecretKey,
                   @Value("${jwt.secret.refresh}") String refreshSecretKey,
                   @Value("${jwt.expiration.access}") Integer accessExpirationTime,
                   @Value("${jwt.expiration.refresh}") Integer refreshExpirationTime) {
        this.accessSecretKey = accessSecretKey;
        this.refreshSecretKey = refreshSecretKey;
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

//    public void setExpirationTime() {
////    		JwtTokenUtil.expirationTime = Integer.parseInt(expirationTime);
//        JwtUtil.expirationTime = expirationTime;
//    }

//    public static JWTVerifier getVerifier() {
//        return JWT
//                .require(Algorithm.HMAC512(secretKey.getBytes()))
//                .withIssuer(ISSUER)
//                .build();
//    }

    public static String getAccessToken(String userEmail) {
        Date expires = JwtUtil.getTokenExpiration(accessExpirationTime);
        return JWT.create()
                .withSubject(userEmail)
                .withExpiresAt(expires)
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC512(accessSecretKey.getBytes()));
    }

    public static String getRefreshToken(String userEmail) {
        Date expires = JwtUtil.getTokenExpiration(refreshExpirationTime);
        return JWT.create()
                .withSubject(userEmail)
                .withExpiresAt(expires)
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC512(refreshSecretKey.getBytes()));
    }

    public static String getUserEmail(String refreshToken){
        return JWT.decode(refreshToken).getPayload();
    }


    public static Date getTokenExpiration(int expirationTime) {
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }

//    public static String getToken(Instant expires, String userId) {
//        return JWT.create()
//                .withSubject(userId)
//                .withExpiresAt(Date.from(expires))
//                .withIssuer(ISSUER)
//                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
//                .sign(Algorithm.HMAC512(secretKey.getBytes()));
//    }


//    public static void handleError(String token) {
//        JWTVerifier verifier = JWT
//                .require(Algorithm.HMAC512(secretKey.getBytes()))
//                .withIssuer(ISSUER)
//                .build();
//
//        try {
//            verifier.verify(token.replace(TOKEN_PREFIX, ""));
//        } catch (AlgorithmMismatchException ex) {
//            throw ex;
//        } catch (InvalidClaimException ex) {
//            throw ex;
//        } catch (SignatureGenerationException ex) {
//            throw ex;
//        } catch (SignatureVerificationException ex) {
//            throw ex;
//        } catch (TokenExpiredException ex) {
//            throw ex;
//        } catch (JWTCreationException ex) {
//            throw ex;
//        } catch (JWTDecodeException ex) {
//            throw ex;
//        } catch (JWTVerificationException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//
//    public static void handleError(JWTVerifier verifier, String token) {
//        try {
//            verifier.verify(token.replace(TOKEN_PREFIX, ""));
//        } catch (AlgorithmMismatchException ex) {
//            throw ex;
//        } catch (InvalidClaimException ex) {
//            throw ex;
//        } catch (SignatureGenerationException ex) {
//            throw ex;
//        } catch (SignatureVerificationException ex) {
//            throw ex;
//        } catch (TokenExpiredException ex) {
//            throw ex;
//        } catch (JWTCreationException ex) {
//            throw ex;
//        } catch (JWTDecodeException ex) {
//            throw ex;
//        } catch (JWTVerificationException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
}
