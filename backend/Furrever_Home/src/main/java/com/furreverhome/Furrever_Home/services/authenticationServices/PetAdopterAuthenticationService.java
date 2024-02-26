package com.furreverhome.Furrever_Home.services.authenticationServices;

import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.entities.PasswordResetToken;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.exception.EmailExistsException;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.emailservice.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PetAdopterAuthenticationService {

    private final UserRepository userRepository;

    private final PetAdopterRepository petAdopterRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    public PetAdopterDto signup(String appUrl, SignupRequest signupRequest) throws MessagingException {
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
        user.setRole(Role.PETADOPTER);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        User result = userRepository.save(user);
        PetAdopter petAdopter = new PetAdopter();

        petAdopter.setFirstname(((PetAdopterSignupRequest) signupRequest).getFirstName());
        petAdopter.setLastname(((PetAdopterSignupRequest) signupRequest).getLastName());
        petAdopter.setPhone_number(((PetAdopterSignupRequest) signupRequest).getPhone_number());
        petAdopter.setAddress(((PetAdopterSignupRequest) signupRequest).getAddress());
        petAdopter.setCity(((PetAdopterSignupRequest) signupRequest).getCity());
        petAdopter.setCountry(((PetAdopterSignupRequest) signupRequest).getCountry());
        petAdopter.setZipcode(((PetAdopterSignupRequest) signupRequest).getZipcode());
        petAdopter.setUser(result);

        String url = appUrl + "/api/auth/verify/" + signupRequest.getEmail();
        String linkText = "Click here to verify your email.";
        String message = "<p>Please use the link below to verify your email.</p>"
                + "<a href=\"" + url + "\">" + linkText + "</a>";
        // TODO: Separate the logic for the code to send email from here.
        emailService.sendEmail(signupRequest.getEmail(), "Email Verification",
                message, true);

        petAdopterRepository.save(petAdopter);

        PetAdopterDto petAdopterDto = new PetAdopterDto();
        petAdopterDto.setId(result.getId());
        petAdopterDto.setFirstname(petAdopter.getFirstname());
        petAdopterDto.setEmail(result.getEmail());
        petAdopterDto.setUserRole(result.getRole());
        return petAdopterDto;
    }
}


