package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.services.shelterService.ShelterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelter")
@RequiredArgsConstructor
public class ShelterController {

    private final ShelterServiceImpl shelterService;
    @PostMapping("/registerPet")
    public ResponseEntity<PetDto> registerPetInShelter(@RequestBody RegisterPetRequest registerPetRequest){
        return ResponseEntity.ok(shelterService.registerPet(registerPetRequest));
    }

    @PostMapping("/editPet/{petID}")
    public ResponseEntity<PetDto> editPetInShelter(@PathVariable Long petID, @RequestBody RegisterPetRequest registerPetRequest){
        return ResponseEntity.ok(shelterService.editPet(petID,registerPetRequest));
    }

    @DeleteMapping("/deletePet/{petID}")
    public ResponseEntity<GenericResponse> deletePetInShelter(@PathVariable Long petID){
        return ResponseEntity.ok(shelterService.deletePet(petID));
    }

    @GetMapping("/{shelterID}/pets")
    public ResponseEntity<List<PetDto>> getPetInShelter(@PathVariable Long shelterID){
        return ResponseEntity.ok(shelterService.getPetsForShelter(shelterID));
    }

    @GetMapping("/shelter/{petId}/{status}")
    public ResponseEntity<?> changeAdoptedStatus(@PathVariable Long petId, @PathVariable String status) {
        boolean success = shelterService.changeAdoptedStatus(petId, status);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
