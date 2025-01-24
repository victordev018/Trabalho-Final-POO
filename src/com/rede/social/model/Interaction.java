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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InteractionType getType() {
        return type;
    }

    public void setType(InteractionType type) {
        this.type = type;
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }
}
