package com.yildiz.terapinisec.util;

import com.yildiz.terapinisec.dto.UserResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "rabia_ecem_emre";

    public String generateToken(UserResponseDto userResponseDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_" + userResponseDto.getUserRole().name());
        claims.put("isPremium", userResponseDto.isPremium());
        claims.put("userName", userResponseDto.getUserName());
        claims.put("email", userResponseDto.getEmail());
        return createToken(claims, userResponseDto.getUserNameOrEmail());
    }


    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    public String extractUsernameOrEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Boolean validateToken(String token, UserResponseDto userResponseDto) {
        final String usernameOrEmail = extractUsernameOrEmail(token);
        return ((usernameOrEmail.equals(userResponseDto.getUserName()) || usernameOrEmail.equals(userResponseDto.getEmail()))
                && !isTokenExpired(token));
    }
}