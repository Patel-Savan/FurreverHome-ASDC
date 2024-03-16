package com.furreverhome.Furrever_Home.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetVaccineDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.services.petservice.PetService;
import com.furreverhome.Furrever_Home.services.shelterService.ShelterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShelterControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ShelterServiceImpl shelterService;

    @Mock
    private PetService petService;

    @InjectMocks
    private ShelterController shelterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shelterController).build();
    }

    @Test
    void registerPetInShelter_SuccessfulRegistration_ReturnsPetDto() throws Exception {
        RegisterPetRequest request = new RegisterPetRequest();
        request.setType("Dog");
        request.setBreed("Labrador");
        request.setColour("Black");
        request.setGender("Male");
        PetDto expectedPetDto = new PetDto();
        expectedPetDto.setPetID(1L);
        expectedPetDto.setType(request.getType());
        expectedPetDto.setBreed(request.getBreed());
        expectedPetDto.setColour(request.getColour());
        expectedPetDto.setGender(request.getGender());
        when(shelterService.registerPet(any(RegisterPetRequest.class))).thenReturn(expectedPetDto);
        mockMvc.perform(post("/api/shelter/registerPet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void addVaccine_SuccessfulAddition_ReturnsOk() throws Exception {
        long petId = 2L;
        PetVaccineDto petVaccineDto = new PetVaccineDto();
        petVaccineDto.setVaccineName("Rabies");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        petVaccineDto.setDate(sdf.parse("03/05/2021"));
        petVaccineDto.setVaccineGiven(true);
        GenericResponse expectedResponse = new GenericResponse("Vaccination added.");
        when(petService.addVaccinationDetails(any(PetVaccineDto.class), any(Long.class))).thenReturn(expectedResponse);
        mockMvc.perform(post("/api/shelter/{petID}/addvaccine", petId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(petVaccineDto)))
                .andExpect(status().isOk());
    }



    /**
     * util function to covert object to json
     * @param obj
     * @return
     */
    private String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
