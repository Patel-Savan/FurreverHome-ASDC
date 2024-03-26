package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestResponseDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetVaccineDto;
import com.furreverhome.Furrever_Home.dto.petadopter.ShelterResponseDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.services.petservice.PetService;
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
    private final PetService petService;

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

    @GetMapping("/{petID}")
    public ResponseEntity<PetDto> getPetInfo(@PathVariable Long petID){
        return ResponseEntity.ok(petService.getPetInfo(petID));
    }

    @GetMapping("/{petID}/adoptionrequests")
    public ResponseEntity<PetAdoptionRequestResponseDto> getPetAdoptionRequests(@PathVariable Long petID){
        return ResponseEntity.ok(shelterService.getPetAdoptionRequests(petID));
    }

    @PostMapping("/{petID}/addvaccine")
    public ResponseEntity<GenericResponse> addVaccine(@RequestBody PetVaccineDto petVaccineDto , @PathVariable Long petID){
        return ResponseEntity.ok(petService.addVaccinationDetails(petVaccineDto, petID));
    }

    @GetMapping("/single/{userId}")
    public ResponseEntity<ShelterResponseDto> getShelterByUser (@PathVariable Long userId) {
        ShelterResponseDto shelterResponseDto = shelterService.getShelterDetailsById(userId);
        if(shelterResponseDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(shelterResponseDto);
    }
}
