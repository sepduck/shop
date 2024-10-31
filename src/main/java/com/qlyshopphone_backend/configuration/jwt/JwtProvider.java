package com.qlyshopphone_backend.configuration.jwt;

import com.qlyshopphone_backend.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.JWT_PRIVATE_KEY_BASE64}")
    private String privateKeyString;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        Users users = (Users) authentication.getPrincipal();
        claims.put("roles", authentication.getAuthorities());
        claims.put("email", users.getEmail());
        claims.put("firstName", users.getFirstName());
        claims.put("lastName", users.getLastName());

        SecretKey secretKey = Keys.hmacShaKeyFor(privateKeyString.getBytes());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(users.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(privateKeyString.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(privateKeyString.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();

    }
}
