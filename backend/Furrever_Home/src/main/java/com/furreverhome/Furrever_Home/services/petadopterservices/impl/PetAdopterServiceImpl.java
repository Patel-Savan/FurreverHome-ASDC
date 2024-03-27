package com.furreverhome.Furrever_Home.services.petadopterservices.impl;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestDto;
import com.furreverhome.Furrever_Home.dto.PetAdopterDto;
import com.furreverhome.Furrever_Home.dto.petadopter.*;
import com.furreverhome.Furrever_Home.entities.*;
import com.furreverhome.Furrever_Home.exception.UserNotFoundException;
import com.furreverhome.Furrever_Home.repository.*;
import com.furreverhome.Furrever_Home.services.petadopterservices.PetAdopterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetAdopterServiceImpl implements PetAdopterService {

    private final ShelterRepository shelterRepository;

    private final PetAdopterRepository petAdopterRepository;

    private final UserRepository userRepository;

    private final PetRepository petRepository;

    private final AdopterPetRequestsRepository adopterPetRequestsRepository;

    /**
     * Retrieves a list of all shelters.
     * @return List of ShelterResponseDto representing all shelters.
     */
    @Override
    public List<ShelterResponseDto> getAllShelter() {
        return shelterRepository.findAll().stream().map(Shelter::getShelterResponseDto).collect(Collectors.toList());
    }

    /**
     * Retrieves details of a pet adopter by their user ID.
     * @param userId The ID of the user to retrieve pet adopter details for.
     * @return PetAdopterDto representing the details of the pet adopter.
     * @throws UserNotFoundException if the user or pet adopter is not found.
     */
    @Override
    public PetAdopterDto getPetAdopterDetailsById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<PetAdopter> optionalPetAdopter = petAdopterRepository.findByUser(user);

            if(optionalPetAdopter.isPresent()) {
                PetAdopter petAdopter = optionalPetAdopter.get();
                return petAdopter.getPetAdopterDto();
            } else {
                throw new UserNotFoundException("Pet Adopter Not Found");
            }
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    /**
     * Searches for shelters based on the provided criteria.
     * @param searchShelterDto Object containing search criteria for shelters.
     * @return ShelterResponseDtoListDto containing a list of ShelterResponseDto matching the search criteria.
     */
    @Override
    public ShelterResponseDtoListDto searchShelter(SearchShelterDto searchShelterDto) {
        Shelter shelter = new Shelter();
        shelter.setName(searchShelterDto.getName());
        shelter.setCity(searchShelterDto.getCity());
        shelter.setCapacity(searchShelterDto.getCapacity());
        shelter.setAccepted(true);
        shelter.setRejected(false);

        ExampleMatcher exampleMatcher =
                ExampleMatcher.matchingAll()
                        .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("capacity", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Shelter> shelterExample = Example.of(shelter, exampleMatcher);
        List<Shelter> shelterList = shelterRepository.findAll(shelterExample);
       ShelterResponseDtoListDto shelterResponseDtoListDto = new ShelterResponseDtoListDto();
       shelterResponseDtoListDto.setShelterResponseDtoList(shelterList.stream().map(Shelter::getShelterResponseDto).collect(Collectors.toList()));
       return shelterResponseDtoListDto;
    }

    /**
     * Searches for pets based on the provided criteria.
     * @param searchPetDto Object containing search criteria for pets.
     * @return PetResponseDtoListDto containing a list of PetResponseDto matching the search criteria.
     */
    @Override
    public PetResponseDtoListDto searchPet(SearchPetDto searchPetDto) {
        Pet pet = new Pet();
        pet.setAge(searchPetDto.getAge());
        pet.setBreed(searchPetDto.getBreed());
        pet.setType(searchPetDto.getType());
        pet.setGender(searchPetDto.getGender());
        pet.setColour(searchPetDto.getColor());

        ExampleMatcher exampleMatcher =
                ExampleMatcher.matchingAll()
                        .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("breed", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("age", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("gender", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Pet> petExample = Example.of(pet, exampleMatcher);
        List<Pet> petList = petRepository.findAll(petExample);
        PetResponseDtoListDto petResponseDtoListDto = new PetResponseDtoListDto();
        petResponseDtoListDto.setPetResponseDtoList(petList.stream().map(Pet::getPetResponseDto).collect(Collectors.toList()));
        return petResponseDtoListDto;
    }

    /**
     * Creates a request to adopt a pet.
     * @param petAdoptionRequestDto Object containing information about the pet adoption request.
     * @return GenericResponse indicating the status of the adoption request.
     */
    public GenericResponse adoptPetRequest(PetAdoptionRequestDto petAdoptionRequestDto) {
        Optional<PetAdopter> optionalPetAdopter= petAdopterRepository.findById(petAdoptionRequestDto.getPetAdopterID());
        Optional<Pet> optionalPet = petRepository.findById(petAdoptionRequestDto.getPetID());
        if (optionalPet.isPresent() && optionalPetAdopter.isPresent()) {
            Pet pet = optionalPet.get();
            if (pet.isAdopted() == true ) {
                return new GenericResponse("Pet already adopted");
            } else if (requestExists(petAdoptionRequestDto.getPetID(), petAdoptionRequestDto.getPetAdopterID())) {
                return new GenericResponse("Pet adoption request already exists.");
            } else {
                AdopterPetRequests adopterPetRequests = new AdopterPetRequests();
                PetAdopter petAdopter = optionalPetAdopter.get();
                adopterPetRequests.setPetAdopter(petAdopter);
                adopterPetRequests.setPet(pet);
                adopterPetRequestsRepository.save(adopterPetRequests);
                return new GenericResponse("Adoption Request Successful.");
            }
        } else {
            return new GenericResponse("PetAdopter or pet not found.");
        }
    }

    /**
     * Checks whether a pet adoption request already exists.
     * @param petID The ID of the pet.
     * @param petAdopterID The ID of the pet adopter.
     * @return true if the request exists, false otherwise.
     */
    public boolean requestExists(Long petID, Long petAdopterID){
        Optional<PetAdopter> optionalPetAdopter= petAdopterRepository.findById(petAdopterID);
        Optional<Pet> optionalPet = petRepository.findById(petID);
        if (optionalPet.isPresent() && optionalPetAdopter.isPresent()) {
            Pet pet = optionalPet.get();
            PetAdopter petAdopter = optionalPetAdopter.get();
            return adopterPetRequestsRepository.existsByPetAndPetAdopter(pet, petAdopter);
        }
        return false;
    }

}
