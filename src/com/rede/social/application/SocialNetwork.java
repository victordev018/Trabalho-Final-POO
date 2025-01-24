package com.rede.social.application;

import com.rede.social.model.*;
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

    public void sendRequest(Profile applicant, Profile receiver) {
        if(pendingFriendRequests.containsKey(applicant) && pendingFriendRequests.get(receiver).equals(receiver) {
            // TODO: adicionar lançamento de exceção para solicitação já existir
        }
        if(applicant.getFriends().contains(receiver)){
            // TODO: adicionar lançamento de exceção para o solicitado já constar nos amigos
        }

        pendingFriendRequests.put(applicant, receiver);

    }

    public void acceptRequest(Profile applicant, Profile receiver) {
        if(!pendingFriendRequests.containsKey(applicant) || !pendingFriendRequests.get(receiver).equals(receiver)) {
            // TODO: adicionar exceção de não haver solicitações entre os usuários em questão
        }

        applicant.addFriend(receiver);
        receiver.addFriend(applicant);
        pendingFriendRequests.remove(applicant);
    }

    public void removeRequest(Profile applicant, Profile receiver){
        if(!pendingFriendRequests.containsKey(applicant) || !pendingFriendRequests.get(receiver).equals(receiver)){
            // TODO: adicionar mesma exceção do método acima
        }

        pendingFriendRequests.remove(applicant);
    }

    public void addInteraction(Post post, Interaction interaction){
        if(!(post instanceof AdvancedPost)){
            // TODO: lançar exceção que post não é instancia de AdvancedPost
        }
        AdvancedPost advancedPost = (AdvancedPost) post;
        if(advancedPost.listInteractions().contains(interaction)){
            // TODO: Adicionar exceção de já existir interação com o AdvancedPost
        }
        advancedPost.addInteraction(interaction);
    }
}


