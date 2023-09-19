package com.adamc.eventplannerbe.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String secretKey = "tVk0MOreUuzXlSuO8Fu7oC9Z5LKB6C9MqkmvjTRNzgjc/bkPZxawgTmG8XdZ921I";
    private static final String secretRefreshKey = "itqFmOJdMQH4fC4tFkgzXihjyM1lOilwhuueALUBDg7jJVac55QWrULUT7xUnZnsz+3CtR24XGny87DdUZ3zDhGWFLCau3GeMBkXKGnZ4yKLNWPxB6Qypx3BmW232ObD1E293H17kSLmKBK5Ee7hvTJDUJ0vu9Z6lJQiiKFNyVRkCGWrti2nyTT9MHvcztWq35hTCdAhjixdj2gyaimACf2HSfUATPg71byaV/GHVCTI9Zn+seJFmjYNJBkI96OI9xTum2cg5FnuSoPz+Tq2nst/nzcNDAofsuAe3hKv1Hb/28dg+IGiVmxYtoaUX/3zzduZf0Ei1ZYd3klBZ7PmAl6nJoHelXxpJWTt+KUrF5I=";

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public String extractRefreshUsername(String refreshToken) {
        return extractRefreshClaims(refreshToken, Claims::getSubject);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getRefreshSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretRefreshKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public <T> T extractRefreshClaims(String refreshToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllRefreshClaims(refreshToken);

        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims extractAllRefreshClaims(String refreshToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getRefreshSignInKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10)))
                .signWith(getRefreshSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isRefreshTokenValid(String refreshToken, UserDetails userDetails) {
        final String username = extractRefreshUsername(refreshToken);

        return username.equals(userDetails.getUsername()) && !isRefreshTokenExpired(refreshToken);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private boolean isRefreshTokenExpired(String refreshToken) {
        return extractRefreshExpiration(refreshToken).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private Date extractRefreshExpiration(String refreshToken) {
        return extractRefreshClaims(refreshToken, Claims::getExpiration);
    }
}
