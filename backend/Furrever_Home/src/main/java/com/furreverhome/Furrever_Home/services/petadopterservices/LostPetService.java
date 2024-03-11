package com.furreverhome.Furrever_Home.services.petadopterservices;

import com.furreverhome.Furrever_Home.dto.lostpet.RegisterLostPetDto;
import com.furreverhome.Furrever_Home.entities.LostPet;

public interface LostPetService {
    LostPet registerLostPet(String authorizationHeader, RegisterLostPetDto registerLostPetDto);
}
