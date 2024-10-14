package com.remreren.justodoit.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remreren.justodoit.domain.user.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    public JwtService jwtService;
    public ObjectMapper objectMapper;

    @Value("${security.jwt.secret-key}")
    private String secretKey = "your-256-bit-secret-123456-256bit";

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration = 3600000; // 1 hour

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        jwtService = new JwtService();

        jwtService.setSecretKey(secretKey);
        jwtService.setJwtExpiration(jwtExpiration);
    }

    @Test
    void testExtractClaims() {
        // Given
        String token = generateTestToken();

        // When
        Claims claims = jwtService.extractClaims(token);

        // Then
        assertNotNull(claims);
        assertEquals("test@example.com", claims.getSubject());
    }

    @Test
    void testIsTokenValid() {
        String token = generateTestToken();
        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    void testGenerateToken() throws Exception {
        // Given
        User user = new User(UUID.randomUUID().toString(), "test@example.com", "password123");

        // When
        String token = jwtService.generateToken(user);

        // Then
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<>() {
        };

        String[] tokenParts = token.split("\\.");
        byte[] payload = Base64.getDecoder().decode(tokenParts[1]);

        Map<String, String> payloadMap = objectMapper.readValue(payload, typeRef);

        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token));
        assertEquals(user.email(), payloadMap.get("sub"));
        assertEquals(user.id(), payloadMap.get("sub_id"));
    }

    private String generateTestToken() {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                   .setSubject("test@example.com")
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                   .signWith(key)
                   .compact();
    }
}