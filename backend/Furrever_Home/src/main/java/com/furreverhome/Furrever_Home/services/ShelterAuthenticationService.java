package com.furreverhome.Furrever_Home.services;

import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.exception.EmailExistsException;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShelterAuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final ShelterRepository shelterRepository;

//    @Autowired
//    public ShelterAuthenticationService(UserRepository userRepository, PetAdopterRepository petAdopterRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, EmailService emailService, ShelterRepository shelterRepository) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.emailService = emailService;
//        this.shelterRepository = shelterRepository;
//    }


    public ShelterDto signup(SignupRequest signupRequest) {
        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            try {
                throw new EmailExistsException("User Already Exists");
            } catch (EmailExistsException e) {
                throw new RuntimeException(e);
            }
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setVerified(Boolean.FALSE);
        user.setRole(Role.SHELTER);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        User result = userRepository.save(user);

        Shelter shelter = new Shelter();
        shelter.setName(((ShelterSignupRequest) signupRequest).getName());
        shelter.setContact(((ShelterSignupRequest) signupRequest).getContact());
        shelter.setLicense(((ShelterSignupRequest) signupRequest).getLicense());
        shelter.setLocation(((ShelterSignupRequest) signupRequest).getLocation());
        shelter.setCapacity(((ShelterSignupRequest) signupRequest).getCapacity());
        shelter.setImage(((ShelterSignupRequest) signupRequest).getImage());
        shelter.setUser(result);

        // TODO: Separate the logic for the code to send email from here.
        emailService.sendEmail(signupRequest.getEmail(), "Email Verification",
                "http://localhost:8080/api/auth/verify/" + signupRequest.getEmail());

        shelterRepository.save(shelter);

        ShelterDto shelterDto = new ShelterDto();
        shelterDto.setName(shelter.getName());
        shelterDto.setEmail(result.getEmail());
        shelterDto.setUserRole(result.getRole());
        return shelterDto;
    }
}
