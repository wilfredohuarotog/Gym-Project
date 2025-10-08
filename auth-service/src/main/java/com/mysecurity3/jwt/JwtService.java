package com.mysecurity3.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final Key key;

    public JwtService(@Value("${security.key}") String key) {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String getToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails.getAuthorities() != null && !userDetails.getAuthorities().isEmpty()) {
            String role = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
            claims.put("role", role);
        }

        return buildToken(claims, userDetails);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }

    public Claims getAllClaims(String token) {

        return Jwts
                .parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){

        Claims claims = getAllClaims(token);

        return claimsResolver.apply(claims);

    }

    public boolean isTokenExpired (String token){

        Date date = getClaim(token,Claims::getExpiration);

        return date.before(new Date());
    }
}