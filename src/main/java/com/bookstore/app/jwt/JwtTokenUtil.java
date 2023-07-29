package com.bookstore.app.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtil {
    public static final long  JWTexpiration = 70000000 ;

    private Key getSigningKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+JWTexpiration);

        String token = Jwts.builder().setSubject(username).setIssuedAt(currentDate).
                setExpiration(expireDate).signWith(getSigningKey()).compact();

        return token;
    }
}
