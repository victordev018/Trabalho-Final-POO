package com.rede.social.model;

import java.time.LocalDateTime;

public class Post {

    private int id;
    private String content;
    private LocalDateTime createdAt;
    private Profile owner;

    public Post(int id, String content, Profile owner) {
        this.id = id;
        this.content = content;
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Profile getOwner() {
        return owner;
    }

    public void setOwner(Profile owner) {
        this.owner = owner;
    }
}
