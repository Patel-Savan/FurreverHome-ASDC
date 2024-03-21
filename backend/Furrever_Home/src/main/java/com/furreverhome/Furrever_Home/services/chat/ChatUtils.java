package com.furreverhome.Furrever_Home.services.chat;

import com.furreverhome.Furrever_Home.utils.jgravatar.Gravatar;
import com.furreverhome.Furrever_Home.utils.jgravatar.GravatarDefaultImage;
import com.furreverhome.Furrever_Home.utils.jgravatar.GravatarRating;

public class ChatUtils {

    private ChatUtils() {
    }

    public static String getAvatarUrl(String email) {
        Gravatar gravatar = new Gravatar();
        gravatar.setSize(50);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        return gravatar.getUrl(email);
    }
}
