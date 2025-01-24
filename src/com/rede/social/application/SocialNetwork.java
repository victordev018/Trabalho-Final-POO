package com.rede.social.application;

import com.rede.social.exception.global.AlreadyExistsError;
import com.rede.social.exception.global.NotFoundError;
import com.rede.social.exception.profileException.ProfileAlreadyActivatedError;
import com.rede.social.exception.profileException.ProfileAlreadyDeactivatedError;
import com.rede.social.exception.profileException.ProfileUnauthorizedError;
import com.rede.social.model.*;
import com.rede.social.repository.IPostRepository;
import com.rede.social.repository.IProfileRepository;

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

    /**
     * Método responsável por executar a lógica de criar um post
     * @param content o conteúdo do post a ser criado
     * @param owner a instância de perfil que representa o dono do post
     * @return uma nova instância de Post, com o id gerado baseado na quantidade de posts existentes
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public Post createPost(String content, Profile owner) {
        List<Post> posts = postRepository.getAllPosts();
        if (posts.isEmpty()) {
            return new Post(1, content, owner);
        }
        Integer id = posts.get(posts.size() - 1).getId() + 1;
        return new Post(id, content, owner);
    }

    /**
     * Método responsável por executar a lógica de criar um perfil
     * @param username o nome de usuário do perfil a ser criado
     * @param photo o emoji do perfil a ser criado
     * @param email o email do perfil a ser criado
     * @return uma instância de perfil, com o id gerado baseado na quantidade de perfis existentes
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public Profile createProfile(String username, String photo, String email) {
        List<Profile> profiles = profileRepository.getAllProfiles();
        if (profiles.isEmpty()) {
            return new Profile(1, username, photo, email);
        }
        Integer id = profiles.get(profiles.size() - 1).getId() + 1;
        return new Profile(id, username, photo, email);
    }

    /**
     * Método que encapsula a lógica de adicionar um perfil no repositório de perfis
     * @param profile uma instância de perfil a ser adicionada no repositório
     * @throws AlreadyExistsError se o perfil já existe no repositório de perfis
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public void addProfile(Profile profile) throws AlreadyExistsError {
        Optional<Profile> foundedById = profileRepository.findProfileById(profile.getId());
        Optional<Profile> foundedByEmail = profileRepository.findProfileByEmail(profile.getEmail());
        Optional<Profile> foundedByName = profileRepository.findProfileByUsername(profile.getUsername());

        foundedByName.orElseThrow(() -> new AlreadyExistsError("Ja existe perfil com nome: " + profile.getUsername()));
        foundedByEmail.orElseThrow(() -> new AlreadyExistsError("Ja existe perfil com email: " + profile.getEmail()));
        foundedById.orElseThrow(() -> new AlreadyExistsError("Ja existe perfil com id: " + profile.getId()));

        profileRepository.addProfile(profile);
    }

    /**
     * Método que encapsula a lógica de buscar perfil por id informado
     * @param id o id do perfil a ser buscado no repositório
     * @throws NotFoundError se o perfil não for encontrado
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public Profile findProfileById(Integer id) throws NotFoundError{
        Optional<Profile> founded = profileRepository.findProfileById(id);
        return founded.orElseThrow(() -> new NotFoundError("Não foi encontrado perfil com id: " + id));
    }

    /**
     * Método que encapsula a lógica de buscar perfil por email informado
     * @param email o email do perfil a ser buscado no repositório
     * @throws NotFoundError se o perfil não for encontrado
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public Profile findProfileByEmail(String email) throws NotFoundError {
        Optional<Profile> founded = profileRepository.findProfileByEmail(email);
        return founded.orElseThrow(() -> new NotFoundError("Não foi encontrado perfil com email: " + email));
    }

    /**
     * Método que encapsula a lógica de buscar perfil por nome de usuário informado
     * @param username o nome de usuário do perfil a ser buscado no repositório
     * @throws NotFoundError se o perfil não for encontrado
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public Profile findProfileByUsername(String username) throws NotFoundError {
        Optional<Profile> founded = profileRepository.findProfileByUsername(username);
        return founded.orElseThrow(() -> new NotFoundError("Não foi encontrado perfil com nome: " + username));
    }

    /**
     * Método para ativar perfil se o perfil for instância de AdvancedProfile
     * @param username o nome de usuário do perfil a ser buscado no repositório
     * @throws NotFoundError se o perfil não for encontrado
     * @throws ProfileUnauthorizedError se o perfil encontrado não for instância de perfil avançado
     * @throws ProfileAlreadyActivatedError se o perfil encontrado já estiver ativo
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public void activateProfile(String username) throws NotFoundError, ProfileUnauthorizedError, ProfileAlreadyActivatedError {
        Optional<Profile> optionalProfile = profileRepository.findProfileByUsername(username);
        Profile profile = optionalProfile.orElseThrow(() -> new NotFoundError("Não foi encontrado perfil com nome: " + username));
        if (!(profile instanceof AdvancedProfile)) {
            throw new ProfileUnauthorizedError("Somente perfis avançados podem ativar/desativar perfis.");
        }
        AdvancedProfile advancedProfile = (AdvancedProfile) profile;
        if (advancedProfile.getStatus()) throw new ProfileAlreadyActivatedError("O perfil do " + username + " ja esta ativo.");
        advancedProfile.setStatus(true);
    }

    /**
     * Método para desativar perfil se o perfil for instância de AdvancedProfile
     * @param username o nome de usuário do perfil a ser buscado no repositório
     * @throws NotFoundError se o perfil não for encontrado
     * @throws ProfileUnauthorizedError se o perfil encontrado não for instância de perfil avançado
     * @throws ProfileAlreadyDeactivatedError se o perfil encontrado já estiver inativo
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public void unactivateProfile(String username) throws NotFoundError, ProfileUnauthorizedError, ProfileAlreadyDeactivatedError {
        Optional<Profile> optionalProfile = profileRepository.findProfileByUsername(username);
        Profile profile = optionalProfile.orElseThrow(() -> new NotFoundError("Não foi encontrado perfil com nome: " + username));
        if (!(profile instanceof AdvancedProfile)) {
            throw new ProfileUnauthorizedError("Somente perfis avançados podem ativar/desativar perfis.");
        }
        AdvancedProfile advancedProfile = (AdvancedProfile) profile;
        if (!advancedProfile.getStatus()) throw new ProfileAlreadyDeactivatedError("O perfil do " + username + " ja esta inativo.");
        advancedProfile.setStatus(false);
    }

    public void sendRequest(Profile applicant, Profile receiver) {
        if(pendingFriendRequests.containsKey(applicant) && pendingFriendRequests.get(receiver).equals(receiver)) {
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


