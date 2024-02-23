package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.profile.*;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileManagementServiceTest {
    @Mock
    private PetAdopterRepository petAdopterRepository;

    @Mock
    private ShelterRepository shelterRepository;

    @Mock
    private UserProfileMapper userProfileMapper;

    @InjectMocks
    private UserProfileManagementService userProfileManagementService;

    @Test
    void updateUserProfileWhenUserExists() {
        // Arrange
        Long userId = 1L;
        UpdateUserProfileRequestDto requestDto = new UpdateUserProfileRequestDto("John", "Doe", "1234567890", "123 Main St");
        PetAdopter petAdopter = new PetAdopter();
        PetAdopter updatedPetAdopter = new PetAdopter();
        UpdateUserProfileResponseDto expectedResponse = new UpdateUserProfileResponseDto(userId, "John", "Doe", "1234567890", "123 Main St");

        when(petAdopterRepository.findByUserId(userId)).thenReturn(Optional.of(petAdopter));
        when(userProfileMapper.toPetAdopterEntity(requestDto, petAdopter)).thenReturn(updatedPetAdopter);
        when(petAdopterRepository.save(updatedPetAdopter)).thenReturn(updatedPetAdopter);
        when(userProfileMapper.toUpdateUserProfileResponseDto(updatedPetAdopter)).thenReturn(expectedResponse);

        // Act
        UpdateUserProfileResponseDto actualResponse = userProfileManagementService.updateUserProfile(userId, requestDto);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(petAdopterRepository).findByUserId(userId);
        verify(userProfileMapper).toPetAdopterEntity(requestDto, petAdopter);
        verify(petAdopterRepository).save(updatedPetAdopter);
        verify(userProfileMapper).toUpdateUserProfileResponseDto(updatedPetAdopter);
    }

    @Test
    void updateUserProfileWhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        UpdateUserProfileRequestDto requestDto = new UpdateUserProfileRequestDto("John", "Doe", "1234567890", "123 Main St");

        when(petAdopterRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            userProfileManagementService.updateUserProfile(userId, requestDto);
        });

        verify(petAdopterRepository).findByUserId(userId);
        verify(userProfileMapper, never()).toPetAdopterEntity(any(), any());
        verify(petAdopterRepository, never()).save(any());
    }


    @Test
    void getUserProfileWhenUserExists() {
        // Arrange
        Long userId = 1L;
        PetAdopter petAdopter = new PetAdopter();
        UpdateUserProfileResponseDto expectedResponse = new UpdateUserProfileResponseDto(userId, "John", "Doe", "1234567890", "123 Main St");

        when(petAdopterRepository.findByUserId(userId)).thenReturn(Optional.of(petAdopter));
        when(userProfileMapper.toUpdateUserProfileResponseDto(petAdopter)).thenReturn(expectedResponse);

        // Act
        UpdateUserProfileResponseDto actualResponse = userProfileManagementService.getUserProfile(userId);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(petAdopterRepository).findByUserId(userId);
        verify(userProfileMapper).toUpdateUserProfileResponseDto(petAdopter);
    }

    @Test
    void getUserProfileWhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;

        when(petAdopterRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userProfileManagementService.getUserProfile(userId));

        verify(petAdopterRepository).findByUserId(userId);
    }

    @Test
    void updateShelterProfileWhenShelterExists() {
        // Arrange
        Long shelterId = 1L;
        UpdateShelterProfileRequestDto requestDto = new UpdateShelterProfileRequestDto("Shelter", "Location", 100L, "Contact", "ImageBase64", "License");
        Shelter shelter = new Shelter();
        Shelter updatedShelter = new Shelter();
        UpdateShelterProfileResponseDto expectedResponse = new UpdateShelterProfileResponseDto(shelterId, "Shelter", "Location", 100L, "Contact", "ImageBase64", "License");

        when(shelterRepository.findByUserId(shelterId)).thenReturn(Optional.of(shelter));
        when(userProfileMapper.toShelterEntity(requestDto, shelter)).thenReturn(updatedShelter);
        when(shelterRepository.save(updatedShelter)).thenReturn(updatedShelter);
        when(userProfileMapper.toUpdateShelterProfileResponseDto(updatedShelter)).thenReturn(expectedResponse);

        // Act
        UpdateShelterProfileResponseDto actualResponse = userProfileManagementService.updateShelterProfile(shelterId, requestDto);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(shelterRepository).findByUserId(shelterId);
        verify(userProfileMapper).toShelterEntity(requestDto, shelter);
        verify(shelterRepository).save(updatedShelter);
        verify(userProfileMapper).toUpdateShelterProfileResponseDto(updatedShelter);
    }

    @Test
    void updateShelterProfileWhenShelterDoesNotExist() {
        // Arrange
        Long shelterId = 1L;
        UpdateShelterProfileRequestDto requestDto = new UpdateShelterProfileRequestDto("Shelter", "Location", 100L, "Contact", "ImageBase64", "License");

        when(shelterRepository.findByUserId(shelterId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userProfileManagementService.updateShelterProfile(shelterId, requestDto));

        verify(shelterRepository).findByUserId(shelterId);
        verify(userProfileMapper, never()).toShelterEntity(any(), any());
        verify(shelterRepository, never()).save(any());
    }

    @Test
    void getShelterProfileWhenShelterExists() {
        // Arrange
        Long shelterId = 1L;
        Shelter shelter = new Shelter();
        UpdateShelterProfileResponseDto expectedResponse = new UpdateShelterProfileResponseDto(shelterId, "Shelter", "Location", 100L, "Contact", "ImageBase64", "License");

        when(shelterRepository.findByUserId(shelterId)).thenReturn(Optional.of(shelter));
        when(userProfileMapper.toUpdateShelterProfileResponseDto(shelter)).thenReturn(expectedResponse);

        // Act
        UpdateShelterProfileResponseDto actualResponse = userProfileManagementService.getShelterProfile(shelterId);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(shelterRepository).findByUserId(shelterId);
        verify(userProfileMapper).toUpdateShelterProfileResponseDto(shelter);
    }

    @Test
    void getShelterProfileWhenShelterDoesNotExist() {
        // Arrange
        Long shelterId = 1L;

        when(shelterRepository.findByUserId(shelterId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userProfileManagementService.getShelterProfile(shelterId));

        verify(shelterRepository).findByUserId(shelterId);
    }
}
