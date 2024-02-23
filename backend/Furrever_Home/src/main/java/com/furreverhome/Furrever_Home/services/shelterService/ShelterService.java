package com.furreverhome.Furrever_Home.services.shelterService;

import com.furreverhome.Furrever_Home.dto.PetDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;

public interface ShelterService {
    PetDto registerPet(RegisterPetRequest registerPetRequest);
}
