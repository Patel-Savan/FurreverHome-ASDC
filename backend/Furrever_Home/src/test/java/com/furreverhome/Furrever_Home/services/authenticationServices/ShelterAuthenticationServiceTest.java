package com.furreverhome.Furrever_Home.services.authenticationServices;

import com.furreverhome.Furrever_Home.dto.PetAdopterDto;
import com.furreverhome.Furrever_Home.dto.PetAdopterSignupRequest;
import com.furreverhome.Furrever_Home.dto.ShelterDto;
import com.furreverhome.Furrever_Home.dto.ShelterSignupRequest;
import com.furreverhome.Furrever_Home.dto.petadopter.ShelterResponseDto;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterAuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShelterRepository shelterRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ShelterAuthenticationService shelterAuthenticationService;

    @Test
    void testSignupSuccessfully() throws MessagingException {
        // Arrange
        ShelterSignupRequest signupRequest = new ShelterSignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword("password123");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ShelterDto result = shelterAuthenticationService.signup("http://localhost:8080", signupRequest);

        // Assert
        assertNotNull(result);
        assertEquals(signupRequest.getEmail(), result.getEmail());
        verify(emailService, times(1)).sendEmail(
                eq(signupRequest.getEmail()),
                anyString(),
                anyString(),
                eq(true));
    }

    @Test
    void testThrowExceptionWhenEmailExists() {
        // Arrange
        ShelterSignupRequest signupRequest = new ShelterSignupRequest();
        signupRequest.setEmail("existing@example.com");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            shelterAuthenticationService.signup("http://localhost:8080", signupRequest);
        });
    }
}
