package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.config.FrontendConfigurationProperties;
import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.SigninRequest;
import com.furreverhome.Furrever_Home.services.AuthenticationService;
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

    @Test
    public void testShowChangePasswordPage_InvalidToken() {
        // Given
        String token = "invalidToken";
        when(authenticationService.validatePasswordResetToken(token)).thenReturn("invalidToken");
        when(frontendConfigurationProperties.getLoginUrl()).thenReturn("http://example.com/login");

        // When
        RedirectView redirectView = authenticationController.showChangePasswordPage(token);

        // Then
        assertEquals("http://example.com/login?message=Invalid+token.", redirectView.getUrl());
    }

    @Test
    public void testShowChangePasswordPage_ExpiredToken() {
        // Given
        String token = "expiredToken";
        when(authenticationService.validatePasswordResetToken(token)).thenReturn("expired");
        when(frontendConfigurationProperties.getLoginUrl()).thenReturn("http://example.com/login");

        // When
        RedirectView redirectView = authenticationController.showChangePasswordPage(token);

        // Then
        assertEquals("http://example.com/login?message=Token+has+expired.", redirectView.getUrl());
    }

    @Test
    public void testShowChangePasswordPage_ValidToken() {
        // Given
        String token = "validToken";
        when(authenticationService.validatePasswordResetToken(token)).thenReturn(null);
        when(frontendConfigurationProperties.getUpdatePasswordUrl()).thenReturn("http://example.com/updatePassword");

        // When
        RedirectView redirectView = authenticationController.showChangePasswordPage(token);

        // Then
        assertEquals("http://example.com/updatePassword?token=validToken", redirectView.getUrl());
    }



}
