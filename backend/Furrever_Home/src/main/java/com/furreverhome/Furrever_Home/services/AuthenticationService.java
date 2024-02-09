package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.*;

public interface AuthenticationService {
    PetAdopterDto signup(PetAdopterSignupRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    public boolean verifyByEmail(String email);

}
