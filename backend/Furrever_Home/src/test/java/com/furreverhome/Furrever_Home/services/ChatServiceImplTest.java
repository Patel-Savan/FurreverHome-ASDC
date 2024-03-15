package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.chat.ChatCredentialsResponse;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.chat.ChatServiceImpl;
import io.getstream.chat.java.exceptions.StreamException;
import org.apache.commons.lang3.Validate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ChatServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetAdopterRepository petAdopterRepository;

    @Mock
    private ShelterRepository shelterRepository;

    @InjectMocks
    private ChatServiceImpl chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateChatSession_UserNotFound() {
        // Arrange
        long fromUserId = 1L;
        long toUserId = 2L;
        when(userRepository.findById(fromUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> chatService.createChatSession(fromUserId, toUserId));
    }


    @Test
    public void testCreateChatSession_SuccessfulCreation() throws StreamException {
        // Arrange
        long fromUserId = 1L;
        long toUserId = 2L;
        User fromUser = mock(User.class);
        User toUser = mock(User.class);
        PetAdopter petAdopter = mock(PetAdopter.class);
        Shelter shelter = mock(Shelter.class);

        when(userRepository.findById(fromUserId)).thenReturn(Optional.of(fromUser));
        when(userRepository.findById(toUserId)).thenReturn(Optional.of(toUser));
        when(fromUser.getRole()).thenReturn(Role.PETADOPTER);
        when(toUser.getRole()).thenReturn(Role.SHELTER);
        when(petAdopterRepository.findByUserId(any(Long.class))).thenReturn(Optional.of(petAdopter));
        when(shelterRepository.findByUserId(any(Long.class))).thenReturn(Optional.of(shelter));
        when(petAdopter.getUser()).thenReturn(fromUser);
        when(shelter.getUser()).thenReturn(toUser);
        when(chatService.getAvatarUrl(any(String.class))).thenReturn("");

        // Act
        ChatCredentialsResponse response = chatService.createChatSession(fromUserId, toUserId);

        // Assert
        assertNotNull(response);
    }




}
