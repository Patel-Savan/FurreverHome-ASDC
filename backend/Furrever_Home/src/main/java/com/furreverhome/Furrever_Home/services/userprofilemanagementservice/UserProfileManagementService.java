package com.furreverhome.Furrever_Home.services.userprofilemanagementservice;

import com.furreverhome.Furrever_Home.dto.profile.*;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileManagementService {

    private final PetAdopterRepository petAdopterRepository;
    private final ShelterRepository shelterRepository;
    private final UserProfileMapper userProfileMapper;

    public UpdateUserProfileResponseDto updateUserProfile(Long userId, UpdateUserProfileRequestDto updateUserProfileRequestDto) {
        PetAdopter petAdopter;
        petAdopter = petAdopterRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        PetAdopter updatedPetAdopter = userProfileMapper.toPetAdopterEntity(updateUserProfileRequestDto, petAdopter);
        petAdopterRepository.save(updatedPetAdopter);

        return userProfileMapper.toUpdateUserProfileResponseDto(updatedPetAdopter);
    }

    public UpdateUserProfileResponseDto getUserProfile(Long userId) {
        PetAdopter petAdopter;
        petAdopter = petAdopterRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return userProfileMapper.toUpdateUserProfileResponseDto(petAdopter);
    }

    public UpdateShelterProfileResponseDto updateShelterProfile(Long shelterId, UpdateShelterProfileRequestDto updateShelterProfileRequestDto) {
        Shelter shelter;
        shelter = shelterRepository.findByUserId(shelterId)
                .orElseThrow(() -> new EntityNotFoundException("Shelter not found"));

        Shelter updatedShelter = userProfileMapper.toShelterEntity(updateShelterProfileRequestDto, shelter);
        shelterRepository.save(updatedShelter);

        return userProfileMapper.toUpdateShelterProfileResponseDto(updatedShelter);
    }

    public UpdateShelterProfileResponseDto getShelterProfile(Long shelterId) {
        Shelter shelter;
        shelter = shelterRepository.findByUserId(shelterId)
                .orElseThrow(() -> new EntityNotFoundException("Shelter not found"));

        return userProfileMapper.toUpdateShelterProfileResponseDto(shelter);
    }

}
