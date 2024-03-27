package com.furreverhome.Furrever_Home.services.petadopterservices.impl;

import com.furreverhome.Furrever_Home.dto.lostpet.LostPetDto;
import com.furreverhome.Furrever_Home.dto.lostpet.LostPetResponseDtoListDto;
import com.furreverhome.Furrever_Home.dto.lostpet.RegisterLostPetDto;
import com.furreverhome.Furrever_Home.dto.petadopter.SearchPetDto;
import com.furreverhome.Furrever_Home.entities.LostPet;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.exception.UserNotFoundException;
import com.furreverhome.Furrever_Home.repository.LostPetRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.JwtService;
import com.furreverhome.Furrever_Home.services.petadopterservices.LostPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LostPetServiceImpl implements LostPetService {

    private final UserRepository userRepository;
    private final LostPetRepository lostPetRepository;
    private final JwtService jwtService;
    private final int authHeaderSubstring = 7;
    @Override
    public LostPet registerLostPet(String authorizationHeader, RegisterLostPetDto registerLostPetDto) {
        String jwt = authorizationHeader.substring(authHeaderSubstring);
        String username = jwtService.extractUserName(jwt);
        Optional<User> user = userRepository.findByEmail(username);
        if(user.isPresent()) {
            LostPet lostPet = new LostPet();
            lostPet.setBreed(registerLostPetDto.getBreed());
            lostPet.setColour(registerLostPetDto.getColour());
            lostPet.setGender(registerLostPetDto.getGender());
            lostPet.setType(registerLostPetDto.getType());
            lostPet.setPetImage(registerLostPetDto.getPetImage());
            lostPet.setPhone(registerLostPetDto.getPhone());
            lostPet.setEmail(registerLostPetDto.getEmail());
            lostPet.setUser(user.get());
            lostPetRepository.save(lostPet);
            return lostPet;
        } else throw new UserNotFoundException("User not found");
    }


    @Override
    public List<LostPetDto> getAllLostPets() {
        return lostPetRepository.findAll().stream().map(LostPet::getLostPetDto).collect(Collectors.toList());
    }


    @Override
    public LostPetResponseDtoListDto searchLostPet(SearchPetDto searchPetDto) {
        LostPet lostPet = new LostPet();
        lostPet.setBreed(searchPetDto.getBreed());
        lostPet.setType(searchPetDto.getType());
        lostPet.setGender(searchPetDto.getGender());
        lostPet.setColour(searchPetDto.getColor());

        ExampleMatcher exampleMatcher =
                ExampleMatcher.matchingAll()
                        .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("breed", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("age", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("colour", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("gender", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<LostPet> petExample = Example.of(lostPet, exampleMatcher);
        List<LostPet> lostPetList = lostPetRepository.findAll(petExample);
        LostPetResponseDtoListDto lostPetResponseDtoListDto = new LostPetResponseDtoListDto();
        lostPetResponseDtoListDto.setLostPetDtoList(lostPetList.stream().map(LostPet::getLostPetDto).collect(Collectors.toList()));
        return lostPetResponseDtoListDto;
    }

    @Override
    public LostPetResponseDtoListDto getLostPetListByUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<LostPet> lostPetList = lostPetRepository.findByUser(user);
            if (!lostPetList.isEmpty()) {
                LostPetResponseDtoListDto lostPetResponseDtoListDto = new LostPetResponseDtoListDto();
                lostPetResponseDtoListDto.setLostPetDtoList(lostPetList.stream().map(LostPet::getLostPetDto).collect(Collectors.toList()));
                return lostPetResponseDtoListDto;
            } else  throw new RuntimeException("No lostpets found for this user");
        }else throw new UserNotFoundException("User not found");
    }

    @Override
    public boolean updateLostPetDetails(LostPetDto lostPetDto) {
        Optional<LostPet> optionalLostPet = lostPetRepository.findById(lostPetDto.getId());
        if(optionalLostPet.isPresent()) {
            LostPet lostPet = optionalLostPet.get();
            lostPet.setType(lostPetDto.getType());
            lostPet.setPetImage(lostPetDto.getPetImage());
            lostPet.setColour(lostPetDto.getColour());
            lostPet.setGender(lostPetDto.getGender());
            lostPet.setPhone(lostPetDto.getPhone());
            lostPet.setEmail(lostPetDto.getEmail());
            lostPet.setBreed(lostPetDto.getBreed());
            lostPetRepository.save(lostPet);
            return true;
        }
        return false;
    }
}
