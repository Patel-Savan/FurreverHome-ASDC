package com.furreverhome.Furrever_Home.services.petservice;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetVaccineDto;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetVaccination;
import com.furreverhome.Furrever_Home.repository.PetRepository;
import com.furreverhome.Furrever_Home.repository.PetVaccinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService{

    private final PetRepository petRepository;
    @Autowired
    private final PetVaccinationRepository petVaccinationRepository;

    public PetDto getPetInfo(Long petID){
        Optional<Pet> optionalPet = petRepository.findById(petID);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            return mapPetToDto(pet);
        } else {
            throw new RuntimeException("Pet not found.");
        }
    }

    public GenericResponse addVaccinationDetails(PetVaccineDto petVaccineDto, Long petID){
        Optional<Pet> optionalPet = petRepository.findById(petID);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            PetVaccination petVaccination = new PetVaccination();
            if (petVaccinationRepository.existsByPetAndVaccineName(pet, petVaccineDto.getVaccineName())){
                return new GenericResponse("already present");
            } else {
                petVaccination.setPet(pet);
                petVaccination.setVaccineName(petVaccineDto.getVaccineName());
                petVaccination.setDate(petVaccineDto.getDate());
                petVaccination.setVaccineGiven(petVaccineDto.isVaccineGiven());
                petVaccinationRepository.save(petVaccination);
                return new GenericResponse("Vaccination added.");
            }
        } else {
            throw new RuntimeException("Pet with petID "+petID+" not found.");
        }
    }


    //---------------------------UTILS----------------------------

    private PetDto mapPetToDto(Pet pet) {
        PetDto petDto = new PetDto();
        petDto.setPetID(pet.getPetID());
        petDto.setType(pet.getType());
        petDto.setBreed(pet.getBreed());
        petDto.setColour(pet.getColour());
        petDto.setGender(pet.getGender());
        petDto.setBirthdate(pet.getBirthdate());
        petDto.setPetImage(pet.getPetImage());
        petDto.setPetMedicalHistory(petDto.getPetMedicalHistory());
        List<PetVaccination> petVaccinationList = petVaccinationRepository.findByPet(pet);
        List<String> vaccineNameList = new ArrayList<>();
        for (PetVaccination petVaccine:petVaccinationList){
            vaccineNameList.add(petVaccine.getVaccineName());
        }
        petDto.setVaccineNameList(vaccineNameList);
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
