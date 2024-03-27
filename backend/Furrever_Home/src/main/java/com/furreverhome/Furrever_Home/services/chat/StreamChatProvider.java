package com.furreverhome.Furrever_Home.services.chat;

import com.furreverhome.Furrever_Home.dto.chat.UserRoleEntities;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import io.getstream.chat.java.exceptions.StreamException;
import io.getstream.chat.java.models.Channel;
import io.getstream.chat.java.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

import static com.furreverhome.Furrever_Home.services.chat.ChatUtils.getAvatarUrl;

@RequiredArgsConstructor
public class StreamChatProvider implements ChatProviderService {

    @Value("${io.getstream.chat.apiKey}")
    private String apiKey;

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public void createChatChannel(PetAdopter petAdopter, Shelter shelter, String channelId) {
        // Upsert both users
        String petAdopterUserId = getPetChatUserId(petAdopter.getId());
        String petAdopterUsername = petAdopter.getFirstname();
        String petAdopterAvatarUrl = getAvatarUrl(petAdopter.getUser().getEmail());
        var petStreamUser = upsertUser(petAdopterUserId, petAdopterUsername, petAdopterAvatarUrl);

        String shelterUserId = getPetChatUserId(shelter.getId());
        String shelterUsername = shelter.getName();
        String shelterAvatarUrl = getAvatarUrl(shelter.getUser().getEmail());
        var shelterStreamUser = upsertUser(shelterUserId, shelterUsername, shelterAvatarUrl);

        try {
            createStreamChannel(petStreamUser,
                    shelterStreamUser,
                    new UserRoleEntities(shelter, petAdopter),
                    channelId,
                    getAvatarUrl("furrever_" + channelId + "@furrever.ca"));
        } catch (StreamException e) {
            // Duplicate is a false positive so allow it.
            if (!e.getLocalizedMessage().contains("Duplicate")) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addUser(String userId, String username, String imageUrl) {
        upsertUser(userId, username, imageUrl);
    }

    private void createStreamChannel(
            User.UserRequestObject fromStreamUser,
            User.UserRequestObject toStreamUser,
            UserRoleEntities userRoleEntities,
            String channelId,
            String imageUrl
    ) throws StreamException {
        var fromChannelUser = Channel.ChannelMemberRequestObject.builder().user(fromStreamUser).build();
        var toChannelUser = Channel.ChannelMemberRequestObject.builder().user(toStreamUser).build();


        var channelData = Channel.ChannelRequestObject.builder()
                .createdBy(fromStreamUser)
                .additionalField("name", userRoleEntities.shelter().getName() + " :-: " + userRoleEntities.petAdopter().getFirstname())
                .additionalField("petadopter", userRoleEntities.petAdopter().getUser().getEmail())
                .additionalField("shelter", userRoleEntities.shelter().getUser().getEmail())
                .additionalField("image", imageUrl)
                .members(List.of(fromChannelUser, toChannelUser))
                .build();

        Channel.getOrCreate("messaging", channelId)
                .data(channelData)
                .request();
    }

    public User.UserRequestObject upsertUser(String userId, String username, String imageUrl) {
        var streamUser = io.getstream.chat.java.models.User.UserRequestObject.builder()
                .id(userId)
                .name(username)
                .additionalField("image", imageUrl)
                .build();
        var usersUpsertRequest = io.getstream.chat.java.models.User.upsert();
        usersUpsertRequest.user(streamUser);
        return streamUser;
    }

    @Override
    public String getToken(String userId, Date expiresAt, Date issuedAt) {
        return User.createToken(userId, expiresAt, issuedAt);
    }

    @Override
    public String getPetChatUserId(long userId) {
        return "testpetuser1";
    }

    @Override
    public String getShelterChatUserId(long userId) {
        return "testshelteruser1";
    }
}
