package com.rede.social.application;

import com.rede.social.exception.global.AlreadyExistsError;
import com.rede.social.exception.global.NotFoundError;
import com.rede.social.exception.interactionException.InteractionDuplicatedError;
import com.rede.social.exception.interactionException.PostUnauthorizedError;
import com.rede.social.exception.requestException.FriendshipAlreadyExistsError;
import com.rede.social.exception.profileException.ProfileAlreadyActivatedError;
import com.rede.social.exception.profileException.ProfileAlreadyDeactivatedError;
import com.rede.social.exception.profileException.ProfileUnauthorizedError;
import com.rede.social.exception.requestException.RequestNotFoundError;
import com.rede.social.model.*;
import com.rede.social.model.enums.InteractionType;
import com.rede.social.repository.IPostRepository;
import com.rede.social.repository.IProfileRepository;

import java.util.*;
import java.util.stream.Stream;

public class SocialNetwork {
    private Map<Profile, Profile> pendingFriendRequests;
    private List<Interaction> interactions;
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
        List<Post> posts = postRepository.listPosts();
        if (posts.isEmpty()) {
            return new Post(1, content, owner);
        }
        Integer id = posts.get(posts.size() - 1).getId() + 1;
        return new Post(id, content, owner);
    }

    /**
     * Método responsável por executar a lógica de criar um post avançado
     * @param content o conteúdo do post a ser criado
     * @param owner a instância de perfil que representa o dono do post
     * @return uma nova instância de AdvancedPost, com o id gerado baseado na quantidade de posts existentes
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public AdvancedPost createAdvancedPost(String content, Profile owner) {
        List<Post> posts = postRepository.listPosts();
        if (posts.isEmpty()) {
            return new AdvancedPost(1, content, owner);
        }
        Integer id = posts.get(posts.size() - 1).getId() + 1;
        return new AdvancedPost(id, content, owner);
    }

    /**
     * Método que encapsula a lógica de adicionar um post no repositório de posts
     * @param post uma instância de Post a ser adicionada no repositório
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public void addPost(Post post) {
        this.postRepository.addPost(post);
    }

    /**
     * Método que encapsula a lógica de recuperar todos os posts através do repositório de posts
     * @return retorna uma lista com todos os posts cadastrados
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public List<Post> listPosts() {
        return this.postRepository.listPosts();
    }

    /**
     * Método que encapsula a lógica de recuperar todos os posts de um dado Perfil, através do repositório de posts
     * @param usernameOwner uma String que representa o nome do dono dos posts
     * @return uma lista de posts pertencentes ao dono do perfil
     * @throws NotFoundError no caso do perfil não ser encontrado
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public List<Post> listPostsByProfile(String usernameOwner) throws NotFoundError {
        return this.postRepository.listPostsByProfile(usernameOwner);
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
     * Método responsável por executar a lógica de criar um perfil avançado
     * @param username o nome de usuário do perfil a ser criado
     * @param photo o emoji do perfil a ser criado
     * @param email o email do perfil a ser criado
     * @return uma instância de perfil avançado, com o id gerado baseado na quantidade de perfis existentes
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public AdvancedProfile createAdvancedPost(String username, String photo, String email) {
        List<Profile> profiles = profileRepository.getAllProfiles();
        if (profiles.isEmpty()) {
            return new AdvancedProfile(1, username, photo, email);
        }
        Integer id = profiles.get(profiles.size() - 1).getId() + 1;
        return new AdvancedProfile(id, username, photo, email);
    }

    /**
     * Método que encapsula a lógica de adicionar um perfil no repositório de perfis
     * @param profile uma instância de perfil a ser adicionada no repositório
     * @throws AlreadyExistsError se o perfil já existe no repositório de perfis
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public void addProfile(Profile profile) throws AlreadyExistsError {
        profileRepository.addProfile(profile);
    }

    /**
     * Método que encapsula a lógica de buscar perfil por id informado
     * @param id o id do perfil a ser buscado no repositório
     * @throws NotFoundError se o perfil não for encontrado
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public Profile findProfileById(Integer id) throws NotFoundError {
        Optional<Profile> founded = profileRepository.findProfileById(id);
        return founded.get();
    }

    /**
     * Método que encapsula a lógica de buscar perfil por email informado
     * @param email o email do perfil a ser buscado no repositório
     * @throws NotFoundError se o perfil não for encontrado
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public Profile findProfileByEmail(String email) throws NotFoundError {
        Optional<Profile> founded = profileRepository.findProfileByEmail(email);
        return founded.get();
    }

    /**
     * Método que encapsula a lógica de buscar perfil por nome de usuário informado
     * @param username o nome de usuário do perfil a ser buscado no repositório
     * @throws NotFoundError se o perfil não for encontrado
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    public Profile findProfileByUsername(String username) throws NotFoundError {
        Optional<Profile> founded = profileRepository.findProfileByUsername(username);
        return founded.get();
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
        Profile profile = optionalProfile.get();
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
        Profile profile = optionalProfile.get();
        if (!(profile instanceof AdvancedProfile)) {
            throw new ProfileUnauthorizedError("Somente perfis avançados podem ativar/desativar perfis.");
        }
        AdvancedProfile advancedProfile = (AdvancedProfile) profile;
        if (!advancedProfile.getStatus()) throw new ProfileAlreadyDeactivatedError("O perfil do " + username + " ja esta inativo.");
        advancedProfile.setStatus(false);
    }

    /**
     * Método responsável por enviar uma solicitação de amizade de um perfil para outro
     * @param usernameApplicant nome do usuário que está enviando a solicitação
     * @param usernameReceiver nome do usuário que irá receber a solicitação
     * @throws NotFoundError caso um dos perfis informados não seja encontrado
     * @throws AlreadyExistsError caso a solicitação ja existe nas solicitações pendentes
     * @throws FriendshipAlreadyExistsError caso os perfis já tenham amizade
     */
    public void sendRequest(String usernameApplicant, String usernameReceiver) throws NotFoundError, AlreadyExistsError, FriendshipAlreadyExistsError {
        Profile applicant = this.profileRepository.findProfileByUsername(usernameApplicant).get();
        Profile receiver = this.profileRepository.findProfileByUsername(usernameReceiver).get();
        if (pendingFriendRequests.containsKey(applicant) && pendingFriendRequests.get(receiver).equals(receiver)) {
            throw new AlreadyExistsError("solicitacao ja existe.");
        }
        if (applicant.getFriends().contains(receiver)){
            throw new FriendshipAlreadyExistsError("esses perfis ja sao amigos");
        }
        pendingFriendRequests.put(applicant, receiver);
    }

    /**
     * Método responsável por aceitar uma solicitação de amizade de um perfil para outro
     * @param usernameApplicant nome do usuário que está enviando a solicitação
     * @param usernameReceiver nome do usuário que irá receber a solicitação
     * @throws NotFoundError caso um dos perfis informados não seja encontrado
     * @throws RequestNotFoundError caso a solicitação que relacionado aos dois perfis não exista
     */
    public void acceptRequest(String usernameApplicant, String usernameReceiver) throws NotFoundError, RequestNotFoundError {
        Profile applicant = this.profileRepository.findProfileByUsername(usernameApplicant).get();
        Profile receiver = this.profileRepository.findProfileByUsername(usernameReceiver).get();
        if (!pendingFriendRequests.containsKey(applicant) || !pendingFriendRequests.get(applicant).equals(receiver)) {
            throw new RequestNotFoundError("solicitacao de amizade nao encontrada.");
        }
        applicant.addFriend(receiver);
        receiver.addFriend(applicant);
        pendingFriendRequests.remove(applicant);
    }

    /**
     * Método responsável por recusar uma solicitação de amizade de um perfil para outro
     * @param usernameApplicant nome do usuário que está enviando a solicitação
     * @param usernameReceiver nome do usuário que irá receber a solicitação
     * @throws NotFoundError caso um dos perfis informados não seja encontrado
     * @throws RequestNotFoundError caso a solicitação que relacionado aos dois perfis não exista
     */
    public void refuseRequest(String usernameApplicant, String usernameReceiver) throws NotFoundError, RequestNotFoundError {
        Profile applicant = this.profileRepository.findProfileByUsername(usernameApplicant).get();
        Profile receiver = this.profileRepository.findProfileByUsername(usernameReceiver).get();
        if (!pendingFriendRequests.containsKey(applicant) || !pendingFriendRequests.get(receiver).equals(receiver)){
            throw new RequestNotFoundError("solicitacao de amizade nao encontrada.");
        }
        pendingFriendRequests.remove(applicant);
    }

    /**
     * Método responsável criar uma instância de Interaction
     * @param type instância de InteractionType que representa o tipo de interação
     * @param owner a instância de perfil que representa o dono da interação
     * @return uma nova instância de Interaction, com o id gerado baseado na quantidade de interactions existentes
     */
    public Interaction createInteraction(InteractionType type, Profile owner) {
        if (interactions.isEmpty()) {
            return new Interaction(1, type, owner);
        }
        Integer id = interactions.get(interactions.size() - 1).getId() + 1;
        return new Interaction(id, type, owner);
    }

    /**
     * Método responsável por adicionar uma nova interação em um post avançado
     * @param idPost id do post que deve ser inserido a interação
     * @param interaction instância de Interaction que representa a interação que será inserida no post
     * @throws PostUnauthorizedError no caso de o post não ser um post avançado
     * @throws InteractionDuplicatedError no caso de uma inserção duplicada de uma mesma interação de um mesmo perfil
     * @throws NotFoundError no caso do post procurado não ser encontrado
     */
    public void addInteraction(Integer idPost, Interaction interaction) throws PostUnauthorizedError, InteractionDuplicatedError, NotFoundError {
        Post post = this.postRepository.findPostById(idPost).get();
        if (!(post instanceof AdvancedPost)){
            throw new PostUnauthorizedError("somente posts avancados podem realizar interacoes.");
        }
        AdvancedPost advancedPost = (AdvancedPost) post;
        if (this.interactionAlreadyExists(advancedPost, interaction)){
            throw new InteractionDuplicatedError("interacao ja existe");
        }
        advancedPost.addInteraction(interaction);
    }

    /**
     * Método axuiliar com a lógica para verificar se uma interação já existe, excencial para evitar interações duplicadas
     * @param advancedPost instância de post avançado que permite interações
     * @param interaction instância da interação que deseja verificar se já existe
     * @return retorna true caso a interação já exista no post avançado ou false caso não exista
     */
    private boolean interactionAlreadyExists(AdvancedPost advancedPost, Interaction interaction) {
        Stream<Interaction> interactionsFromPost = advancedPost.listInteractions().stream();
        return interactionsFromPost.anyMatch( i -> i.getAuthor().equals(interaction.getAuthor()) &&
                i.getType() == interaction.getType());
    }
}


