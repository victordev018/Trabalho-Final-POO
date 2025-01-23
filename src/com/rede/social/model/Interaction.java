package com.rede.social.model;

import com.rede.social.model.enums.InteractionType;

public class Interaction {

    private int id;
    private InteractionType type;
    private Profile author;

    public Interaction(int id, InteractionType type, Profile author) {
        this.id = id;
        this.type = type;
        this.author = author;
    }
}
