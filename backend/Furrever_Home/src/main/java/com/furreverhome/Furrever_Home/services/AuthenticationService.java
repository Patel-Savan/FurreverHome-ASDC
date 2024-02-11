package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(Role.ADMIN);

        if(adminAccount == null) {
            User user = new User();

public interface AuthenticationService {
    PetAdopterDto signup(PetAdopterSignupRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    public boolean verifyByEmail(String email);

}
