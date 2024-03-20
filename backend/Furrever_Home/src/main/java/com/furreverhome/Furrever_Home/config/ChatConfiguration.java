package com.furreverhome.Furrever_Home.config;

import com.furreverhome.Furrever_Home.repository.ChatService;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.chat.ChatServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfiguration {
    @Bean
    public ChatService chatService(UserRepository userRepository,
                                   PetAdopterRepository petAdopterRepository,
                                   ShelterRepository shelterRepository) {
        return new ChatServiceImpl(userRepository, petAdopterRepository, shelterRepository);
    }
}
