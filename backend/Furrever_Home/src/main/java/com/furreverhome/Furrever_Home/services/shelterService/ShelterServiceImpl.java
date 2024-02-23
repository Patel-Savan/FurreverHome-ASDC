package com.furreverhome.Furrever_Home.services.shelterService;

import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.PetDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.repository.PetRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShelterServiceImpl implements ShelterService{

    @Autowired
    PetRepository petRepository;

    @Autowired
    ShelterRepository shelterRepository;

    public PetDto registerPet(RegisterPetRequest registerPetRequest){
        Pet pet = new Pet();
        pet.setType(registerPetRequest.getType());
        pet.setBreed(registerPetRequest.getBreed());
        pet.setColour(registerPetRequest.getColour());
        pet.setGender(registerPetRequest.getGender());
        pet.setBirthdate(registerPetRequest.getBirthdate());
        pet.setPetImage(registerPetRequest.getPetImage());

        Shelter shelter = shelterRepository.findById(registerPetRequest.getShelter()).orElse(null);

        if (shelter != null){
            pet.setShelter(shelter);
        }
        petRepository.save(pet);

        PetDto petDto = new PetDto();
        petDto.setPetID(pet.getPetID());
        petDto.setType(pet.getType());
        petDto.setBreed(pet.getBreed());
        petDto.setColour(pet.getColour());
        petDto.setGender(pet.getGender());
        petDto.setBirthdate(pet.getBirthdate());
        petDto.setPetImage(pet.getPetImage());
        petDto.setShelter(pet.getShelter());
        return petDto;
    }
}
