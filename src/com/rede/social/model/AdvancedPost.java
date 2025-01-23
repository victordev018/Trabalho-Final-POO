package com.rede.social.model;

import java.util.ArrayList;
import java.util.List;

public class AdvancedPost extends Post {

    private final List<Interaction> interactions;

    public AdvancedPost(int id, String content, Profile owner) {
        super(id, content, owner);
        this.interactions = new ArrayList<>();
    }

    public void addInteraction(Interaction newInteraction) {
        this.interactions.add(newInteraction);
    }

    public List<Interaction> listInteractions() {
        return this.interactions;
    }
}
