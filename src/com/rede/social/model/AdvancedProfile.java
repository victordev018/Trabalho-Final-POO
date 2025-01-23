package com.rede.social.model;

public class AdvancedProfile extends Profile {

    public AdvancedProfile(int id, String username, String photo, String email) {
        super(id, username, photo, email);
    }

    public void changeOtherProfileStatus(Profile profile) {
        profile.changeStatus();
    }
}
