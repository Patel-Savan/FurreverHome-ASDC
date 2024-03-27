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

    /**
     * Service method to register a new pet.
     * @param registerPetRequest Request object containing information to register the pet.
     * @return PetDto representing the registered pet details.
     */
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

    /**
     * Service method to edit pet details.
     * @param petID ID of the pet to edit.
     * @param updatePetRequest Request object containing updated pet information.
     * @return PetDto representing the updated pet details.
     * @throws RuntimeException if the pet with the specified ID is not found.
     */
    public PetDto editPet(Long petID, RegisterPetRequest updatePetRequest){
        Optional<Pet> optionalPet = petRepository.findById(petID);
        if (optionalPet.isPresent()){
            Pet pet = optionalPet.get();
            updatePetFields(pet, updatePetRequest);
            petRepository.save(pet);
            return mapPetToDto(pet);
        }else {
            throw new RuntimeException("Pet with ID "+petID+" not found");
        }
    }

    /**
     * helper method to check and update the required pet fields
     * @param pet
     * @param updatePetRequest
     */
    private void updatePetFields(Pet pet, RegisterPetRequest updatePetRequest) {
        if (updatePetRequest.getType() != null) {
            pet.setType(updatePetRequest.getType());
        }
        if (updatePetRequest.getBreed() != null) {
            pet.setBreed(updatePetRequest.getBreed());
        }
        if (updatePetRequest.getColour() != null) {
            pet.setColour(updatePetRequest.getColour());
        }
        if (updatePetRequest.getGender() != null) {
            pet.setGender(updatePetRequest.getGender());
        }
        if (updatePetRequest.getBirthdate() != null) {
            pet.setBirthdate(updatePetRequest.getBirthdate());
        }
        if (updatePetRequest.getPetImage() != null) {
            pet.setPetImage(updatePetRequest.getPetImage());
        }
        if (updatePetRequest.getPetMedicalHistory() != null) {
            pet.setPetMedicalHistory(updatePetRequest.getPetMedicalHistory());
        }
    }

    /**
     * Service method to delete a pet from the database.
     * @param petId ID of the pet to delete.
     * @return GenericResponse indicating the success or failure of the deletion operation.
     * @throws RuntimeException if the pet with the specified ID is not found.
     */
    public GenericResponse deletePet(Long petId) {
        if (petRepository.existsById(petId)) {
            petRepository.deleteById(petId);
            return new GenericResponse("Pet deleted.");
        } else {
            throw new RuntimeException("Pet with ID " + petId + " not found");
        }
    }

    /**
     * Service method to retrieve a list of pets belonging to a specific shelter.
     * @param shelterID ID of the shelter whose pets are to be retrieved.
     * @return List of PetDto representing pets belonging to the specified shelter.
     * @throws RuntimeException if the shelter with the specified ID is not found.
     */
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

    /**
     * util method to map pet attributes to petDto
     * @param pet
     * @return mapped petDto
     */
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

    /**
     * Service method to change the adoption status of a pet.
     * @param petId ID of the pet whose adoption status is to be changed.
     * @param status New adoption status ("Adopted" or "Not Adopted").
     * @return true if the adoption status is successfully changed, false otherwise.
     */
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

    /**
     * Service method to retrieve all adoption requests made for a particular pet.
     * @param petID ID of the pet for which adoption requests are to be retrieved.
     * @return PetAdoptionRequestResponseDto containing information about adoption requests for the specified pet.
     * @throws RuntimeException if the pet with the specified ID is not found.
     */
    public PetAdoptionRequestResponseDto getPetAdoptionRequests(Long petID){
        //checking whether the pet with given id is present or not
        Optional<Pet> optionalPet = petRepository.findById(petID);
        if (optionalPet.isPresent()){
            Pet pet = optionalPet.get();
            //getting a list of all the petadopters (users) that requested to adopt the pet
            List<PetAdopter> petAdopters = adopterPetRequestsRepository.findPetAdoptersByPet(pet);
            PetAdoptionRequestResponseDto petAdoptionRequestResponseDto = new PetAdoptionRequestResponseDto();
            petAdoptionRequestResponseDto.setPetID(petID);
            petAdoptionRequestResponseDto.setPetAdopters(petAdopters);
            return petAdoptionRequestResponseDto;
        }else {
            throw new RuntimeException("Requests with petID " + petID + " not found");
        }
    }
}
