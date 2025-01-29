package com.rede.social.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Profile {

    @JsonProperty("id")
    private int id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("email")
    private String email;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("type")
    private String type;

    @JsonIgnore
    private List<Profile> friends = new ArrayList<>();

    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    public Profile() {
    }

    @JsonCreator
    public Profile(
            @JsonProperty("id") int id,
            @JsonProperty("username") String username,
            @JsonProperty("photo") String photo,
            @JsonProperty("email") String email,
            @JsonProperty("type") String type) {
        this.id = id;
        this.username = username;
        this.photo = photo;
        this.email = email;
        this.status = true;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Profile> getFriends() {
        return friends;
    }

    public void setFriends(List<Profile> friends) {
        this.friends = friends;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addFriend(Profile friend) {
        this.friends.add(friend);
    }

    public void deleteFriend(Profile friend) {
        this.friends.remove(friend);
    }

    public void addPost(Post newPost) {
        this.posts.add(newPost);
    }

    public List<Profile> listFriends() {
        return this.friends;
    }

    public List<Post> listPosts() {
        return this.posts;
    }

    public void changeStatus() {
        this.status = !this.status;
    }

    public String toString() {
        String dataFormated = String.format("""
                ╔═══════════╦══════════════════╦══════════════════════════════╦════════════╦══════════════╗
                ║ <ID> %-4d ║ @%-15s ║ %-28s ║ photo: %-1s  ║  %-10s  ║
                ╚═══════════╩══════════════════╩══════════════════════════════╩════════════╩══════════════╝
                """, id, username, email, photo, status? "ativado":"desativado");
        return dataFormated;
    }

    // equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id == profile.id && Objects.equals(username, profile.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
