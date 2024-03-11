package com.furreverhome.Furrever_Home.services.petservice;

import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public PetDto getPetInfo(Long petID){
        Optional<Pet> optionalPet = petRepository.findById(petID);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            return mapPetToDto(pet);
        } else {
            throw new RuntimeException("Pet not found.");
        }
    }

//    public PetDto addVaccinationDetails(){
//
//    }

    private PetDto mapPetToDto(Pet pet) {
        PetDto petDto = new PetDto();
        petDto.setPetID(pet.getPetID());
        petDto.setType(pet.getType());
        petDto.setBreed(pet.getBreed());
        petDto.setColour(pet.getColour());
        petDto.setGender(pet.getGender());
        petDto.setBirthdate(pet.getBirthdate());
        petDto.setPetImage(pet.getPetImage());
        petDto.setShelter(pet.getShelter());
        petDto.setAdopted(pet.isAdopted());
        return petDto;
    }

//    private PetResponseDto mapPetToDto(Pet pet) {
//        PetResponseDto petResponseDto = new PetResponseDto();
//        petResponseDto.setPetId(pet.getPetID());
//        petResponseDto.setType(pet.getType());
//        petResponseDto.setBreed(pet.getBreed());
//        petResponseDto.setColor(pet.getColour());
//        petResponseDto.setGender(pet.getGender());
//        petResponseDto.setBirthdate(pet.getBirthdate());
//        petResponseDto.setPetImage(pet.getPetImage());
//        petResponseDto.setShelterName(shelter.getName());
//        petResponseDto.setShelterCity(shelter.getCity());
//        petResponseDto.setShelterContact(shelter.getContact());
//        petResponseDto.setAdopted(pet.isAdopted());
//        return petResponseDto;
//    }
}
