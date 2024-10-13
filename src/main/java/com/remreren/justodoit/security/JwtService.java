package com.remreren.justodoit.security;

import com.remreren.justodoit.domain.user.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSecretKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    public boolean isTokenValid(String token) {
        Claims claims = extractClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    public String generateToken(User user) {
        return Jwts.builder()
                   .setClaims(Map.of("sub_id", user.id()))
                   .setSubject(user.email())
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                   .signWith(getSecretKey())
                   .compact();
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
