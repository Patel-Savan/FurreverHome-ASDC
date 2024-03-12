package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.repository.PetRepository;
import com.furreverhome.Furrever_Home.services.petservice.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Test
    void getPetInfoSuccess() {
        Long petId = 2L;
        Pet pet = new Pet();
        pet.setPetID(petId);
        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        PetDto petDto = petService.getPetInfo(petId);
        assertNotNull(petDto);
        assertEquals(petId, petDto.getPetID());
    }

    @Test
    void getPetInfoNotFound() {
        Long petId = 2L;
        when(petRepository.findById(petId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> petService.getPetInfo(petId));
    }

}
