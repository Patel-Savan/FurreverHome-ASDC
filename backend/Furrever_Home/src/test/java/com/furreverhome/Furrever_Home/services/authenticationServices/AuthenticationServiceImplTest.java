package com.furreverhome.Furrever_Home.services.authenticationServices;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import com.furreverhome.Furrever_Home.entities.PasswordResetToken;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.PasswordTokenRepository;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
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

    @Mock
    private PasswordTokenRepository passwordTokenRepository;

    @Mock
    private EmailService emailService;

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

    @Test
    void testVerifyByEmailUserExistsUserVerified() {
        // Arrange
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);
        user.setVerified(false);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        boolean result = authenticationService.verifyByEmail(email);

        // Assert
        assertTrue(result);
        assertTrue(user.getVerified());
        verify(userRepository).save(user);
    }

    @Test
    void testVerifyByEmailUserDoesNotExistReturnFalse() {
        // Arrange
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        boolean result = authenticationService.verifyByEmail(email);

        // Assert
        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testResetByEmailSuccessfulReset() throws Exception {
        // Arrange
        String email = "user@example.com";
        String token = "randomToken";
        String contextPath = "http://example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        when(jwtService.generateToken(mockUser)).thenReturn(token);

        // Act
        GenericResponse response = authenticationService.resetByEmail(contextPath, email);

        // Assert
        assertNotNull(response);
        assertTrue(response.getMessage().contains("A password reset email has been sent"));
        verify(jwtService).generateToken(mockUser);
        verify(passwordTokenRepository).save(any(PasswordResetToken.class));
        verify(emailService).sendEmail(eq(email), eq("Password Reset"), anyString(), eq(true));
    }

    @Test
    void testResetByEmailUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        String contextPath = "http://example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authenticationService.resetByEmail(contextPath, email);
        }, "Expected exception for user not found");
    }


    @Test
    void testResetPasswordWithInvalidTokenReturnsErrorMessage() {
        // Arrange
        String token = "invalidToken";
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setToken(token);
        passwordDto.setNewPassword("newPassword");
        when(authenticationService.validatePasswordResetToken(token)).thenReturn(null);

        // Act
        GenericResponse response = authenticationService.resetPassword(passwordDto);

        // Assert
        assertEquals(token,response.getMessage());
    }

    @Test
    public void testWhenNoAdminAccountExistsThenCreateAdminAccount() {
        // Arrange
        when(userRepository.findByRole(Role.ADMIN)).thenReturn(null);

        // Act
        authenticationService.createAdminAccount();

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testWhenAdminAccountExistsThenDoNotCreateAdminAccount() {
        // Arrange
        User existingAdmin = new User();
        existingAdmin.setEmail("admin@gmail.com");
        existingAdmin.setRole(Role.ADMIN);
        when(userRepository.findByRole(Role.ADMIN)).thenReturn(existingAdmin);

        // Act
        authenticationService.createAdminAccount();

        // Assert
        verify(userRepository, never()).save(any(User.class));
    }

//    @Test
//    void resetPassword_WithValidTokenAndUserPresent_PasswordResetSuccessfully() {
//        // Arrange
//        String token = "validToken";
//        PasswordDto passwordDto = new PasswordDto();
//        passwordDto.setNewPassword("newPassword");
//
//        when(authenticationService.validatePasswordResetToken(token)).thenReturn(null);
//        when(authenticationService.getUserByPasswordResetToken(token)).thenReturn(Optional.of(mockUser));
//        // Simulating finding a user
//
//        // Act
//        GenericResponse response = authenticationService.resetPassword(passwordDto);
//
//        // Assert
//        assertEquals("Password reset successfully", response.getMessage());
//    }

//    @Test
//    void resetPassword_WithValidTokenButNoUser_ReturnsErrorMessage() {
//        // Arrange
//        String token = "validTokenButNoUser";
//        PasswordDto passwordDto = new PasswordDto();
//        passwordDto.setToken("validTokenButNoUser");
//        when(authenticationService.validatePasswordResetToken(passwordDto.getToken())).thenReturn(null);
//        when(authenticationService.getUserByPasswordResetToken(passwordDto.getToken())).thenReturn();
//        PasswordResetToken validToken = new PasswordResetToken(token, new User());
//        when(passwordTokenRepository.findByToken(token)).thenReturn(validToken);
//        // Act
//        GenericResponse response = authenticationService.resetPassword(passwordDto);
//
//        // Assert
//        assertNotNull(response.getMessage());
//        assertEquals("This username is invalid, or does not exist", response.getMessage());
//    }
}
