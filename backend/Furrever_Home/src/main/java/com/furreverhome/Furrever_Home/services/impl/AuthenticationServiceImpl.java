package com.furreverhome.Furrever_Home.services.impl;


import com.furreverhome.Furrever_Home.dto.SignupRequest;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.AuthenticationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;


    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(Role.ADMIN);

        if(adminAccount == null) {
            User user = new User();

            user.setV_id("admin1234");
            user.setAddress("admin st");
            user.setPhone_number("499992222");
            user.setEmail("admin@gmail.com");
            user.setFirstname("admin");
            user.setLastname("admin");
            user.setRole(Role.ADMIN);
            user.setVerified(Boolean.TRUE);
            user.setPassword("admin1234");
            userRepository.save(user);
            System.out.println("Admin successfully created..");
        }
    }

    public User signup(SignupRequest signupRequest) {
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setFirstname(signupRequest.getFirstName());
        user.setLastname(signupRequest.getLastName());
        user.setV_id(signupRequest.getV_id());
        user.setPhone_number(signupRequest.getPhone_number());
        user.setAddress(signupRequest.getAddress());
        user.setVerified(Boolean.FALSE);
        user.setRole(Role.PETADOPTER);
        user.setPassword(signupRequest.getPassword());

        return userRepository.save(user);
    }


}
