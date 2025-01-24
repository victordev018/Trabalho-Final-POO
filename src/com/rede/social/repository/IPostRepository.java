package com.rede.social.repository;

import com.rede.social.model.Post;
import com.rede.social.model.Profile;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    void addPost(Post post);
    List<Post> getAllPosts();
    Optional<Post> findPostById(Integer id);
    List<Post> listPosts();
    List<Post> listPostsByProfile(Profile profile);
}
