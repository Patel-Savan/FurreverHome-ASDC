package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.config.FrontendConfigurationProperties;
import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.SigninRequest;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import com.furreverhome.Furrever_Home.dto.user.ResetEmailRequest;
import com.furreverhome.Furrever_Home.services.AuthenticationService;
import com.furreverhome.Furrever_Home.services.authenticationServices.PetAdopterAuthenticationService;
import com.furreverhome.Furrever_Home.services.authenticationServices.ShelterAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthenticationControllerTest {

    @Mock
    private PetAdopterAuthenticationService petAdopterAuthenticationService;

    @Mock
    private ShelterAuthenticationService shelterAuthenticationService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private FrontendConfigurationProperties frontendConfigurationProperties;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignin() {
        SigninRequest signinRequest = new SigninRequest();
        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse();

        // Mocking the authentication service's behavior
        when(authenticationService.signin(signinRequest)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<JwtAuthenticationResponse> responseEntity = authenticationController.signin(signinRequest);

        // Asserting the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void testRefresh() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse();

        // Mocking the authentication service's behavior
        when(authenticationService.refreshToken(refreshTokenRequest)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<JwtAuthenticationResponse> responseEntity = authenticationController.refresh(refreshTokenRequest);

        // Asserting the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }


    @Test
    void testVerifyByEmail() {
        String email = "test@example.com";
        when(authenticationService.verifyByEmail(email)).thenReturn(true);
        RedirectView redirectView = (RedirectView) authenticationController.verifyByEmail(email);
        assertEquals(frontendConfigurationProperties.getLoginUrl(), redirectView.getUrl());
    }



}
