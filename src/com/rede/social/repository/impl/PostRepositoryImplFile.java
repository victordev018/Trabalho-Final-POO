package com.rede.social.repository.impl;

import com.rede.social.exception.global.NotFoundError;
import com.rede.social.model.Post;
import com.rede.social.model.Profile;
import com.rede.social.repository.IPostRepository;
import com.rede.social.repository.IProfileRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostRepositoryImplFile implements IPostRepository {
    private final List<Post> posts;
    private final IProfileRepository profileRepository;

    public PostRepositoryImplFile(IProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        this.posts = new ArrayList<>();
    }

    @Override
    public void addPost(Post post) {
        this.posts.add(post);
    }

    @Override
    public Optional<Post> findPostById(Integer id) throws NotFoundError {
        for (Post post : posts){
            if (post.getId() == id){
                return Optional.of(post);
            }
        }
        throw new NotFoundError("nao foi encontrado post com id: " + id);
    }

    @Override
    public List<Post> listPosts() {
        return this.posts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> listPostsByProfile(String usernameOwner) throws NotFoundError {
        Profile owner = this.profileRepository.findProfileByUsername(usernameOwner).get();
        return this.posts.stream()
                .filter(post -> post.getOwner().equals(owner))
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
}
