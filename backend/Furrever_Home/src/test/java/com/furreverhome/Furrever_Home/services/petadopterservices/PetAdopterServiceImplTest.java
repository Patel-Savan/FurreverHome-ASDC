package com.furreverhome.Furrever_Home.services.petadopterservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.furreverhome.Furrever_Home.services.petadopterservices.impl.PetAdopterServiceImpl;
import com.furreverhome.Furrever_Home.repository.*;
import com.furreverhome.Furrever_Home.entities.*;
import com.furreverhome.Furrever_Home.dto.petadopter.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PetAdopterServiceImplTest {

    @Mock
    private ShelterRepository shelterRepository;

    @Mock
    private PetAdopterRepository petAdopterRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetAdopterServiceImpl petAdopterService;

    private User user;

    private Shelter shelter;

    @BeforeEach
    void setup() {
        // Create a dummy user for the shelter
        user = new User();
        user.setId(1L); // Assuming the User class has these fields
        user.setEmail("user@example.com");
        user.setVerified(true);

        // Create the shelter object and set its properties
        shelter = new Shelter();
        shelter.setId(1L);
        shelter.setName("Happy Paws Shelter");
        shelter.setCapacity(100L);
        shelter.setContact("555-1234");
        shelter.setImageBase64("base64EncodedImageString");
        shelter.setLicense("base64EncodedLicenseString");
        shelter.setUser(user);
        shelter.setAddress("1234 Street Name");
        shelter.setCity("CityName");
        shelter.setCountry("CountryName");
        shelter.setZipcode("123456");
        shelter.setRejected(false);
    }


    @Test
    void testGetAllShelter() {
        // Mock the shelter list
        List<Shelter> shelters = Arrays.asList(shelter, shelter);
        when(shelterRepository.findAll()).thenReturn(shelters);

        // Act
        List<ShelterResponseDto> result = petAdopterService.getAllShelter();

        // Assert
        assertNotNull(result);
        assertEquals(shelters.size(), result.size());

        // Verify interaction with shelterRepository
        verify(shelterRepository, times(1)).findAll();
    }

}

