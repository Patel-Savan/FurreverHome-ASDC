package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.services.authenticationServices.PetAdopterAuthenticationService;
import com.furreverhome.Furrever_Home.services.authenticationServices.ShelterAuthenticationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.furreverhome.Furrever_Home.config.FrontendConfigurationProperties;
import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.PetAdopterSignupRequest;
import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.SigninRequest;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import com.furreverhome.Furrever_Home.dto.user.ResetEmailRequest;
import com.furreverhome.Furrever_Home.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import com.furreverhome.Furrever_Home.dto.user.ResetEmailRequest;
import com.furreverhome.Furrever_Home.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

//    private final AuthenticationServiceImpl authenticationService;

    private final PetAdopterAuthenticationService petAdopterAuthenticationService;

    private final ShelterAuthenticationService shelterAuthenticationService;
    private final AuthenticationService authenticationService;
    private final FrontendConfigurationProperties frontendConfigurationProperties;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(HttpServletRequest request, @RequestBody SignupRequest signupRequest) throws MessagingException {
        if (signupRequest instanceof PetAdopterSignupRequest) {
            return ResponseEntity.ok(petAdopterAuthenticationService.signup(getAppUrl(request), (PetAdopterSignupRequest) signupRequest));
        } else if (signupRequest instanceof ShelterSignupRequest) {
            return ResponseEntity.ok(shelterAuthenticationService.signup(getAppUrl(request), (ShelterSignupRequest) signupRequest));
        }
        return ResponseEntity.ok(null);
    }

    @GetMapping("/verify/{email}")
    public Object verifyByEmail(@PathVariable String email) {
        if (authenticationService.verifyByEmail(email)) {
            return new RedirectView(frontendConfigurationProperties.getLoginUrl());
        }
        return ResponseEntity.notFound();
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin (@RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh (@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<GenericResponse> reset (
        final HttpServletRequest request,
        @Valid @RequestBody ResetEmailRequest resetEmailRequest
    ) {
        return ResponseEntity.ok(authenticationService.resetByEmail(getAppUrl(request), resetEmailRequest.email()));
    }

    @GetMapping("/redirectChangePassword")
    public RedirectView showChangePasswordPage(@RequestParam("token") String token) {
        String result = authenticationService.validatePasswordResetToken(token);
        if(result != null) {
            String message;
            switch (result) {
                case "invalidToken":
                    message = "Invalid token.";
                    break;
                case "expired":
                    message = "Token has expired.";
                    break;
                default:
                    message = "Error.";
            }

          String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
          return new RedirectView(frontendConfigurationProperties.getLoginUrl() + "?message="  + encodedMessage);
        } else {
            return new RedirectView(frontendConfigurationProperties.getUpdatePasswordUrl() + "?token=" + token);
        }
    }

    @PostMapping("/resetPassword")
    public GenericResponse resetPassword(@Valid @RequestBody PasswordDto passwordDto) {
        return authenticationService.resetPassword(passwordDto);
    }

    @PostMapping("/updatePassword")
    public GenericResponse changeUserPassword(@Valid @RequestBody PasswordDto passwordDto) {
        return authenticationService.updateUserPassword(passwordDto);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
