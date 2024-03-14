package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetVaccineDto;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetVaccination;
import com.furreverhome.Furrever_Home.repository.PetRepository;
import com.furreverhome.Furrever_Home.repository.PetVaccinationRepository;
import com.furreverhome.Furrever_Home.services.petservice.PetServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @Mock
    private PetVaccinationRepository petVaccinationRepository;

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

    @Test
    void addVaccinationDetailsPetNotFound() throws ParseException {
        Long petID = 2L;
        PetVaccineDto petVaccineDto = new PetVaccineDto();
        petVaccineDto.setVaccineName("Rabies");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        petVaccineDto.setDate(sdf.parse("03/05/2021"));
        petVaccineDto.setVaccineGiven(true);
        when(petRepository.findById(petID)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> petService.addVaccinationDetails(petVaccineDto, petID));
    }

    @Test
    void addVaccinationDetailsVaccinationExists() throws ParseException {
        Long petID = 2L;
        Pet pet = new Pet();
        pet.setPetID(petID);
        PetVaccineDto petVaccineDto = new PetVaccineDto();
        petVaccineDto.setVaccineName("Rabies");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        petVaccineDto.setDate(sdf.parse("03/05/2021"));
        petVaccineDto.setVaccineGiven(true);
        when(petRepository.findById(petID)).thenReturn(Optional.of(pet));
        when(petVaccinationRepository.existsByPetAndVaccineName(pet, "Rabies")).thenReturn(true);
        GenericResponse response = petService.addVaccinationDetails(petVaccineDto, petID);
        verify(petRepository, times(1)).findById(petID);
        verify(petVaccinationRepository, times(1)).existsByPetAndVaccineName(pet, "Rabies");
        verify(petVaccinationRepository, never()).save(any(PetVaccination.class));
        assertEquals("already present", response.getMessage());
    }

    @Test
    void addVaccinationDetailsSuccess() throws ParseException {
        Long petID = 2L;
        Pet pet = new Pet();
        pet.setPetID(petID);
        PetVaccineDto petVaccineDto = new PetVaccineDto();
        petVaccineDto.setVaccineName("Rabies");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        petVaccineDto.setDate(sdf.parse("03/05/2021"));
        petVaccineDto.setVaccineGiven(true);
        when(petRepository.findById(petID)).thenReturn(Optional.of(pet));
        when(petVaccinationRepository.existsByPetAndVaccineName(pet, "Rabies")).thenReturn(false);
        GenericResponse response = petService.addVaccinationDetails(petVaccineDto, petID);
        verify(petRepository, times(1)).findById(petID);
        verify(petVaccinationRepository, times(1)).existsByPetAndVaccineName(pet, "Rabies");
        verify(petVaccinationRepository, times(1)).save(any(PetVaccination.class));
        assertEquals("Vaccination added.", response.getMessage());
    }
}
