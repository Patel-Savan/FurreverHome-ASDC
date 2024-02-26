package com.furreverhome.Furrever_Home.services.authenticationServices;

import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.exception.EmailExistsException;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelterAuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final ShelterRepository shelterRepository;

    public ShelterDto signup(String appUrl, SignupRequest signupRequest) throws MessagingException {
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
        shelter.setCapacity(((ShelterSignupRequest) signupRequest).getCapacity());
        shelter.setImageBase64(((ShelterSignupRequest) signupRequest).getImageBase64());
        shelter.setCity(((ShelterSignupRequest) signupRequest).getCity());
        shelter.setCountry(((ShelterSignupRequest) signupRequest).getCountry());
        shelter.setAddress(((ShelterSignupRequest) signupRequest).getAddress());
        shelter.setZipcode(((ShelterSignupRequest) signupRequest).getZipcode());
        shelter.setRejected(Boolean.FALSE);
        shelter.setUser(result);

//        String url = appUrl + "/api/auth/verify/" + signupRequest.getEmail();
//        String linkText = "Click here to verify your email.";
//        String message = "<p>Please use the link below to verify your email.</p>"
//                + "<a href=\"" + url + "\">" + linkText + "</a>";
//        // TODO: Separate the logic for the code to send email from here.
//        emailService.sendEmail(signupRequest.getEmail(), "Email Verification",
//                message, true);

        shelterRepository.save(shelter);

        ShelterDto shelterDto = new ShelterDto();
        shelterDto.setName(shelter.getName());
        shelterDto.setEmail(result.getEmail());
        shelterDto.setUserRole(result.getRole());
        return shelterDto;
    }
}
