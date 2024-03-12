package com.furreverhome.Furrever_Home.services.petadopterservices;

import com.furreverhome.Furrever_Home.dto.lostpet.LostPetDto;
import com.furreverhome.Furrever_Home.dto.lostpet.RegisterLostPetDto;
import com.furreverhome.Furrever_Home.entities.LostPet;

import java.util.List;

public interface LostPetService {
    LostPet registerLostPet(String authorizationHeader, RegisterLostPetDto registerLostPetDto);

    List<LostPetDto> getAllLostPets();
}
