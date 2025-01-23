package com.rede.social.model;

import java.util.ArrayList;
import java.util.List;

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
}
