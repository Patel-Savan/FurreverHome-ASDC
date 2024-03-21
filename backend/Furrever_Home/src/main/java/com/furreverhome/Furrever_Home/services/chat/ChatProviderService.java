package com.furreverhome.Furrever_Home.services.chat;

import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public interface ChatProviderService {

    String getApiKey();

    void createChatChannel(PetAdopter user1, Shelter user2, String channelId);

    void addUser(String userId, String username, String imageUrl);

    @NotNull
    String getToken(@NotNull String userId, Date expiresAt, Date issuedAt);

    /**
     * Gets pet chat user id.
     *
     * @param userId the user id
     * @return the pet chat user id
     */
    String getPetChatUserId(long userId);

    /**
     * Gets shelter chat user id.
     *
     * @param userId the user id
     * @return the shelter chat user id
     */
    String getShelterChatUserId(long userId);
}
