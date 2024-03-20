package com.furreverhome.Furrever_Home.services.impl;

import com.furreverhome.Furrever_Home.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest()
public class JwtServiceImplTest {

    @Autowired
    private JwtService jwtService;

    @Test
    public void testGenerateToken() {
        UserDetails userDetails = new User("testUser", "password", new ArrayList<>());
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    public void testGenerateRefreshToken() {
        UserDetails userDetails = new User("testUser", "password", new ArrayList<>());
        Map<String, Object> extraClaims = new HashMap<>();
        String refreshToken = jwtService.generateRefreshToken(extraClaims, userDetails);
        assertNotNull(refreshToken);
    }

    @Test
    public void testIsTokenValid() {
        UserDetails userDetails = new User("testUser", "password", new ArrayList<>());
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }


}