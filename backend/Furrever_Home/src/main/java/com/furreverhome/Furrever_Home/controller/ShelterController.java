package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.PetDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.services.shelterService.ShelterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shelter")
@RequiredArgsConstructor
public class ShelterController {

    private final ShelterServiceImpl shelterService;
    @PostMapping("/registerPet")
    public ResponseEntity<PetDto> registerPetInShelter(@RequestBody RegisterPetRequest registerPetRequest){
        return ResponseEntity.ok(shelterService.registerPet(registerPetRequest));
    }
}
