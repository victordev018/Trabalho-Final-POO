package com.rede.social.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Profile {
    private int id;
    private String username;
    private String photo;
    private String email;
    private Boolean status;
    private List<Profile> friends;
    private List<Post> posts;

    public Profile(int id, String username, String photo, String email) {
        this.id = id;
        this.username = username;
        this.photo = photo;
        this.email = email;
        this.status = true;
        this.friends = new ArrayList<>();
        this.posts = new ArrayList<>();
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
                ╔═══════════╦══════════════════╦══════════════════════════════╦════════════╗
                ║ <ID> %-4d ║ @%-15s ║ %-28s ║ photo: %-1s  ║
                ╚═══════════╩══════════════════╩══════════════════════════════╩════════════╝
                """, id, username, email, photo);
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
