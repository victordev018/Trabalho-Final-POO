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
    // TODO adicionar array de postagens


    public Profile(int id, String username, String photo, String email) {
        this.id = id;
        this.username = username;
        this.photo = photo;
        this.email = email;
        this.status = true;
        this.friends = new ArrayList<>();
    }

    public void addFriend(Profile friend) {
        this.friends.add(friend);
    }

    public void deleteFriend(Profile friend) {
        this.friends.remove(friend);
    }

    // TODO criar metodo para adicionar publicação

    public List<Profile> listFriends() {
        return this.friends;
    }

    // TODO criar método para listar postagens

    public void changeStatus() {
        this.status = !this.status;
    }
}
