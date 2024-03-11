package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.repository.AdopterPetRequestsRepository;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.PetRepository;
import com.furreverhome.Furrever_Home.services.petadopterservices.impl.PetAdopterServiceImpl;
import com.furreverhome.Furrever_Home.services.shelterService.ShelterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private ShelterServiceImpl shelterService;

    @Mock
    private PetAdopterRepository petAdopterRepository;

    @Mock
    private AdopterPetRequestsRepository adopterPetRequestsRepository;

    @InjectMocks
    private PetAdopterServiceImpl petAdopterService;


    @Test
    void deletePetNotFoundTest(){
        Long petID = 2L;
        when(petRepository.existsById(petID)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> shelterService.deletePet(petID));
        verify(petRepository, never()).deleteById(anyLong());
    }

    @Test
    void deletePetSuccess(){
        Long petID = 2L;
        when(petRepository.existsById(petID)).thenReturn(true);
        GenericResponse response = shelterService.deletePet(petID);
        verify(petRepository, times(1)).deleteById(petID);
        assertEquals("Pet deleted.", response.getMessage());
    }

    @Test
    void editPetNotFound() {
        Long petID = 2L;
        RegisterPetRequest updatePetRequest = new RegisterPetRequest();
        updatePetRequest.setType("Dog");
        when(petRepository.findById(petID)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> shelterService.editPet(petID, updatePetRequest));
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    void editPet_Success() throws ParseException {
        Long petID = 2L;
        RegisterPetRequest updatePetRequest = new RegisterPetRequest();
        updatePetRequest.setType("Dog");
        updatePetRequest.setBreed("Labrador");
        updatePetRequest.setColour("Golden");
        updatePetRequest.setGender("Male");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        updatePetRequest.setBirthdate(sdf.parse("11/06/2001"));
        updatePetRequest.setPetImage("dog.jpg");
        Pet pet = new Pet();
        pet.setPetID(petID);
        when(petRepository.findById(petID)).thenReturn(Optional.of(pet));
        PetDto updatedPet = shelterService.editPet(petID, updatePetRequest);
        verify(petRepository, times(1)).findById(petID);
        verify(petRepository, times(1)).save(any(Pet.class));
        assertEquals(updatePetRequest.getType(), updatedPet.getType());
        assertEquals(updatePetRequest.getBreed(), updatedPet.getBreed());
        assertEquals(updatePetRequest.getColour(), updatedPet.getColour());
        assertEquals(updatePetRequest.getGender(), updatedPet.getGender());
        assertEquals(updatePetRequest.getBirthdate(), updatedPet.getBirthdate());
        assertEquals(updatePetRequest.getPetImage(), updatedPet.getPetImage());
    }

    @Test
    void petAdoptedStatusSuccess() {
        Long petID = 2L;
        Pet pet = new Pet();
        pet.setPetID(petID);
        when(petRepository.findById(petID)).thenReturn(Optional.of(pet));
        boolean result = shelterService.changeAdoptedStatus(petID, "Adopted");
        verify(petRepository, times(1)).findById(petID);
        verify(petRepository, times(1)).save(pet);
        assertTrue(result);
        assertTrue(pet.isAdopted());
    }

    @Test
    void petNotAdoptedStatusSuccess() {
        Long petID = 2L;
        Pet pet = new Pet();
        pet.setPetID(petID);
        when(petRepository.findById(petID)).thenReturn(Optional.of(pet));
        boolean result = shelterService.changeAdoptedStatus(petID, "NotAdopted");
        verify(petRepository, times(1)).findById(petID);
        verify(petRepository, times(1)).save(pet);
        assertTrue(result);
        assertFalse(pet.isAdopted());
    }

    @Test
    void adoptPetRequestSuccess() {
        PetAdoptionRequestDto petAdoptionRequestDto = new PetAdoptionRequestDto();
        petAdoptionRequestDto.setPetAdopterID(1L);
        petAdoptionRequestDto.setPetID(2L);
        PetAdopter petAdopter = new PetAdopter();
        Pet pet = new Pet();
        when(petAdopterRepository.findById(petAdoptionRequestDto.getPetAdopterID())).thenReturn(Optional.of(petAdopter));
        when(petRepository.findById(petAdoptionRequestDto.getPetID())).thenReturn(Optional.of(pet));
        GenericResponse response = petAdopterService.adoptPetRequest(petAdoptionRequestDto);
        verify(petAdopterRepository, times(1)).findById(petAdoptionRequestDto.getPetAdopterID());
        verify(petRepository, times(1)).findById(petAdoptionRequestDto.getPetID());
        verify(adopterPetRequestsRepository, times(1)).save(any());
        assertEquals("Adoption Request Successful.", response.getMessage());
    }

    @Test
    void adoptPetRequestPetAdopted() {
        PetAdoptionRequestDto petAdoptionRequestDto = new PetAdoptionRequestDto();
        petAdoptionRequestDto.setPetAdopterID(2L);
        petAdoptionRequestDto.setPetID(2L);
        Pet pet = new Pet();
        PetAdopter petAdopter = new PetAdopter();
        pet.setAdopted(true);
        when(petAdopterRepository.findById(petAdoptionRequestDto.getPetAdopterID())).thenReturn(Optional.of(petAdopter));
        when(petRepository.findById(petAdoptionRequestDto.getPetID())).thenReturn(Optional.of(pet));
        GenericResponse response = petAdopterService.adoptPetRequest(petAdoptionRequestDto);
        verify(petAdopterRepository, times(1)).findById(petAdoptionRequestDto.getPetAdopterID());
        verify(petRepository, times(1)).findById(petAdoptionRequestDto.getPetID());
        verify(adopterPetRequestsRepository, never()).save(any());
        assertEquals("Pet already adopted", response.getMessage());
    }

    @Test
    void adoptPetRequestNotFound() {
        PetAdoptionRequestDto petAdoptionRequestDto = new PetAdoptionRequestDto();
        petAdoptionRequestDto.setPetAdopterID(2L);
        petAdoptionRequestDto.setPetID(2L);
        when(petAdopterRepository.findById(petAdoptionRequestDto.getPetAdopterID())).thenReturn(Optional.empty());
        when(petRepository.findById(petAdoptionRequestDto.getPetID())).thenReturn(Optional.empty());
        GenericResponse response = petAdopterService.adoptPetRequest(petAdoptionRequestDto);
        verify(petAdopterRepository, times(1)).findById(petAdoptionRequestDto.getPetAdopterID());
        verify(petRepository, times(1)).findById(petAdoptionRequestDto.getPetID());
        verify(adopterPetRequestsRepository, never()).save(any());
        assertEquals("PetAdopter or pet not found.", response.getMessage());
    }

}
