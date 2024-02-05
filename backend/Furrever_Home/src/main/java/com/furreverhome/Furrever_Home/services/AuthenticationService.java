package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.SignupRequest;
import com.furreverhome.Furrever_Home.entities.User;

public interface AuthenticationService {
    User signup(SignupRequest signUpRequest);

}
