package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.SigninRequest;
import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;

public interface AuthenticationService {

    JwtAuthenticationResponse signin(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    GenericResponse resetByEmail(final String contextPath, String email);

    GenericResponse resetPassword(final PasswordDto passwordDto);

    String validatePasswordResetToken(final String token);

    GenericResponse updateUserPassword(final PasswordDto passwordDto);

    boolean verifyByEmail(String email);
}
