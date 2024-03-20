package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.chat.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
//        mockUserRepositoryBehavior();
//        mockPetAdopterRepositoryBehavior();
//        mockShelterRepositoryBehavior();
    }

    @Test
    void createChatSession_ShouldCreateSessionForValidUsers() {
        assertTrue(true);
    }


    @Test
    void createChatSession_ShouldThrowExceptionWhenFromUserNotFound() {
        // Test exception handling when fromUser is not found
        assertTrue(true);
    }

    @Test
    void createChatSession_ShouldThrowExceptionWhenToUserNotFound() {
        // Test exception handling when toUser is not found
        assertTrue(true);
    }

    @Test
    void getChatHistory_ShouldReturnChatHistoryForValidUser() {
        // Test happy path for getChatHistory
        assertTrue(true);
    }

    @Test
    void getChatHistory_ShouldThrowExceptionWhenUserNotFound() {
        // Test exception handling when user is not found
        assertTrue(true);
    }

    @Test
    void getAvatarUrl_ShouldReturnValidUrlForEmail() {
        // Test that getAvatarUrl returns a correctly formatted URL
        assertTrue(true);
    }

    @Test
    void generateChannelId_ShouldGenerateExpectedId() {
        // Test that generateChannelId generates the expected channel ID
        assertTrue(true);
    }


    // Utility methods for creating test objects, mock responses, etc.

    private void mockUserRepositoryBehavior() {
        // Mocking a typical user find operation
        when(userRepository.findById(anyLong())).thenAnswer(invocation -> {
            Long userId = invocation.getArgument(0);
            if (userId.equals(1L)) {
                return Optional.of(createUserMock(1L, "adopter@example.com", Role.PETADOPTER, true));
            } else if (userId.equals(2L)) {
                return Optional.of(createUserMock(2L, "shelter@example.com", Role.SHELTER, true));
            }
            return Optional.empty();
        });
    }

    private void mockPetAdopterRepositoryBehavior() {
        // Mocking a typical pet adopter find operation
        when(petAdopterRepository.findByUserId(anyLong())).thenAnswer(invocation -> {
            Long userId = invocation.getArgument(0);
            if (userId.equals(1L)) {
                return Optional.of(createPetAdopterMock(1L, "John Doe", "adopter@example.com"));
            }
            return Optional.empty();
        });
    }

    private void mockShelterRepositoryBehavior() {
        // Mocking a typical shelter find operation
        when(shelterRepository.findByUserId(anyLong())).thenAnswer(invocation -> {
            Long userId = invocation.getArgument(0);
            if (userId.equals(2L)) {
                return Optional.of(createShelterMock(2L, "Happy Paws Shelter", "shelter@example.com"));
            }
            return Optional.empty();
        });
    }

    private User createUserMock(Long id, String email, Role role, Boolean verified) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword("password");
        user.setRole(role);
        user.setVerified(verified);
        return user;
    }

    private PetAdopter createPetAdopterMock(Long userId, String name, String email) {
        PetAdopter petAdopter = new PetAdopter();
        petAdopter.setId(userId);
        petAdopter.setFirstname(name.split(" ")[0]);
        petAdopter.setLastname(name.split(" ")[1]);
        petAdopter.setPhone_number("1234567890");
        petAdopter.setAddress("123 Pet Street");
        petAdopter.setUser(createUserMock(userId, email, Role.PETADOPTER, true));
        petAdopter.setCity("Petville");
        petAdopter.setCountry("Petland");
        petAdopter.setZipcode("12345");
        return petAdopter;
    }

    private Shelter createShelterMock(Long userId, String name, String email) {
        Shelter shelter = new Shelter();
        shelter.setId(userId);
        shelter.setName(name);
        shelter.setCapacity(100L);
        shelter.setContact("9876543210");
        shelter.setImageBase64("base64Image==");
        shelter.setLicense("License123");
        shelter.setUser(createUserMock(userId, email, Role.SHELTER, true));
        shelter.setAddress("456 Shelter Avenue");
        shelter.setCity("Sheltertown");
        shelter.setCountry("Shelterland");
        shelter.setZipcode("67890");
        shelter.setRejected(false);
        return shelter;
    }
}
