package com.furreverhome.Furrever_Home.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furreverhome.Furrever_Home.services.petadopterservices.PetAdopterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PetAdopterControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PetAdopterService petAdopterService;

    @InjectMocks
    private PetAdopterController petAdopterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petAdopterController).build();
    }

    @Test
    public void requestExistsBothExistsReturns200() throws Exception {
        long petID = 2L;
        long petAdopterID = 2L;
        given(petAdopterService.requestExists(petID, petAdopterID)).willReturn(true);
        mockMvc.perform(get("/api/petadopter/pet/adopt/requestexists")
                        .param("petID", String.valueOf(petID))
                        .param("petAdopterID", String.valueOf(petAdopterID)))
                .andExpect(status().isOk());
    }

    @Test
    public void requestExistsPetAdopterDoesNotExistReturns404() throws Exception {
        long petID = 2L;
        long petAdopterID = 2L;
        given(petAdopterService.requestExists(petID, petAdopterID)).willReturn(false);
        mockMvc.perform(get("/api/petadopter/pet/adopt/requestexists")
                        .param("petID", String.valueOf(petID))
                        .param("petAdopterID", String.valueOf(petAdopterID)))
                .andExpect(status().isNotFound());
    }


    /**
     * util function to covert object to json
     * @param obj
     * @return
     */    private String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
