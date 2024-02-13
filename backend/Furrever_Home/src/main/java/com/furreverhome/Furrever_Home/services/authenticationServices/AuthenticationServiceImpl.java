package com.furreverhome.Furrever_Home.services.impl;


import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.PetAdopterDto;
import com.furreverhome.Furrever_Home.dto.PetAdopterSignupRequest;
import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.SigninRequest;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import com.furreverhome.Furrever_Home.entities.PasswordResetToken;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.exception.EmailExistsException;
import com.furreverhome.Furrever_Home.repository.PasswordTokenRepository;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.AuthenticationService;
import com.furreverhome.Furrever_Home.services.JwtService;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
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

    private final PasswordTokenRepository passwordTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


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

    @Override
    public GenericResponse resetByEmail(final String contextPath, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        System.out.println("USER HERE IS " + optionalUser.get());
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
    }

      return new GenericResponse(
            "A password reset email has been sent. Follow the instructions inside\n" + message
      );
    }

    @Override
    public GenericResponse resetPassword(PasswordDto passwordDto) {
        String result = validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return new GenericResponse(result);
        }

        Optional<User> user = getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            changeUserPassword((User) user.get(), passwordDto.getNewPassword());
            invalidateResetToken(passwordDto.getToken());
            return new GenericResponse("Password reset successfully");
        } else {
            return new GenericResponse(null, "This username is invalid, or does not exist");
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
                return new GenericResponse(null, "Invalid Old Password.");
            }

            changeUserPassword(user.get(), passwordDto.getNewPassword());
            return new GenericResponse("Password updated successfully");
        } else {
            return new GenericResponse(null, "Not a valid user.");
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
