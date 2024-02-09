package com.furreverhome.Furrever_Home.services.impl;


import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.exception.EmailExistsException;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.AuthenticationService;
import com.furreverhome.Furrever_Home.services.JwtService;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PetAdopterRepository petAdopterRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final EmailService emailService;


    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(Role.ADMIN);

        if(adminAccount == null) {
            User user = new User();

            user.setEmail("admin@gmail.com");
            user.setRole(Role.ADMIN);
            user.setVerified(Boolean.TRUE);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println("Admin successfully created..");
        }
    }

    public PetAdopterDto signup(PetAdopterSignupRequest petAdopterSignupRequest) {

        if(userRepository.existsByEmail(petAdopterSignupRequest.getEmail())) {
            try {
                throw new EmailExistsException("User Already Exists");
            } catch (EmailExistsException e) {
                throw new RuntimeException(e);
            }
        }

        User user = new User();
        user.setEmail(petAdopterSignupRequest.getEmail());
        user.setVerified(Boolean.FALSE);
        user.setRole(Role.PETADOPTER);
        user.setPassword(passwordEncoder.encode(petAdopterSignupRequest.getPassword()));
        User result = userRepository.save(user);
        PetAdopter petAdopter = new PetAdopter();
        if (Objects.equals(petAdopterSignupRequest.getRole(), "petadopter")) {

            petAdopter.setFirstname(petAdopterSignupRequest.getFirstName());
            petAdopter.setLastname(petAdopterSignupRequest.getLastName());
            petAdopter.setPhone_number(petAdopterSignupRequest.getPhone_number());
            petAdopter.setAddress(petAdopterSignupRequest.getAddress());
            petAdopter.setUser(result);

            emailService.sendEmail(petAdopterSignupRequest.getEmail(), "Email Verification", "http://localhost:8080/api/auth/verify/"+petAdopterSignupRequest.getEmail());

            petAdopterRepository.save(petAdopter);
        }
        PetAdopterDto petAdopterDto = new PetAdopterDto();
        petAdopterDto.setId(result.getId());
        petAdopterDto.setFirstname(petAdopter.getFirstname());
        petAdopterDto.setEmail(result.getEmail());
        petAdopterDto.setUserRole(result.getRole());
        return petAdopterDto;
    }

    public boolean verifyByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setVerified(Boolean.TRUE);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public JwtAuthenticationResponse signin(SigninRequest signinRequest) throws
            BadCredentialsException,
            DisabledException,
            UsernameNotFoundException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),
                    signinRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email and password"));
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        if(user.getVerified()) {
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            jwtAuthenticationResponse.setUserRole(user.getRole());
            jwtAuthenticationResponse.setUserId(user.getId());
            jwtAuthenticationResponse.setVerified(user.getVerified());
            return jwtAuthenticationResponse;
        }
        jwtAuthenticationResponse.setVerified(user.getVerified());
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }


}
