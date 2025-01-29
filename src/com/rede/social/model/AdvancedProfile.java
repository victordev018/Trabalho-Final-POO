package com.rede.social.model;

public class AdvancedProfile extends Profile {

    public AdvancedProfile(int id, String username, String photo, String email, String type) {
        super(id, username, photo, email, type);
    }

    public void changeOtherProfileStatus(Profile profile) {
        profile.changeStatus();
    }
}
