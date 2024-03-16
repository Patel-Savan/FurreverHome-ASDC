package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.PetAdopterDto;
import com.furreverhome.Furrever_Home.dto.petadopter.SearchPetDto;
import com.furreverhome.Furrever_Home.dto.petadopter.SearchShelterDto;
import com.furreverhome.Furrever_Home.dto.petadopter.ShelterResponseDto;
import com.furreverhome.Furrever_Home.services.petadopterservices.PetAdopterService;
import com.furreverhome.Furrever_Home.services.petservice.PetService;
import com.furreverhome.Furrever_Home.services.petservice.PetServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petadopter")
@RequiredArgsConstructor
public class PetAdopterController {

    private final PetAdopterService petAdopterService;
    private final PetService petService;
    @GetMapping("/shelters")
    public ResponseEntity<List<ShelterResponseDto>> getAllShelters() {
        List<ShelterResponseDto> shelterResponseDtoList = petAdopterService.getAllShelter();
        return ResponseEntity.ok(shelterResponseDtoList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PetAdopterDto> getPetAdopterByUser (@PathVariable Long userId) {
        PetAdopterDto petAdopterDto = petAdopterService.getPetAdopterDetailsById(userId);
        if(petAdopterDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(petAdopterDto);
    }

    @PostMapping("/searchshelter")
    public ResponseEntity<?> searchShelter(@RequestBody SearchShelterDto searchShelterDto) {
        return ResponseEntity.ok(petAdopterService.searchShelter(searchShelterDto));
    }

    @PostMapping("/searchpet")
    public ResponseEntity<?> searchPet(@RequestBody SearchPetDto searchPetDto) {
        return ResponseEntity.ok(petAdopterService.searchPet(searchPetDto));
    }

    @PostMapping("/pet/adopt")
    public ResponseEntity<?> adoptPetRequest(@RequestBody PetAdoptionRequestDto petAdoptionRequestDto){
        return ResponseEntity.ok(petAdopterService.adoptPetRequest(petAdoptionRequestDto));
    }

    @GetMapping("/pets/{petID}")
    public ResponseEntity<PetDto> getPetInfo(@PathVariable Long petID){
        return ResponseEntity.ok(petService.getPetInfo(petID));
    }

    @GetMapping("/pet/adopt/requestexists")
    public ResponseEntity<?> requestExists( @RequestParam("petID") Long petID, @RequestParam("petAdopterID") Long petAdopterID){
        boolean success = petAdopterService.requestExists(petID,petAdopterID);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
