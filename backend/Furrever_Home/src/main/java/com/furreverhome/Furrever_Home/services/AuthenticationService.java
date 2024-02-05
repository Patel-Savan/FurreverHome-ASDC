package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.SigninRequest;
import com.furreverhome.Furrever_Home.dto.SignupRequest;
import com.furreverhome.Furrever_Home.entities.User;

public interface AuthenticationService {
    User signup(SignupRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    public boolean verifyByEmail(String email);

}
