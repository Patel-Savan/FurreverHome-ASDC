package com.furreverhome.Furrever_Home.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furreverhome.Furrever_Home.dto.profile.UpdateShelterProfileRequestDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateShelterProfileResponseDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateUserProfileRequestDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateUserProfileResponseDto;
import com.furreverhome.Furrever_Home.exception.GlobalExceptionHandler;
import com.furreverhome.Furrever_Home.services.UserProfileManagementService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class UserProfileManagementControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserProfileManagementService userProfileManagementService;

    @InjectMocks
    private UserProfileManagementController userProfileManagementController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileManagementController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testUpdateUserProfile() throws Exception {
        Long userId = 1L;
        UpdateUserProfileRequestDto requestDto = new UpdateUserProfileRequestDto("John", "Doe", "1234567890", "123 Main St");
        UpdateUserProfileResponseDto responseDto = new UpdateUserProfileResponseDto(userId, "John", "Doe", "1234567890", "123 Main St");

        when(userProfileManagementService.updateUserProfile(anyLong(), any(UpdateUserProfileRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(userProfileManagementService).updateUserProfile(anyLong(), any(UpdateUserProfileRequestDto.class));
    }

    @Test
    void testGetUserProfileSuccess() throws Exception {
        Long userId = 1L;
        UpdateUserProfileResponseDto responseDto = new UpdateUserProfileResponseDto(userId, "John", "Doe", "1234567890", "123 Main St");

        given(userProfileManagementService.getUserProfile(eq(userId))).willReturn(responseDto);

        mockMvc.perform(get("/api/users/{userId}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(userProfileManagementService).getUserProfile(eq(userId));
    }

    @Test
    void testGetUserProfileNotFound() throws Exception {
        Long userId = 2L;
        given(userProfileManagementService.getUserProfile(eq(userId))).willThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/{userId}", userId))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userProfileManagementService).getUserProfile(eq(userId));
    }

    @Test
    void testUpdateShelterProfileSuccess() throws Exception {
        Long shelterId = 1L;
        UpdateShelterProfileRequestDto requestDto = new UpdateShelterProfileRequestDto("Shelter", "Location", 100L, "Contact", "ImageBase64", "License");
        UpdateShelterProfileResponseDto responseDto = new UpdateShelterProfileResponseDto(shelterId, "Shelter", "Location", 100L, "Contact", "ImageBase64", "License");

        given(userProfileManagementService.updateShelterProfile(eq(shelterId), any(UpdateShelterProfileRequestDto.class))).willReturn(responseDto);

        mockMvc.perform(put("/api/shelters/{shelterId}", shelterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Shelter"))
                .andExpect(jsonPath("$.location").value("Location"));

        verify(userProfileManagementService).updateShelterProfile(eq(shelterId), any(UpdateShelterProfileRequestDto.class));
    }

    @Test
    void testUpdateShelterProfileNotFound() throws Exception {
        Long shelterId = 2L;
        UpdateShelterProfileRequestDto requestDto = new UpdateShelterProfileRequestDto("Shelter", "Location", 100L, "Contact", "ImageBase64", "License");

        given(userProfileManagementService.updateShelterProfile(eq(shelterId), any(UpdateShelterProfileRequestDto.class))).willThrow(new EntityNotFoundException("Shelter not found"));

        mockMvc.perform(put("/api/shelters/{shelterId}", shelterId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userProfileManagementService).updateShelterProfile(eq(shelterId), any(UpdateShelterProfileRequestDto.class));
    }

    @Test
    void testUpdateUserProfileUnauthorized() throws Exception {
        Long userId = 1L;
        UpdateUserProfileRequestDto requestDto = new UpdateUserProfileRequestDto("John", "Doe", "1234567890", "123 Main St");

        given(userProfileManagementService.updateUserProfile(eq(userId), any(UpdateUserProfileRequestDto.class))).willThrow(new AccessDeniedException("Unauthorized"));

        mockMvc.perform(put("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        verify(userProfileManagementService).updateUserProfile(eq(userId), any(UpdateUserProfileRequestDto.class));
    }
}
