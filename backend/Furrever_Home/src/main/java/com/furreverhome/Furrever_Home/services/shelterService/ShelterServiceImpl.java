package com.furreverhome.Furrever_Home.services.shelterService;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestResponseDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.repository.AdopterPetRequestsRepository;
import com.furreverhome.Furrever_Home.repository.PetRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShelterServiceImpl implements ShelterService{

    @Autowired
    PetRepository petRepository;

    @Autowired
    ShelterRepository shelterRepository;

    @Autowired
    AdopterPetRequestsRepository adopterPetRequestsRepository;

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

        return mapPetToDto(pet);
    }

    public PetDto editPet(Long petID, RegisterPetRequest updatePetRequest){
        Optional<Pet> optionalPet = petRepository.findById(petID);
        if (optionalPet.isPresent()){
            Pet pet = optionalPet.get();

            if (updatePetRequest.getType()!=null){
                pet.setType(updatePetRequest.getType());
            }
            if (updatePetRequest.getBreed()!=null){
                pet.setBreed(updatePetRequest.getBreed());
            }
            if (updatePetRequest.getColour()!=null){
                pet.setColour(updatePetRequest.getColour());
            }
            if (updatePetRequest.getGender()!=null){
                pet.setGender(updatePetRequest.getGender());
            }
            if (updatePetRequest.getBirthdate()!=null){
                pet.setBirthdate(updatePetRequest.getBirthdate());
            }
            if (updatePetRequest.getPetImage()!=null){
                pet.setPetImage(updatePetRequest.getPetImage());
            }
            if (updatePetRequest.getPetMedicalHistory()!=null){
                pet.setPetMedicalHistory(updatePetRequest.getPetMedicalHistory());
            }
            petRepository.save(pet);

            return mapPetToDto(pet);
        }else {
            throw new RuntimeException("Pet with ID "+petID+" not found");
        }
    }

    public GenericResponse deletePet(Long petId) {
        if (petRepository.existsById(petId)) {
            petRepository.deleteById(petId);
            return new GenericResponse("Pet deleted.");
        } else {
            throw new RuntimeException("Pet with ID " + petId + " not found");
        }
    }

    public List<PetDto> getPetsForShelter(Long shelterID) {
        Optional<Shelter> optionalShelter = shelterRepository.findById(shelterID);
        if (optionalShelter.isPresent()){
            Shelter shelter = optionalShelter.get();
            List<Pet> pets = petRepository.findByShelter(shelter);
            return pets.stream()
                    .map(this::mapPetToDto)
                    .collect(Collectors.toList());
        }else {
            throw new RuntimeException("Shelter with ID " + shelterID + " not found");
        }
    }

    private PetDto mapPetToDto(Pet pet) {
        PetDto petDto = new PetDto();
        petDto.setPetID(pet.getPetID());
        petDto.setType(pet.getType());
        petDto.setBreed(pet.getBreed());
        petDto.setColour(pet.getColour());
        petDto.setGender(pet.getGender());
        petDto.setBirthdate(pet.getBirthdate());
        petDto.setPetImage(pet.getPetImage());
        petDto.setPetMedicalHistory(pet.getPetMedicalHistory());
        petDto.setShelter(pet.getShelter());
        petDto.setAdopted(pet.isAdopted());
        return petDto;
    }

    @Override
    public boolean changeAdoptedStatus(Long petId, String status) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if(optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            if(Objects.equals(status, "Adopted")) {
                pet.setAdopted(Boolean.TRUE);
            } else{
                pet.setAdopted(Boolean.FALSE);
            }
            petRepository.save(pet);
            return true;
        }
        return false;
    }

    public PetAdoptionRequestResponseDto getPetAdoptionRequests(Long petID){
        Optional<Pet> optionalPet = petRepository.findById(petID);
        if (optionalPet.isPresent()){
            Pet pet = optionalPet.get();
            List<PetAdopter> petAdopters = adopterPetRequestsRepository.findPetAdoptersByPet(pet);
            PetAdoptionRequestResponseDto petAdoptionRequestResponseDto = new PetAdoptionRequestResponseDto();
            petAdoptionRequestResponseDto.setPetID(petID);
            List<Map<Long, String>> petAdopterList = new ArrayList<>();
            for (PetAdopter petAdopter : petAdopters) {
                Map<Long, String> petAdopterMap = new HashMap<>();
                petAdopterMap.put(petAdopter.getId(), petAdopter.getFirstname()+" "+petAdopter.getLastname());
                petAdopterList.add(petAdopterMap);
            }
            petAdoptionRequestResponseDto.setPetAdopters(petAdopterList);
            return petAdoptionRequestResponseDto;
        }else {
            throw new RuntimeException("Requests with petID " + petID + " not found");
        }
    }
}
