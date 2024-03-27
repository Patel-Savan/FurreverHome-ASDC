package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.profile.UpdateShelterProfileRequestDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateShelterProfileResponseDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateUserProfileRequestDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateUserProfileResponseDto;
import com.furreverhome.Furrever_Home.services.userprofilemanagementservice.UserProfileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserProfileManagementController {

    private final UserProfileManagementService userProfileManagementService;

    @PutMapping("/users/{userId}")
    ResponseEntity<UpdateUserProfileResponseDto> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UpdateUserProfileRequestDto updateUserProfileRequestDto) {
        return ResponseEntity.ok(userProfileManagementService.updateUserProfile(userId, updateUserProfileRequestDto));

    }

    @GetMapping("/users/{userId}")
    ResponseEntity<UpdateUserProfileResponseDto> updateUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileManagementService.getUserProfile(userId));
    }

    @PutMapping("/shelters/{shelterId}")
    ResponseEntity<UpdateShelterProfileResponseDto> updateShelterProfile(
            @PathVariable Long shelterId,
            @RequestBody UpdateShelterProfileRequestDto updateShelterProfileRequestDto) {
        return ResponseEntity.ok(userProfileManagementService.updateShelterProfile(shelterId, updateShelterProfileRequestDto));
    }

    @GetMapping("/shelters/{shelterId}")
    ResponseEntity<UpdateShelterProfileResponseDto> getShelterProfile(@PathVariable Long shelterId) {
        return ResponseEntity.ok(userProfileManagementService.getShelterProfile(shelterId));
    }
}
