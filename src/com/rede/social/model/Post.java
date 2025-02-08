package com.rede.social.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Post {

    @JsonProperty("id")
    private int id;

    @JsonProperty("content")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("type")
    private String type;

    @JsonProperty("owner")
    private Profile owner;

    @JsonCreator
    public Post(
            @JsonProperty("id") int id,
            @JsonProperty("content") String content,
            @JsonProperty("type") String type,
            @JsonProperty("owner") Profile owner) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.createdAt = LocalDateTime.now();
        this.owner = owner;
    }

    public Post(){}

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Profile getOwner() {
        return owner;
    }

    public void setOwner(Profile owner) {
        this.owner = owner;
    }
}