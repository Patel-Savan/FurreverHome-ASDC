package com.furreverhome.Furrever_Home.services.impl;


import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.SigninRequest;
import com.furreverhome.Furrever_Home.dto.SignupRequest;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import com.furreverhome.Furrever_Home.entities.PasswordResetToken;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.PasswordTokenRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.AuthenticationService;
import com.furreverhome.Furrever_Home.services.JwtService;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import java.util.Calendar;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordTokenRepository passwordTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final EmailService emailService;


    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(Role.ADMIN);

        if(adminAccount == null) {
            User user = new User();

            user.setV_id("admin1234");
            user.setAddress("admin st");
            user.setPhone_number("499992222");
            user.setEmail("admin@gmail.com");
            user.setFirstname("admin");
            user.setLastname("admin");
            user.setRole(Role.ADMIN);
            user.setVerified(Boolean.TRUE);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println("Admin successfully created..");
        }
    }

    public User signup(SignupRequest signupRequest) {
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setFirstname(signupRequest.getFirstName());
        user.setLastname(signupRequest.getLastName());
        user.setV_id(signupRequest.getV_id());
        user.setPhone_number(signupRequest.getPhone_number());
        user.setAddress(signupRequest.getAddress());
        user.setVerified(Boolean.FALSE);
        user.setRole(Role.PETADOPTER);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        emailService.sendEmail(signupRequest.getEmail(), "Email Verification", "http://localhost:8080/api/auth/verify/"+signupRequest.getEmail());

        return userRepository.save(user);
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

    @Override
    public GenericResponse resetByEmail(final String contextPath, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token  = jwtService.generateToken(user);

            PasswordResetToken myToken = new PasswordResetToken(token, user);
            passwordTokenRepository.save(myToken);

            String url = contextPath + "/api/auth/redirectChangePassword?token=" + token;
            String linkText = "Click here to reset your password";
            String message = "<p>Password reset successfully. Please use the link below to reset your password. Note that the link is valid for " + PasswordResetToken.EXPIRATION + " minutes.</p>"
                + "<a href=\"" + url + "\">" + linkText + "</a>";

            try {
            emailService.sendEmail(user.getEmail(), "Password Reset", message, true);
          } catch (MessagingException e) {
              emailService.sendEmail(user.getEmail(), "Password Reset", message);
            throw new RuntimeException(e);
          }

          return new GenericResponse(
                "A password reset email has been sent. Follow the instructions inside\n" + message,
               ""
            );
        }
        return new GenericResponse(
            "Couldn't find a user with that email.",
            ""
        );
    }

    @Override
    public GenericResponse resetPassword(PasswordDto passwordDto) {
        String result = validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return new GenericResponse(result, "");
        }

        Optional user = getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            changeUserPassword((User) user.get(), passwordDto.getNewPassword());
            invalidateResetToken(passwordDto.getToken());
            return new GenericResponse("Password reset successfully", "");
        } else {
            return new GenericResponse("This username is invalid, or does not exist", "");
        }
    }

    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
            : isTokenExpired(passToken) ? "expired"
                : null;
    }

    @Override
    public GenericResponse updateUserPassword(PasswordDto passwordDto) {
        final Optional<User> user = userRepository.findByEmail(passwordDto.getEmail());
        if (user.isPresent()) {
            if (!checkIfValidOldPassword(user.get(), passwordDto.getOldPassword())) {
                return new GenericResponse("", "Invalid Old Password.");
            }

            changeUserPassword(user.get(), passwordDto.getNewPassword());
            return new GenericResponse("Password updated successfully");
        } else {
            return new GenericResponse("", "Not a valid user.");
        }
    }

    // ============== UTILS ============

    private void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    private boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    private Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token) .getUser());
    }

    private void invalidateResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);
        if (passwordResetToken != null) {
            passwordResetToken.setToken(null);
            passwordTokenRepository.save(passwordResetToken);
        }
    }
}
