package com.furreverhome.Furrever_Home.services.petadopterservices.impl;

import com.furreverhome.Furrever_Home.dto.PetAdopterDto;
import com.furreverhome.Furrever_Home.dto.petadopter.*;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.exception.UserNotFoundException;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
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

//    private final PetRepository petRepository;

    @Override
    public List<ShelterResponseDto> getAllShelter() {
        return shelterRepository.findAll().stream().map(Shelter::getShelterResponseDto).collect(Collectors.toList());
    }

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

    @Override
    public ShelterResponseDtoListDto searchShelter(SearchShelterDto searchShelterDto) {
        Shelter shelter = new Shelter();
        shelter.setName(searchShelterDto.getName());
        shelter.setCity(searchShelterDto.getCity());
        shelter.setCapacity(searchShelterDto.getCapacity());

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
}
