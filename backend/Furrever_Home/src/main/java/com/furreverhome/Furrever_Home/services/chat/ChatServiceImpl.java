package com.furreverhome.Furrever_Home.services.chat;

import com.furreverhome.Furrever_Home.dto.chat.ChatCredentialsResponse;
import com.furreverhome.Furrever_Home.dto.chat.UserRoleEntities;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.ChatService;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.furreverhome.Furrever_Home.services.chat.ChatUtils.getAvatarUrl;

/**
 * The type Chat service.
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatProviderService chatProviderService;
    private final UserRepository userRepository;
    private final PetAdopterRepository petAdopterRepository;
    private final ShelterRepository shelterRepository;

    private final String CHATPETUSERIDCONSTANT = "petuser";
    private final String CHATSHELTERUSERIDCONSTANT = "shelteruser";


    @Override
    public ChatCredentialsResponse createChatSession(long fromUserId, long toUserId){
        // Validate users
        var fromUser = validateUserExists(fromUserId);
        var toUser = validateUserExists(toUserId);

        // Determine roles and get entities
        var entities = determineRolesAndGetEntities(fromUser, toUser);

        // Generate the unique channel ID
        String channelId = generateChannelId(entities.petAdopter().getUser().getEmail(), entities.shelter().getUser().getEmail());

        // Create a channel with both users
        chatProviderService.createChatChannel(entities.petAdopter(), entities.shelter(), channelId);

        // Generate token for the 'from' user to connect to the client-side
        return generateTokenForUser(fromUser, entities.petAdopter(), entities.shelter(), channelId);
    }

    @Override
    public ChatCredentialsResponse getChatHistory(long userId) {
        var user = validateUserExists(userId);
        var entities = determineRolesAndGetEntities(user);

        // Upsert both users
        if (entities.petAdopter() != null) {
            chatProviderService.addUser(entities.petAdopter().getId().toString(), entities.petAdopter().getFirstname(), getAvatarUrl(entities.petAdopter().getUser().getEmail()));
        }

        if (entities.shelter() != null) {
            chatProviderService.addUser(entities.shelter().getId().toString(), entities.shelter().getName(), getAvatarUrl(entities.shelter().getUser().getEmail()));
        }

        return generateTokenForUser(user, entities.petAdopter(), entities.shelter(), null);
    }


    private String generateChannelId(String petAdopterEmail, String shelterEmail) {
        String rawId = DigestUtils.sha256Hex(petAdopterEmail + shelterEmail);
        return rawId.substring(0, Math.min(rawId.length(), 14));
    }

    private User validateUserExists(long userId) throws ResponseStatusException {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private UserRoleEntities determineRolesAndGetEntities(User fromUser, User toUser) {
        Shelter shelter;
        PetAdopter petAdopter;

        if (fromUser.getRole() == Role.PETADOPTER) {
            petAdopter = petAdopterRepository.findByUserId(fromUser.getId()).get();
            shelter = shelterRepository.findByUserId(toUser.getId()).get();
        } else {
            petAdopter = petAdopterRepository.findByUserId(toUser.getId()).get();
            shelter = shelterRepository.findByUserId(fromUser.getId()).get();
        }

        return new UserRoleEntities(shelter, petAdopter);
    }

    private UserRoleEntities determineRolesAndGetEntities(User user) {
        Shelter shelter;
        PetAdopter petAdopter;

        if (user.getRole() == Role.PETADOPTER) {
            petAdopter = petAdopterRepository.findByUserId(user.getId()).get();
            shelter = null;
        } else {
            petAdopter = null;
            shelter = shelterRepository.findByUserId(user.getId()).get();
        }

        return new UserRoleEntities(shelter, petAdopter);
    }


    private ChatCredentialsResponse generateTokenForUser(User fromUser, PetAdopter petAdopter, Shelter shelter, String channelId) {
        var calendar = new GregorianCalendar();
        calendar.add(Calendar.HOUR, 1);


        String token;
        String userId;
        String avatarUrl;

        if (fromUser.getRole() == Role.PETADOPTER) {
            userId = CHATPETUSERIDCONSTANT;
            avatarUrl = getAvatarUrl(petAdopter.getUser().getEmail());
            token = chatProviderService.getToken(userId, calendar.getTime(), null);
        } else {
            userId = CHATSHELTERUSERIDCONSTANT;
            avatarUrl = getAvatarUrl(shelter.getUser().getEmail());

            token = chatProviderService.getToken(userId, calendar.getTime(), null);
        }

        return new ChatCredentialsResponse(token, chatProviderService.getApiKey(), channelId, userId, avatarUrl);
    }
}
