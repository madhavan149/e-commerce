package com.example.e_commerce.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String secret = "ecomprodjwtsecretfG9saTNuWDJyNXA4cTNsNnc4eDkm";

    public String generateToken(String username){
        Map<String,  Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}
