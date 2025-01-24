package com.rede.social.model;

import com.rede.social.repository.impl.PostRepositoryImplFile;
import com.rede.social.repository.impl.ProfileRepositoryImplFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SocialNetwork {
    private Map<Profile, Profile> pendingFriendRequests;
    private PostRepositoryImplFile postRepository;
    private ProfileRepositoryImplFile profileRepository;

    public SocialNetwork(Map<Profile, Profile> pendingFriendRequests,
         PostRepositoryImplFile postRepository, ProfileRepositoryImplFile profileRepository) {
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
    }

    public Post createPost(String content, Profile owner) {
        List<Post> posts = postRepository.getAllPosts();
        if (posts.isEmpty()) {
            return new Post(1, content, owner);
        }
        Integer id = posts.get(posts.size() - 1).getId() + 1;
        return new Post(id, content, owner);
    }

    public Profile createProfile(String username, String photo, String email) {
        List<Profile> profiles = profileRepository.getAllProfiles();
        if (profiles.isEmpty()) {
            return new Profile(1, username, photo, email);
        }
        Integer id = profiles.get(profiles.size() - 1).getId() + 1;
        return new Profile(id, username, photo, email);
    }

    public void addProfile(Profile profile) {
        Optional<Profile> foundedById = profileRepository.findProfileById(profile.getId());
        Optional<Profile> foundedByEmail = profileRepository.findProfileByEmail(profile.getEmail());
        Optional<Profile> foundedByName = profileRepository.findProfileByUsername(profile.getUsername());

        // todo: adicionar lançamento de exceção caso perfil já exista com algum dos critérios de busca
        if (foundedById.isEmpty()) {
            return;
        }
        if (foundedByName.isEmpty()) {
            return;
        }
        if (foundedByEmail.isEmpty()) {
            return;
        }
        profileRepository.addProfile(profile);
    }

    public Profile findProfileById(Integer id) {
        Optional<Profile> founded = profileRepository.findProfileById(id);
        if (founded.isEmpty()) {
            // todo: lançar exceção caso perfil não exista
        }
        return founded.get();
    }

    public Profile findProfileByEmail(String email) {
        Optional<Profile> founded = profileRepository.findProfileByEmail(email);
        if (founded.isEmpty()) {
            // todo: lançar exceção caso perfil não exista
        }
        return founded.get();
    }

    public Profile findProfileByUsername(String username) {
        Optional<Profile> founded = profileRepository.findProfileByUsername(username);
        if (founded.isEmpty()) {
            // todo: lançar exceção caso perfil não exista
        }
        return founded.get();
    }

    public void activateProfile(String username) {
        Optional<Profile> optionalProfile = profileRepository.findProfileByUsername(username);
        if (optionalProfile.isEmpty()){
            // todo: adicionar lançamento de exceção
            return;
        }
        Profile profile = optionalProfile.get();
        if (profile instanceof AdvancedProfile) {
            AdvancedProfile advancedProfile = (AdvancedProfile) profile;
            advancedProfile.setStatus(true);
        }
    }

    public void unactivateProfile(String username) {
        Optional<Profile> optionalProfile = profileRepository.findProfileByUsername(username);
        if (optionalProfile.isEmpty()){
            // todo: adicionar lançamento de exceção
            return;
        }
        Profile profile = optionalProfile.get();
        if (profile instanceof AdvancedProfile) {
            AdvancedProfile advancedProfile = (AdvancedProfile) profile;
            advancedProfile.setStatus(false);
        }
    }
}
