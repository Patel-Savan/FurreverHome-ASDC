package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.PetAdopterDto;
import com.furreverhome.Furrever_Home.dto.lostpet.LostPetResponseDtoListDto;
import com.furreverhome.Furrever_Home.dto.lostpet.RegisterLostPetDto;
import com.furreverhome.Furrever_Home.dto.petadopter.SearchPetDto;
import com.furreverhome.Furrever_Home.dto.petadopter.SearchShelterDto;
import com.furreverhome.Furrever_Home.dto.petadopter.ShelterResponseDto;
import com.furreverhome.Furrever_Home.entities.LostPet;
import com.furreverhome.Furrever_Home.services.petadopterservices.LostPetService;
import com.furreverhome.Furrever_Home.services.petadopterservices.PetAdopterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petadopter")
@RequiredArgsConstructor
public class PetAdopterController {

    private final PetAdopterService petAdopterService;
    private final LostPetService lostPetService;
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

    @PostMapping("/lostpet")
    public ResponseEntity<LostPet> registerLostPet(@Valid @RequestHeader("Authorization") String authorizationHeader, @RequestBody RegisterLostPetDto registerLostPetDto) {
        return ResponseEntity.ok(lostPetService.registerLostPet(authorizationHeader, registerLostPetDto));
    }

    @PostMapping("/searchlostpet")
    public ResponseEntity<LostPetResponseDtoListDto> searchLostPet(@RequestBody SearchPetDto SearchPetDto) {
        return ResponseEntity.ok(lostPetService.searchLostPet(SearchPetDto));
    }

    @GetMapping("/lostpet/{userId}")
    public ResponseEntity<LostPetResponseDtoListDto> getLostPetBy(@PathVariable Long userId) {
        return ResponseEntity.ok(lostPetService.getLostPetListByUser(userId));
    }

}
