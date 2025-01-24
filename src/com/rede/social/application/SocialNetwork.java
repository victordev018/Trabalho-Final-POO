package com.rede.social.application;

import com.rede.social.exception.global.AlreadyExistsError;
import com.rede.social.exception.global.NotFoundError;
import com.rede.social.exception.profileException.ProfileAlreadyActivatedException;
import com.rede.social.exception.profileException.ProfileUnauthorizedError;
import com.rede.social.model.AdvancedProfile;
import com.rede.social.model.Post;
import com.rede.social.model.Profile;
import com.rede.social.repository.IPostRepository;
import com.rede.social.repository.IProfileRepository;
import com.rede.social.repository.impl.PostRepositoryImplFile;
import com.rede.social.repository.impl.ProfileRepositoryImplFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SocialNetwork {
    private Map<Profile, Profile> pendingFriendRequests;
    private IPostRepository postRepository;
    private IProfileRepository profileRepository;

    public SocialNetwork(IPostRepository postRepository, IProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
        this.pendingFriendRequests = new HashMap<>();
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

    public void addProfile(Profile profile) throws AlreadyExistsError {
        Optional<Profile> foundedById = profileRepository.findProfileById(profile.getId());
        Optional<Profile> foundedByEmail = profileRepository.findProfileByEmail(profile.getEmail());
        Optional<Profile> foundedByName = profileRepository.findProfileByUsername(profile.getUsername());

        foundedByName.orElseThrow(() -> new AlreadyExistsError("Ja existe perfil com nome: " + profile.getUsername()));
        foundedByEmail.orElseThrow(() -> new AlreadyExistsError("Ja existe perfil com email: " + profile.getEmail()));
        foundedById.orElseThrow(() -> new AlreadyExistsError("Ja existe perfil com id: " + profile.getId()));

        profileRepository.addProfile(profile);
    }

    public Profile findProfileById(Integer id) throws NotFoundError{
        Optional<Profile> founded = profileRepository.findProfileById(id);
        return founded.orElseThrow(() -> new NotFoundError("Não foi encontrado perfil com id: " + id));
    }

    public Profile findProfileByEmail(String email) throws NotFoundError {
        Optional<Profile> founded = profileRepository.findProfileByEmail(email);
        return founded.orElseThrow(() -> new NotFoundError("Não foi encontrado perfil com email: " + email));
    }

    public Profile findProfileByUsername(String username) throws NotFoundError {
        Optional<Profile> founded = profileRepository.findProfileByUsername(username);
        return founded.orElseThrow(() -> new NotFoundError("Não foi encontrado perfil com nome: " + username));
    }

    public void activateProfile(String username) throws NotFoundError, ProfileUnauthorizedError, ProfileAlreadyActivatedException {
        Optional<Profile> optionalProfile = profileRepository.findProfileByUsername(username);
        Profile profile = optionalProfile.orElseThrow(() -> new NotFoundError("Não foi encontrado perfil com nome: " + username));
        if (!(profile instanceof AdvancedProfile)) {
            throw new ProfileUnauthorizedError("somente perfis avançados podem ativar/desativar perfis.");
        }
        AdvancedProfile advancedProfile = (AdvancedProfile) profile;
        if (advancedProfile.getStatus()) throw new ProfileAlreadyActivatedException("o perfil do " + username + " ja esta ativo.");
        advancedProfile.setStatus(true);
    }

    public void unactivateProfile(String username) throws NotFoundError, ProfileUnauthorizedError, ProfileAlreadyActivatedException {
        Optional<Profile> optionalProfile = profileRepository.findProfileByUsername(username);
        Profile profile = optionalProfile.orElseThrow(() -> new NotFoundError("Não foi encontrado perfil com nome: " + username));
        if (!(profile instanceof AdvancedProfile)) {
            throw new ProfileUnauthorizedError("somente perfis avançados podem ativar/desativar perfis.");
        }
        AdvancedProfile advancedProfile = (AdvancedProfile) profile;
        if (!advancedProfile.getStatus()) throw new ProfileAlreadyActivatedException("o perfil do " + username + " ja esta ativo.");
        advancedProfile.setStatus(false);
    }
}
