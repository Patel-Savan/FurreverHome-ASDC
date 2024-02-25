package com.furreverhome.Furrever_Home.services.adminservices.impl;

import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.adminservices.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ShelterRepository shelterRepository;
    private final UserRepository userRepository;


    @Override
    public boolean changeVerifiedStatus(String email, String status) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Shelter> optionalShelter = shelterRepository.findByUserId(user.getId());
            if (optionalShelter.isPresent()) {
                Shelter shelter = optionalShelter.get();
                if(Objects.equals(status, "Approve")) {
                    user.setVerified(Boolean.TRUE);
                    shelter.setRejected(Boolean.FALSE);
                } else{
                    shelter.setRejected(Boolean.TRUE);
                    user.setVerified(Boolean.FALSE);
                }
                shelterRepository.save(shelter);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

//    @Override
//    public boolean changeVerifiedStatus(Long shelterId, String status) {
//        Optional<Shelter> optionalShelter = shelterRepository.findById(shelterId);
//        System.out.println(shelterId);
//        System.out.println(status);
//        if(optionalShelter.isPresent()) {
//            Shelter shelter = optionalShelter.get();
//            System.out.println(shelter.getUser());
//            User user = shelter.getUser();
//            System.out.println(user.getVerified());
//            if(Objects.equals(status, "Approve"))
//                user.setVerified(Boolean.TRUE);
//            else
//                user.setVerified(Boolean.FALSE);
//            userRepository.save(user);
//            return true;
//        }
//        return false;
//    }
}
