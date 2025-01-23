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
}
