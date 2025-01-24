package com.rede.social.repository.impl;

import com.rede.social.model.Post;
import com.rede.social.model.Profile;
import com.rede.social.repository.IPostRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostRepositoryImplFile implements IPostRepository {
    private final List<Post> posts;

    public PostRepositoryImplFile() {
        this.posts = new ArrayList<>();
    }

    @Override
    public void addPost(Post post) {
        this.posts.add(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return this.posts;
    }

    @Override
    public Optional<Post> findPostById(Integer id) {
        for (Post post : posts){
            if (post.getId() == id){
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Post> listPosts() {
        return this.posts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> listPostsByProfile(Profile profile) {
        return this.posts.stream()
                .filter(post -> post.getOwner().equals(profile))
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
}
