package com.furreverhome.Furrever_Home.services.authenticationServices;

import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.SigninRequest;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private PetAdopterRepository petAdopterRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("user@example.com");
        mockUser.setPassword(passwordEncoder.encode("password"));
        mockUser.setVerified(true);
    }

    @Test
    void testSigninSuccessForShelter() {
        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setEmail("user@example.com");
        signinRequest.setPassword("password");
        when(userRepository.findByEmail(signinRequest.getEmail())).thenReturn(Optional.of(mockUser));
        when(shelterRepository.findByUserId(mockUser.getId())).thenReturn(Optional.ofNullable(mock(Shelter.class)));
        when(petAdopterRepository.findByUserId(mockUser.getId())).thenReturn(Optional.ofNullable(null));
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
        when(jwtService.generateToken(any(User.class))).thenReturn("mockJwtToken");
        when(jwtService.generateRefreshToken(any(), any(User.class))).thenReturn("mockRefreshToken");

        // Act
        JwtAuthenticationResponse response = authenticationService.signin(signinRequest);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals("mockJwtToken", response.getToken(), "JWT token does not match expected value");
        assertEquals("mockRefreshToken", response.getRefreshToken(), "Refresh token does not match expected value");

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(signinRequest.getEmail());
        verify(shelterRepository).findByUserId(mockUser.getId());
        verify(petAdopterRepository).findByUserId(mockUser.getId());
        verify(jwtService).generateToken(mockUser);
        verify(jwtService).generateRefreshToken(anyMap(), eq(mockUser));
    }


    @Test
    void testSigninSuccessForPetAdopter() {
        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setEmail("user@example.com");
        signinRequest.setPassword("password");
        when(userRepository.findByEmail(signinRequest.getEmail())).thenReturn(Optional.of(mockUser));
        when(shelterRepository.findByUserId(mockUser.getId())).thenReturn(Optional.ofNullable(null));
        when(petAdopterRepository.findByUserId(mockUser.getId())).thenReturn(Optional.ofNullable(mock(PetAdopter.class)));
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
        when(jwtService.generateToken(any(User.class))).thenReturn("mockJwtToken");
        when(jwtService.generateRefreshToken(any(), any(User.class))).thenReturn("mockRefreshToken");

        // Act
        JwtAuthenticationResponse response = authenticationService.signin(signinRequest);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals("mockJwtToken", response.getToken(), "JWT token does not match expected value");
        assertEquals("mockRefreshToken", response.getRefreshToken(), "Refresh token does not match expected value");

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(signinRequest.getEmail());
        verify(shelterRepository).findByUserId(mockUser.getId());
        verify(petAdopterRepository).findByUserId(mockUser.getId());
        verify(jwtService).generateToken(mockUser);
        verify(jwtService).generateRefreshToken(anyMap(), eq(mockUser));
    }

    @Test
    void testSigninWithBadCredentials() {
        // Arrange
        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setEmail("user@example.com");
        signinRequest.setPassword("password");
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new BadCredentialsException("Incorrect username or password"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authenticationService.signin(signinRequest), "Expected BadCredentialsException to be thrown");

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtService);
    }

    @Test
    void signinWithUnverifiedUser() {
        mockUser.setVerified(false);
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUser));
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));

        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setEmail("user@example.com");
        signinRequest.setPassword("password");

        JwtAuthenticationResponse response = authenticationService.signin(signinRequest);

        assertFalse(response.getVerified());
    }


    @Test
    void testRefreshTokenSuccess() {
        String fakeToken = "fakeToken";
        String userEmail = "user@example.com";

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setToken(fakeToken);

        when(jwtService.extractUserName(fakeToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(mockUser));
        when(jwtService.isTokenValid(fakeToken, mockUser)).thenReturn(true);
        when(jwtService.generateToken(mockUser)).thenReturn("newToken");

        JwtAuthenticationResponse response = authenticationService.refreshToken(request);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("newToken", response.getToken());
        assertEquals(fakeToken, response.getRefreshToken());
    }

    @Test
    void testRefreshTokenInvalidToken() {
        String invalidToken = "invalidToken";
        String userEmail = "user@example.com";
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setToken(invalidToken);

        when(jwtService.extractUserName(invalidToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(mockUser));
        when(jwtService.isTokenValid(invalidToken, mockUser)).thenReturn(false);

        JwtAuthenticationResponse response = authenticationService.refreshToken(request);

        assertNull(response);
    }

    @Test
    void testRefreshTokenUserNotFound() {
        String fakeToken = "fakeToken";
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setToken(fakeToken);

        when(jwtService.extractUserName(fakeToken)).thenReturn("nonexistent@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            authenticationService.refreshToken(request);
        });
    }
}
