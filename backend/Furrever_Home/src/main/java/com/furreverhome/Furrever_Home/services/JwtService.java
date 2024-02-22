package com.furreverhome.Furrever_Home.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    String extractUserName(String token);

    boolean isTokenValid(String jwt, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}