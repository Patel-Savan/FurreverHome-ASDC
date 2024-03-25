package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.SigninRequest;
import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    boolean signup(String appUrl, SignupRequest signupRequest) throws MessagingException;
    JwtAuthenticationResponse signin(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    GenericResponse resetByEmail(final String contextPath, String email);

    GenericResponse resetPassword(final PasswordDto passwordDto);

    String validatePasswordResetToken(final String token);

    GenericResponse updateUserPassword(final PasswordDto passwordDto);

    boolean verifyByEmail(String email);
}
